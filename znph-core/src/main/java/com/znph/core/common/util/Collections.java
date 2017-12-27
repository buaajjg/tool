package com.znph.core.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Collections {

	public static <T> List<T> arrayList() {
		return new ArrayList<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> arrayList(T... objects) {
		return new ArrayList<T>(Arrays.asList(objects));
	}
	
	public static <T> List<T> arrayList(Collection<T> objects) {
		return new ArrayList<T>(objects);
	}

	public static <T> Set<T> hashSet() {
		return new HashSet<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> hashSet(T... objects) {
		return new HashSet<T>(Arrays.asList(objects));
	}
	
	public static <T> Set<T> hashSet(Collection<T> objects) {
		return new HashSet<T>(objects);
	}

	public static <T> Set<T> linkedHashSet() {
		return new LinkedHashSet<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> linkedHashSet(T... objects) {
		return new LinkedHashSet<T>(Arrays.asList(objects));
	}
	
	public static <T> Set<T> linkedHashSet(Collection<T> objects) {
		return new LinkedHashSet<T>(objects);
	}

	public static <K, V> Map<K, V> hashMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> Map<K, V> hashMap(K[] keys, V[] values) {
		Map<K, V> map = hashMap();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<T, T> hashMap(T... kvs) {
		Map<T, T> map = hashMap();
		if (kvs.length % 2 != 0) {
			throw new IllegalArgumentException("参数个数必须为偶数个");
		}
		for (int i = 0; i < kvs.length; i += 2) {
			map.put(kvs[i], kvs[i + 1]);
		}
		return map;
	}
	
	public static <K, V> Map<K, V> linkedHashMap() {
		return new LinkedHashMap<K, V>();
	}

	public static <K, V> Map<K, V> linkedHashMap(K[] keys, V[] values) {
		Map<K, V> map = linkedHashMap();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		return map;
	}
	
	public static boolean isEmpty(Collection<?> collections) {
		return collections == null || collections.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collections) {
		return collections != null && !collections.isEmpty();
	}

	public static <K, V> Map<K, V> sortMapV(Map<K, V> map, final Comparator<V> comparator) {
		List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>(map.entrySet());
		java.util.Collections.sort(entryList, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
				return comparator.compare(entry1.getValue(), entry2.getValue());
			}
		});
		Map<K, V> linkedHashMap = linkedHashMap();
		for (Entry<K, V> entry : entryList) {
			linkedHashMap.put(entry.getKey(), entry.getValue());
		}
		return linkedHashMap;
	}

	public static <K, V> Map<K, V> sortMapK(Map<K, V> map, final Comparator<K> comparator) {
		List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>(map.entrySet());
		java.util.Collections.sort(entryList, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
				return comparator.compare(entry1.getKey(), entry2.getKey());
			}
		});
		Map<K, V> linkedHashMap = linkedHashMap();
		for (Entry<K, V> entry : entryList) {
			linkedHashMap.put(entry.getKey(), entry.getValue());
		}
		return linkedHashMap;
	}

	public static <K extends Comparable<? super K>, V> Map<K, V> sortMapK(Map<K, V> map) {
		List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>(map.entrySet());
		java.util.Collections.sort(entryList, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
				return entry1.getKey().compareTo(entry2.getKey());
			}
		});
		Map<K, V> linkedHashMap = linkedHashMap();
		for (Entry<K, V> entry : entryList) {
			linkedHashMap.put(entry.getKey(), entry.getValue());
		}
		return linkedHashMap;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapV(Map<K, V> map) {
		List<Entry<K, V>> entryList = new ArrayList<Entry<K, V>>(map.entrySet());
		java.util.Collections.sort(entryList, new Comparator<Entry<K, V>>() {
			public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		Map<K, V> linkedHashMap = linkedHashMap();
		for (Entry<K, V> entry : entryList) {
			linkedHashMap.put(entry.getKey(), entry.getValue());
		}
		return linkedHashMap;
	}

	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		java.util.Collections.sort(list);
	}

	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		java.util.Collections.sort(list, c);
	}

	public static <T> void reverse(List<T> list) {
		java.util.Collections.reverse(list);
	}
	
	public static <T> void shuffle(List<T> list) {
		java.util.Collections.shuffle(list);
	}
}
