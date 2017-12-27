package com.znph.core.common.bean;

import java.util.Date;

import com.znph.core.common.property.PropertyHolder;


public class ID {

	static long startTime = 1443628800;
	static int index;
	static int prefix;
	static int length = 100000;
	static long time;
	static int[] array = new int[length];

	/**
	 * 获取唯一ID （16位） 2位前缀（10开始）+ 9位时间戳（秒数） + 5位随机数（不重复） 9位时间戳支持31年不重复
	 * 
	 * @param prefix
	 *            前缀（两位，从10开始）
	 * @return
	 */
	public static synchronized long get() {
		if (prefix == 0) {
			String appPrefix = PropertyHolder.get("image.idPrefix");
			prefix = Integer.valueOf(appPrefix);
		}
		long id = get(prefix);
		return id;
	}

	public static synchronized long get(int prefix) {
		long stime = System.currentTimeMillis() / 1000 - startTime;
		if (stime != time) {
			// 重置
			time = stime;
			index = 0;
			for (int i = 0; i < array.length; i++) {
				array[i] = i;
			}
		}
		int count = length - index++;
		if (count == 0) {
			// 用完了，等一秒
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 重置
			stime = System.currentTimeMillis() / 1000 - startTime;
			time = stime;
			index = 0;
			for (int i = 0; i < array.length; i++) {
				array[i] = i;
			}
			count = length - index++;
		}
		int r = (int) (Math.random() * (count));// 在剩下的元素中取出随机顺序
		// 将随机出的元素放在最后
		int n = array[r];
		array[r] = array[count - 1];
		array[count - 1] = n;
		long v = n + stime * length + prefix * 100000000000000l;
		// return reverse(v);
		return reverse(v);
	}

	/**
	 * id随机数乱序后五位
	 * 
	 * @param stime
	 * @return
	 */
	public static long reverse(long stime) {
		String string = String.valueOf(stime);
		char[] array = string.toCharArray();
		int length = array.length;
		char[] newArray = new char[length];
		for (int i = 0; i < 6; i++) {
			newArray[i] = array[i];
		}
		int n = 0;
		for (int i = 6; i < length; i++) {
			if (n % 2 == 0) {
				newArray[i] = array[i - (n) / 2];
			} else {
				newArray[i] = array[length - (n + 1) / 2];
			}
			n++;
		}
		return Long.valueOf(String.valueOf(newArray));
	}


	public static long unReverse(long reverseValue) {
		String reverseValueString = String.valueOf(reverseValue);
		char[] chars = reverseValueString.toCharArray();
		String unReverseString = reverseValueString.substring(0, 7);
		unReverseString += chars[8];
		unReverseString += chars[10];
		unReverseString += chars[12];
		unReverseString += chars[14];
		unReverseString = unReverseString.substring(2);
		return Long.valueOf(unReverseString);
	}

	public static Date date(long idSecond) {
		return new Date((idSecond + startTime) * 1000);
	}

	public static void main(String[] args) {
		long a = ID.get(11);
		System.out.println(a);
		System.out.println(ID.date(ID.unReverse(a)));
	}

}
