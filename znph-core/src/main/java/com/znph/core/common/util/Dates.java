package com.znph.core.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dates {

	public static int compare(Date date1, Date date2, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date1).compareTo(dateFormat.format(date2));
	}

	public static Date dateAdd(Date date, int field, int amount) {
		Calendar calendar = getCalendarInstance(date);
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static Date dateAdd(int field, int amount) {
		return dateAdd(new Date(), field, amount);
	}

	public static String format(Date date) {
		return format(date, PATTERN_DATE);
	}

	public static String format(Date date, String pattern) {
		if (date == null)
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 格式化为时间长度（HH小时mm分ss秒）
	 * 
	 * @param timeMillis
	 * @param pattern
	 * @return
	 */
	public static String formatTimeMillis(long timeMillis, String pattern) {
		long max = 1000 * 60 * 60 * 24;
		timeMillis = timeMillis % max;
		long h = timeMillis / (1000 * 60 * 60);
		long m = (timeMillis - (1000 * 60 * 60) * h) / (1000 * 60);
		long s = ((timeMillis - (1000 * 60 * 60) * h) - (1000 * 60) * m) / 1000;
		return pattern.replace("HH", "" + h / 10 + h % 10).replace("mm", "" + m / 10 + m % 10).replace("ss",
				"" + s / 10 + s % 10);
	}

	public static Date parseFromFriendly(String time) {
		Calendar calendar = Calendar.getInstance();
		if (time.matches("\\d*(分钟前|小时前|昨天|前天|天前)")) {
			String unit = time.replaceAll("\\d", "");
			Integer amount = Integer.parseInt(time.replace(unit, ""));
			if (unit.equals("分钟前")) {
				calendar.add(Calendar.MINUTE, -amount);
			} else if (unit.equals("小时前")) {
				calendar.add(Calendar.HOUR_OF_DAY, -amount);
			} else if (unit.equals("昨天")) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			} else if (unit.equals("前天")) {
				calendar.add(Calendar.DAY_OF_YEAR, -2);
			} else if (unit.equals("天前")) {
				calendar.add(Calendar.DAY_OF_YEAR, -amount);
			}
		}
		return calendar.getTime();
	}

	public static String formatToFriendly(Date date) {
		Calendar calendar = Calendar.getInstance();
		long days = calendar.getTimeInMillis() / 86400000 - date.getTime() / 86400000;
		if (days == 0) {
			long hour = (calendar.getTimeInMillis() - date.getTime()) / 3600000;
			if (hour == 0) {
				return Math.max((calendar.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
			} else {
				return hour + "小时前";
			}
		} else if (days == 1) {
			return "昨天";
		} else if (days == 2) {
			return "前天";
		} else if (days > 2 && days <= 10) {
			return days + "天前";
		} else {
			return format(date, PATTERN_DATE);
		}
	}

	public static Date[][] generateCalendar(Date date) {
		return generateCalendar(date, Calendar.SUNDAY);
	}

	public static Date[][] generateCalendar(Date date, int firstDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		Date[][] days = new Date[6][7];
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		int row = 0;
		for (int i = Calendar.SUNDAY; i < week; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			days[row][week - i - 1] = calendar.getTime();
		}
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++, week++) {
			if (week > 7) {
				week = week % 7;
				row++;
			}
			days[row][week - 1] = calendar.getTime();
			calendar.roll(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.MONDAY, 1);
		if (week > 7) {
			week = week % 7;
			row++;
		}
		while (row < days.length) {
			for (; week <= 7; week++) {
				days[row][week - 1] = calendar.getTime();
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			week = week % 7;
			row++;
		}
		if (firstDay != Calendar.SUNDAY) {
			int offset = firstDay - Calendar.SUNDAY;
			for (int i = 0; i < days.length; i++) {
				for (int j = 0; j < days[i].length; j++) {
					Calendar c = getCalendarInstance(days[i][j]);
					c.add(Calendar.DAY_OF_WEEK, offset);
					days[i][j] = c.getTime();
				}
			}
		}
		if (getCalendarInstance(days[0][0]).get(Calendar.MONTH) == getCalendarInstance(date).get(Calendar.MONTH)) {
			for (int i = 0; i < days.length; i++) {
				for (int j = 0; j < days[i].length; j++) {
					Calendar c = getCalendarInstance(days[i][j]);
					c.add(Calendar.DAY_OF_WEEK, -7);
					days[i][j] = c.getTime();
				}
			}
		}
		return days;
	}

	public static Calendar getCalendarInstance(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date getEarliest(Date date, int field) {
		Calendar calendar = getCalendarInstance(date);
		switch (field) {
		case Calendar.YEAR:
			calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
		case Calendar.MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		case Calendar.DAY_OF_MONTH:
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		case Calendar.HOUR_OF_DAY:
			calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		case Calendar.MINUTE:
			calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		case Calendar.SECOND:
			calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
		default:
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			c.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
			c.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
			c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
			c.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));
			calendar.setTime(c.getTime());
			break;
		}
		return calendar.getTime();
	}

	public static Date getEarliest(int field) {
		return getEarliest(new Date(), field);
	}

	public static Date getLatest(Date date, int field) {
		Calendar calendar = getCalendarInstance(date);
		switch (field) {
		case Calendar.YEAR:
			calendar.set(Calendar.MONTH, calendar.getMaximum(Calendar.MONTH));
		case Calendar.MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
		case Calendar.DAY_OF_MONTH:
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		case Calendar.HOUR_OF_DAY:
			calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		case Calendar.MINUTE:
			calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		case Calendar.SECOND:
			calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
		default:
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			c.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
			c.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
			c.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
			c.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
			c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
			c.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));
			calendar.setTime(c.getTime());
			break;
		}
		return calendar.getTime();
	}

	public static Date getLatest(int field) {
		return getLatest(new Date(), field);
	}

	public static Date parse(String source) {
		return parse(source, PATTERN_DATE);
	}

	public static Date parse(String source, String pattern) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date getTime(Date date, String time) {
		return parse(format(date) + " " + time, PATTERN_DATETIME_FULL);
	}

	public static Date get0Time(Date date) {
		return getTime(date, "00:00:00");
	}

	public static Date get2359Time(Date date) {

		return getTime(date, "23:59:59");
	}
	
	/**
	 * 获得年龄
	 * @param date yyyy-MM-dd
	 * @return
	 * @author Minco
	 * @date 2017年10月18日
	 */
	public static int getAge(String birthDay) {
		if(Strings.isBlank(birthDay)) {
			return 0;
		}
		
		return getAge(Dates.parse(birthDay));
	}
	
	/**
	 * 获得年龄
	 * @param date yyyy-MM-dd
	 * @return
	 * @author Minco
	 * @date 2017年10月18日
	 */
	public static int getAge(Date birthDay) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
        if (Dates.compare(date, birthDay, PATTERN_DATE) < 1) {  
        	//年龄
            return 0;
        }  
        calendar.setTime(date);
        int yearNow = calendar.get(Calendar.YEAR);  
        int monthNow = calendar.get(Calendar.MONTH);  
        int dayNow = calendar.get(Calendar.DAY_OF_MONTH); 
        
        calendar.setTime(birthDay);  
        int yearBirth = calendar.get(Calendar.YEAR);  
        int monthBirth = calendar.get(Calendar.MONTH);  
        int dayBirth = calendar.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)) {  
        	age--;
        }  

        return age;  
	}
	
	

	public static final long MILLISECOND_OF_HOUR = 3600000;

	public static final long MILLISECOND_OF_DAY = 86400000;

	public static final String PATTERN_DATE = "yyyy-MM-dd";

	public static final String PATTERN_TIME = "HH:mm:ss";

	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_DATETIME_FULL = "yyyy-MM-dd HH:mm:ss SSS";

}
