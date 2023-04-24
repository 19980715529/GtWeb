package com.smallchill.core.utils;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

public class WeekUtils {
	 public static void main(String[] args) {  
         
        // WeekUtils cd = new WeekUtils();  
        // System.out.println("开始时间: " + cd.getStartDayOfWeekNo(2017,30) );  
         //System.out.println("结束时间:" + cd.getEndDayOfWeekNo(2017,30) );    

		 //getWeekByDate(new Date());
        // Map<String, Integer> weekByCanlender = getWeekByCanlender(DateKit.parseDate("2016-01-02"),DateKit.parseDate("2017-7-28"));
        // System.out.println(weekByCanlender);  
     }  
       
     /** 
      * get first date of given month and year 
      * @param year 
      * @param month 
      * @return 
      */  
     public String getFirstDayOfMonth(int year,int month){  
         String monthStr = month < 10 ? "0" + month : String.valueOf(month);  
         return year + "-"+monthStr+"-" +"01";  
     }  
       
     /** 
      * get the last date of given month and year 
      * @param year 
      * @param month 
      * @return 
      */  
     public String getLastDayOfMonth(int year,int month){  
         Calendar calendar = Calendar.getInstance();  
         calendar.set(Calendar.YEAR , year);  
         calendar.set(Calendar.MONTH , month - 1);  
         calendar.set(Calendar.DATE , 1);  
         calendar.add(Calendar.MONTH, 1);  
         calendar.add(Calendar.DAY_OF_YEAR , -1);  
         return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +  
                calendar.get(Calendar.DAY_OF_MONTH);  
     }  
       
     /** 
      * get Calendar of given year 
      * @param year 
      * @return 
      */  
     private Calendar getCalendarFormYear(int year){  
         Calendar cal = Calendar.getInstance();  
         cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);        
         cal.set(Calendar.YEAR, year);  
         return cal;  
     }  
       
     /** 
      * get start date of given week no of a year 
      * @param year 
      * @param weekNo 
      * @return 
      */  
     public String getStartDayOfWeekNo(int year,int weekNo){  
         Calendar cal = getCalendarFormYear(year);  
         cal.set(Calendar.WEEK_OF_YEAR, weekNo);  
         return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +  
                cal.get(Calendar.DAY_OF_MONTH);      
           
     }  
       
     /** 
      * get the end day of given week no of a year. 
      * @param year 
      * @param weekNo 
      * @return 
      */  
     public String getEndDayOfWeekNo(int year,int weekNo){  
         Calendar cal = getCalendarFormYear(year);  
         cal.set(Calendar.WEEK_OF_YEAR, weekNo);  
         cal.add(Calendar.DAY_OF_WEEK, 6);  
         return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +  
                cal.get(Calendar.DAY_OF_MONTH);      
     }  
     
     public static String getWeekByDate(Date date) {
       Calendar c1=Calendar.getInstance();
       c1.setTime(date);
       c1.get(Calendar.YEAR);
       String yw = c1.get(Calendar.YEAR) + "-" + c1.get(Calendar.WEEK_OF_YEAR);
       return yw;
     }
     
     /**
      * 返回2个日期间有多少股票周
      * @param startDate 2012-02-01 开始日期
      * @param endDate  2014-02-01   结束日期
      * @return
      */
     public static void getStockWeeks(String  startDate,String endDate){
    	 Calendar c_begin = new GregorianCalendar();
         Calendar c_end = new GregorianCalendar();
         DateFormatSymbols dfs = new DateFormatSymbols(); 
         String[] weeks = dfs.getWeekdays();
         
         c_begin.set(2010, 3, 2); //Calendar的月从0-11，所以4月是3.
         c_end.set(2010, 4, 20); //Calendar的月从0-11，所以5月是4.

         int count = 1;
         c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
         
         while(c_begin.before(c_end)){
       System.out.println("第"+count+"周  日期："+new java.sql.Date(c_begin.getTime().getTime())+", "+weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);

          if(c_begin.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
           count++;
          }
          c_begin.add(Calendar.DAY_OF_YEAR, 1);
         }
     }
     
     public static Map<String,Integer> getWeekByCanlender(Date start, Date end) {
    	 Calendar c_begin = new GregorianCalendar();
         Calendar c_end = new GregorianCalendar();

         c_begin.setTime(start);
         c_end.setTime(end);

         int count = 1;
         c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
         
         Map<String,Integer> m = new TreeMap<String,Integer>();
         while(c_begin.before(c_end)){
        	 count = c_begin.get(Calendar.WEEK_OF_YEAR);
        	 m.put(c_begin.get(Calendar.YEAR)+"-"+count, count);
          c_begin.add(Calendar.DAY_OF_YEAR, 1);
         }
         return m;
     }

     public static TreeMap<String,Integer> getMonthByCanlender(Date start, Date end) {
    	 Calendar c_begin = new GregorianCalendar();
    	 Calendar c_end = new GregorianCalendar();
    	 
    	 c_begin.setTime(start);
    	 c_end.setTime(end);
    	 
    	 int count = 1;
    	 c_begin.add(Calendar.DAY_OF_YEAR, -1);  //结束日期下滚一天是为了包含最后一天
    	 c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
    	 
    	 TreeMap<String,Integer> m = new TreeMap<String,Integer>();
    	 /*while(c_begin.before(c_end)){
    		 count = c_begin.get(Calendar.MONTH);
    		 m.put(c_begin.get(Calendar.YEAR)+"-"+(count+1), (count+1));
    		 c_begin.add(Calendar.DAY_OF_YEAR, 1);
    	 }*/
    	 while(c_end.after(c_begin)){
    		 count = c_end.get(Calendar.MONTH);
    		 m.put(c_end.get(Calendar.YEAR)+"-"+(count+1), (count+1));
    		 c_end.add(Calendar.DAY_OF_YEAR, -1);
    	 }
    	 return m;
     }
}
