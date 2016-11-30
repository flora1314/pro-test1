package com.flora.domain.common;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils extends org.apache.commons.lang.time.DateUtils {

	/**
	 * 根据基础时间(base),单位(unit),偏移量(differ)对时间进行偏移并返回偏移后的时间
	 * @param base
	 * @param forward
	 * @param unit
	 * @param differ
	 * @return
	 */
	public static Date shiftDate(Date base, boolean forward, DateUnit unit, int differ){
		if(base == null){
			return null;
		}
		if(differ <= 0){
			return base;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(base);
		
		c.set(unit.getUnit(), c.get(unit.getUnit()) +(forward ? differ : -differ));
		
		return c.getTime();
	}
	
	/**
	 * 获取一天开始的时间点，如： 2015-05-22 11:49:13， 返回 2015-05-22 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getStartOfDate(Date date){
		if(date == null){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	/**
	 * 获取一天的结束时间点， 如： 2015-05-22 11:49:13， 返回 2015-05-22 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getEndOfDate(Date date){
		if(date == null){
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTime();
	}
	
	public enum DateUnit{
		
		YEAR(Calendar.YEAR),
		MONTH(Calendar.MONTH),
		WEEK(Calendar.MONTH),
		DAY(Calendar.DAY_OF_YEAR),
		HOUR(Calendar.HOUR_OF_DAY),
		DAY_OF_MONTH(Calendar.DAY_OF_MONTH),
		MINUE(Calendar.MINUTE),
		SECOND(Calendar.SECOND);
		
		private int unit;
		
		private DateUnit(int unit){
			this.unit = unit;
		}

		public int getUnit() {
			return unit;
		}

		public void setUnit(int unit) {
			this.unit = unit;
		}
		
	}
	
	public static void main(String[] args){
		Date nowDate = new Date();
		System.out.println(nowDate);
		System.out.println(shiftDate(nowDate, false, DateUnit.MONTH, 3));
	}
}
