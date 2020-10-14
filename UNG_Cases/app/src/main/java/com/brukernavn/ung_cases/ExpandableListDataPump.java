package com.brukernavn.ung_cases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
        HashMap<String, Integer> inner = new HashMap<>();

        List<String> tmpList;
        List<String> days = getListDays();
        Collections.reverse(days);

        String tmpStr;
        Integer tmpVal = 0;
        for(String str: days){
            tmpList = new ArrayList<String>();

            tmpStr = str.substring(0,str.lastIndexOf(',')-2);
            tmpStr = tmpStr.substring(tmpStr.lastIndexOf(' ')).trim();

            if(MainActivity.getCasesDB() != null) {
                inner = MainActivity.getCasesDB().getDateCampusHM().get(Integer.parseInt(tmpStr));

                for (Map.Entry<String, Integer> set : inner.entrySet())
                    tmpList.add(set.getKey() + " - " + set.getValue());

                tmpVal = MainActivity.getCasesDB().getDateHM().get(tmpStr);
            }
            if (tmpVal == null)
                tmpVal = 0;
            expandableListDetail.put(str + " - " + tmpVal, tmpList);
        }

        return expandableListDetail;
    }

    public static List<String> getListDays(){
        List<String> tmpList  = new ArrayList<>();
        Calendar cal = new GregorianCalendar();
        int today = cal.getTime().getDate();
        String dateStr = "";

        cal.add(Calendar.DAY_OF_MONTH, -14);
        while(cal.getTime().getDate() != today) {
            dateStr = new SimpleDateFormat("EEEE").format(cal.getTime()) + ", ";
            dateStr += new SimpleDateFormat("MMMM").format(cal.getTime()) + " ";
            dateStr += ordinal(cal.getTime().getDate()) + ", " + (cal.getTime().getYear()+1900);

            tmpList.add(dateStr);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return tmpList;
    }

    public static String ordinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }
}
