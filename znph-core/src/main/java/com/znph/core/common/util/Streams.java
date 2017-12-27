package com.znph.core.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Streams {

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	public static Map<String, String> properties(InputStream inputStream) {
		return Strings.properties(string(inputStream));
	}

	public static String string(InputStream inputStream) {
		try {
			return new String(bytes(inputStream));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String string(InputStream inputStream, String charsetName) {
		try {
			return new String(bytes(inputStream), charsetName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] bytes(InputStream inputStream) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length = 0;
			while ((length = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void copy(InputStream in, File file) {
		try {
			if (file.getParentFile() != null && !file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			copy(in, out);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void copy(InputStream in, OutputStream out) {
		try {
			byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = in.read(bytes)) != -1) {
				out.write(bytes, 0, length);
			}
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void ungzip(InputStream is, OutputStream os) {
		try {
			GZIPInputStream gis = new GZIPInputStream(is);
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = gis.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
			os.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void gzip(InputStream is, OutputStream os) {
		try {
			GZIPOutputStream gos = new GZIPOutputStream(os);
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = is.read(buffer)) != -1) {
				gos.write(buffer, 0, length);
			}
			gos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
