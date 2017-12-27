package com.znph.core.common.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Beans {
	
	public static List<Class<?>> getParameterizedTypes(Class<?> clazz) {
		List<Class<?>> typeList = Collections.arrayList();
        ParameterizedType ptype = (ParameterizedType) clazz.getGenericSuperclass();
    	Type[] actualTypeArguments = ptype.getActualTypeArguments();
    	for (int i = 0; i < actualTypeArguments.length; i++) {
    		Type cmpType = actualTypeArguments[i];
    		if (cmpType instanceof ParameterizedType) {
    			typeList.add((Class<?>) ((ParameterizedType) cmpType).getRawType());
    		} else {
    			typeList.add((Class<?>) cmpType);
    		}
		}
	    return typeList;
	}
	
	public static boolean isSimpleType(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T proxy(final Class<T> clazz, final Callable<T> constructor) {
		if (clazz.isInterface()) {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			InvocationHandler invocationHandler = new InvocationHandler() {
				private T target;
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					if (target == null) {
						target = constructor.call();
					}
					return method.invoke(target, args);
				}
			};
			return (T) Proxy.newProxyInstance(contextClassLoader, new Class<?>[] { clazz }, invocationHandler);
		}
		throw new UnsupportedOperationException();
	}
	
	public static Map<String, Object> getReadMethodResultMap(Object object) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Method[] allDeclaredReadMethods = getAllDeclaredReadMethods(object.getClass());
		for (Method method : allDeclaredReadMethods) {
			try {
				Object result = method.invoke(object);
				resultMap.put(method.getName(), result);
			} catch (Exception e) {
			}
		}
		return resultMap;
	}
	
	public static <T> void copyProperties(T source, T target) {
		copyProperties(source, target, false, false);
	}
	
	public static <T> void copyPropertiesWithNull(T source, T target) {
		copyProperties(source, target, true, false);
	}
	
	public static <T> void copyPropertiesWithPrimitive(T source, T target) {
		copyProperties(source, target, false, true);
	}

	public static <T> void copyProperties(T source, T target, boolean withNull, boolean withPrimitive) {
		Field[] fields = getAllDeclaredFields(source.getClass());
		for (Field field : fields) {
			try {
				if (!Modifier.isStatic(field.getModifiers()) && (withPrimitive || !field.getType().isPrimitive())) {
					String fieldName = field.getName();
					Method readMethod = getReadMethod(source.getClass(), fieldName);
					Object value = readMethod.invoke(source);
					if (withNull || value != null) {
						Method writeMethod = getWriteMethod(target.getClass(), fieldName);
						Type[] parameterTypes = writeMethod.getGenericParameterTypes();
						if (parameterTypes != null && parameterTypes.length == 1 && parameterTypes[0].equals(readMethod.getGenericReturnType())) {
							writeMethod.invoke(target, value);
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	public static Object emptyObject() {
		return null;
	}
	
	public static Map<String, Object> getAllNotNullFields(Object bean) {
		return getAllNotNullFields(bean, false);
	}
	
	public static Map<String, Object> getAllNotNullFields(Object bean, boolean withPrimitive) {
		Class<?> clazz = bean.getClass();
		Field[] fields = getAllDeclaredFields(clazz);
		Map<String, Object> fieldValues = Collections.linkedHashMap();
		for (Field field : fields) {
			try {
				if (!Modifier.isStatic(field.getModifiers()) && (withPrimitive || !field.getType().isPrimitive())) {
					Method readMethod = getReadMethod(clazz, field);
					Object value = readMethod.invoke(bean);
					if (value != null) {
						fieldValues.put(field.getName(), value);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fieldValues;
	}

	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		while (!clazz.equals(Object.class)) {
			fieldList.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	public static Method[] getAllDeclaredMethods(Class<?> clazz) {
		List<Method> methodList = new ArrayList<Method>();
		while (!clazz.equals(Object.class)) {
			methodList.addAll(Arrays.asList(clazz.getDeclaredMethods()));
			clazz = clazz.getSuperclass();
		}
		Method[] methods = new Method[methodList.size()];
		methodList.toArray(methods);
		return methods;
	}

	public static Method[] getAllDeclaredReadMethods(Class<?> clazz) {
		Method[] allMethods = getAllDeclaredMethods(clazz);
		List<Method> methodList = new ArrayList<Method>();
		for (Method method : allMethods) {
			if (method.getName().startsWith("is") || method.getName().startsWith("get")) {
				methodList.add(method);
			}
		}
		Method[] methods = new Method[methodList.size()];
		methodList.toArray(methods);
		return methods;
	}

	public static Method[] getAllDeclaredWriteMethods(Class<?> clazz) {
		Method[] allMethods = getAllDeclaredMethods(clazz);
		List<Method> methodList = new ArrayList<Method>();
		for (Method method : allMethods) {
			if (method.getName().startsWith("set")) {
				methodList.add(method);
			}
		}
		Method[] methods = new Method[methodList.size()];
		methodList.toArray(methods);
		return methods;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		while (!clazz.equals(Object.class)) {
			try {
				return clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				clazz = clazz.getSuperclass();
			}
		}
		String exceptionMsg = clazz.getName() + "." + methodName + "(";
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> type = parameterTypes[i];
			exceptionMsg += type.getName() + " arg" + i + ", ";
		}
		exceptionMsg += ")";
		exceptionMsg = exceptionMsg.replace(", )", ")");
		try {
			throw new NoSuchMethodException(exceptionMsg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Field getField(Class<?> clazz, String fieldName) {
		while (!clazz.equals(Object.class)) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		try {
			throw new NoSuchFieldException(clazz.getName() + "." + fieldName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getReadMethod(Class<?> clazz, String property) {
		return getReadMethod(clazz, getField(clazz, property));
	}
	
	public static Method getReadMethod(Class<?> clazz, Field field) {
		String property = field.getName();
		property = Strings.upperFirst(property);
		if (field.getType().equals(boolean.class)) {
			return getMethod(clazz, "is" + property);
		} else {
			return getMethod(clazz, "get" + property);
		}
	}

	public static Method getWriteMethod(Class<?> clazz, String property) {
		return getWriteMethod(clazz, getField(clazz, property));
	}
	
	public static Method getWriteMethod(Class<?> clazz, Field field) {
		String property = field.getName();
		property = Strings.upperFirst(property);
		return getMethod(clazz, "set" + property, field.getType());
	}

	public static Object invokeReadMethod(Object bean, String property) {
		try {
			return getReadMethod(bean.getClass(), property).invoke(bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void invokeWriteMethod(Object bean, String property, Object value) {
		try {
			getWriteMethod(bean.getClass(), property).invoke(bean, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String[] getDeclaredFieldMames(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fieldNames.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	public static List<Class<?>> scanClass(String packageName) {
		boolean recursive = true;
		List<Class<?>> classList = new ArrayList<Class<?>>();
		try {
			List<File> fileList = new ArrayList<File>();
			URL url = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
			if (url.getProtocol().equals("file")) {
				File folder = new File(url.toURI());
				if (folder.isDirectory()) {
					fileList.addAll(Files.getAll(folder, ".*class"));
				}
				for (File file : fileList) {
					String className = file.getName().substring(0, file.getName().length() - 6);
					classList.add(Class.forName(packageName + "." + className));
				}
			} else if (url.getProtocol().equals("jar")) {
				JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
				JarFile jarFile = jarURLConnection.getJarFile();
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					if (name.charAt(0) == '/') {
						name = name.substring(1);
					}
					if (name.startsWith(packageName.replace(".", "/"))) {
						int idx = name.lastIndexOf('/');
						if (idx != -1) {
							packageName = name.substring(0, idx).replace('/', '.');
						}
						if ((idx != -1) || recursive) {
							if (name.endsWith(".class") && !entry.isDirectory()) {
								String className = name.substring(packageName.length() + 1, name.length() - 6);
								try {
									classList.add(Class.forName(packageName + '.' + className));
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Collection<K>, K> T clone(T collection) {
		try {
			T cloneCollection = (T) collection.getClass().newInstance();
			for (Object object : collection) {
				K clone = (K) clone(object);
				cloneCollection.add(clone);
			}
			return cloneCollection;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <M extends Map<K, V>, K, V> M clone(M map) {
		try {
			M cloneMap = (M) map.getClass().newInstance();
			for (K k : map.keySet()) {
				K cloneK = (K) clone(k);
				V cloneV = (V) clone(map.get(k));
				cloneMap.put(cloneK, cloneV);
			}
			return cloneMap;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T clone(T object) {
		try {
			T clone = (T) object.getClass().newInstance();
			copyProperties(object, clone);
			return clone;
		} catch (Exception e) {
			return null;
		}
	}
}
