package com.honda.hdm.warrantiesmg.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;


@Component
public class DateUtils {
	
	public String generateTimesTamWithFormat(String format) {
		try {
			Date date = new Date();
	        Timestamp timestamp2 = new Timestamp(date.getTime());	        
	         SimpleDateFormat sdf3 = new SimpleDateFormat(format);
	         return sdf3.format(timestamp2);
		} catch(Exception e) {			
			return null;
		}
	}
	
	public Date convertStringToDate(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	
	public String convertTimestampToString(Date timestamp, String format) {		 
		Date date = new Date();
        date.setTime(timestamp.getTime());
        String formattedDate = new SimpleDateFormat(format).format(date);
        return formattedDate;
	}

	public String convertTimestampToString(Timestamp timestamp){
		String pattern = "dd 'de' MMMMM 'de' yyyy";
		Date date = new Date();
	    date.setTime(timestamp.getTime());	    
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es","ar"));
	    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));	    
	    String formattedDate = simpleDateFormat.format(date);	    
	    return formattedDate;
	}
	
	
	public String format(Date date, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(date);
	}
	
	public Timestamp stringToTimeStamp(String date) {
		
		try {
		  Date dateParsed = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
		  Timestamp timestamp = new Timestamp(dateParsed.getTime());
		  return timestamp;
		} catch (ParseException exception) {
		  exception.printStackTrace();
		  return null;
		}

	}
	
	public Timestamp dateToTimeStamp(String format, String strDate) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat(format);
			java.util.Date date = (java.util.Date) inputFormat.parse(strDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.MILLISECOND, 0);
			return new Timestamp(cal.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String timestampToString(Timestamp date, String format) {
		return date == null ? null : new SimpleDateFormat(format).format(date);
	}
	
	public Date parseDate(String date) throws ParseException {
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(date);
	}
}
