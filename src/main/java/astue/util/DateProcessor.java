
package astue.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateProcessor {
		
		private Date date;
		private  int[] arr=new int[4];
		private Map<String,Integer> secs;
		private Map<String,Integer> mins;
		private Map<String,Integer> hours;
		private Map<String,Integer> day;
		private Map<String,Integer> month;
		private Map<String,Integer> year;
		
		public static DateProcessor getProcessor() {
			return new DateProcessor();
		}
		
		private DateProcessor() {
			date=new Date();
		}
	
		public int[] getDateRegs(){
			Map<String,Integer> secs=getTwo("ss");
			 Map<String,Integer> mins=getTwo("mm");
			 Map<String,Integer> hours=getTwo("HH");
			 Map<String,Integer> day=getTwo("dd");
			 Map<String,Integer> month=getTwo("MM");
			 Map<String,Integer> year=getFour("yyyy");
			arr[0]=(secs.get("mostSign")<<12|secs.get("leastSign")<<8);
			arr[1]= (hours.get("mostSign")<<12|hours.get("leastSign")<<8|mins.get("mostSign")<<4|mins.get("leastSign")<<0);
			arr[2]= (month.get("mostSign")<<12|month.get("leastSign")<<8|day.get("mostSign")<<4|day.get("leastSign")<<0);
			arr[3]=(year.get("mostSign")<<12|year.get("secondSign")<<8|year.get("thirdSign")<<4|year.get("leastSign")<<0);
			
			return arr;
		}
	
	
	
	
	private  Map<String,Integer> getTwo(String pat) {
		String pattern = pat;
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pat);
		String dateString = simpleDateFormat.format(date);
		Integer num=new Integer(dateString);
		Integer mostSign= (int) Math.floor(new Integer(num/10));
		Integer leastSign= num-mostSign*10;
		Map<String,Integer> tempMap=new HashMap<>();
		tempMap.put("mostSign", mostSign);
		tempMap.put("leastSign", leastSign);
		return tempMap;
	}
	private  Map<String,Integer> getFour(String pat) {
		String pattern = pat;
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pat);
		String dateString = simpleDateFormat.format(date);
		Integer num=new Integer(dateString);
		
		Integer mostSign= (int) Math.floor(new Integer(num/1000));
		Integer secondSign= (int) Math.floor(new Integer(num/100))-mostSign*10;
		Integer thirdSign= (int) Math.floor(new Integer(num/10))-mostSign*100- secondSign*10;
		Integer leastSign= num-thirdSign*10-secondSign*100-mostSign*1000;
		Map<String,Integer> tempMap=new HashMap<>();
		tempMap.put("mostSign", mostSign);
		tempMap.put("secondSign", secondSign);
		tempMap.put("thirdSign", thirdSign);
		tempMap.put("leastSign", leastSign);
		return tempMap;
	}
	
}
