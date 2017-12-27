package com.znph.core.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Files {

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	public static void copy(File file, File newFile) {
		copy(file, newFile, DEFAULT_BUFFER_SIZE);
	}

	public static void copy(File file, File newFile, int bufferSize) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File temp : files) {
				copy(temp, new File(newFile, temp.getName()), bufferSize);
			}
			if(!newFile.exists()) {
				newFile.mkdirs();
			}
		} else {
			copySingle(file, newFile, bufferSize);
		}
	}

	private static void copySingle(File file, File newFile, int bufferSize) {
		try {
			if(!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}
			if(!newFile.exists()) {
				newFile.createNewFile();
			} else {
				String md5 = Encrypts.md5(file);
				String newMd5 = Encrypts.md5(newFile);
				if (newMd5.equals(md5)) {
					return;
				}
			}
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(newFile);
			FileChannel ic = fis.getChannel();
			FileChannel oc = fos.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
			while (ic.read(buffer) != -1) {
				buffer.flip();
				oc.write(buffer);
				buffer.clear();
			}
			ic.close();
			oc.close();
			fis.close();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void delete(File file) {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File temp : listFiles) {
				delete(temp);
			}
			file.delete();
			if (file.exists()) {
				throw new RuntimeException("delete file error:" + file.getPath());
			}
		} else {
			file.delete();
			if (file.exists()) {
				throw new RuntimeException("delete file error:" + file.getPath());
			}
		}
	}

	public static List<File> getAll(File folder) {
		return getAll(folder, false);
	}

	public static List<File> getAll(File folder, boolean withFolder) {
		return getAll(folder, withFolder, null);
	}

	public static List<File> getAll(File folder, String pattern) {
		return getAll(folder, false, pattern);
	}

	public static List<File> getAll(File folder, boolean withFolder, String pattern) {
		List<File> allFile = new ArrayList<File>();
		scan(folder, allFile);
		Iterator<File> iterator = allFile.iterator();
		while (iterator.hasNext()) {
			File next = iterator.next();
			if (next.isDirectory() && !withFolder) {
				iterator.remove();
			}
			if (pattern != null && !next.getAbsolutePath().matches(pattern)) {
				iterator.remove();
			}
		}
		return allFile;
	}

	private static void scan(File file, List<File> fileList) {
		fileList.add(file);
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File temp : listFiles) {
				scan(temp, fileList);
			}
		}
	}

	public static Map<String, String> properties(File propertyFile) {
		return Strings.properties(read(propertyFile));
	}

	public static String read(String filePath) {
		return read(new File(filePath));
	}

	public static String read(File file) {
		try {
			FileInputStream in = new FileInputStream(file);
			String content = Streams.string(in);
			in.close();
			return content;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] bytes(String filePath) {
		return bytes(new File(filePath));
	}

	public static byte[] bytes(File file) {
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] content = Streams.bytes(in);
			in.close();
			return content;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void write(String filePath, String content) {
		write(new File(filePath), content);
	}

	public static void write(File file, String content) {
		write(file, content.getBytes());
	}
	
	public static void write(File file, byte[] bytes) {
		try {
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			Streams.copy(bis, fos);
			bis.close();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String md5(File file) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length = 0;
			while ((length = inputStream.read(buffer)) != -1) {
				digest.update(buffer, 0, length);
			}
			inputStream.close();
			byte[] bytes = digest.digest();
			return Strings.bytesToHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(md5(new File("C:\\Users\\mayong\\Desktop\\5662FD0A-D1E9-4B39-9A4C-A30EB6AC35D9.jpg")));
		System.out.println(Encrypts.md5(bytes(new File("C:\\Users\\mayong\\Desktop\\5662FD0A-D1E9-4B39-9A4C-A30EB6AC35D9.jpg"))));
	}
}
