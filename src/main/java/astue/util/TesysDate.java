package astue.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TesysDate {

    private LocalDateTime date;
    private  int[] arr=new int[4];
    private String fullPattern="dd MM yyyy HH:mm:ss";
    
    public static TesysDate getProcessor() {
        return new TesysDate();
    }
    public static TesysDate getProcessor(LocalDateTime date) {
    	
    	return new TesysDate(date);
    }

    private TesysDate() {
        date=LocalDateTime.now();
    }
    private TesysDate(LocalDateTime date) {
    	this.date=date;
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern); 
        String dateString = format.format(date);
        Integer num=Integer.valueOf(dateString);
        Integer mostSign= (int) Math.floor(Integer.valueOf(num/10));
        Integer leastSign= num-mostSign*10;
        Map<String,Integer> tempMap=new HashMap<>();
        tempMap.put("mostSign", mostSign);
        tempMap.put("leastSign", leastSign);
        return tempMap;
    }
    private  Map<String,Integer> getFour(String pat) {
		 String pattern = pat;
	     DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern); 
	     String dateString = format.format(date);
        Integer num=Integer.valueOf(dateString);

        Integer mostSign= (int) Math.floor(Integer.valueOf(num/1000));
        Integer secondSign= (int) Math.floor(Integer.valueOf(num/100))-mostSign*10;
        Integer thirdSign= (int) Math.floor(Integer.valueOf(num/10))-mostSign*100- secondSign*10;
        Integer leastSign= num-thirdSign*10-secondSign*100-mostSign*1000;
        Map<String,Integer> tempMap=new HashMap<>();
        tempMap.put("mostSign", mostSign);
        tempMap.put("secondSign", secondSign);
        tempMap.put("thirdSign", thirdSign);
        tempMap.put("leastSign", leastSign);
        return tempMap;
    }

}
