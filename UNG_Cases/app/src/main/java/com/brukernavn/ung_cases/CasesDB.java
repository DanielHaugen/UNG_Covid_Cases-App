package com.brukernavn.ung_cases;

import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;

public class CasesDB {
    private HashMap<Integer, HashMap<String, Integer>> dateCampusHM = new HashMap<>();
    private HashMap<String, Integer> dateHM, personHM, campusHM;
    private HashMap<Integer, Integer> dayCounts;
    private List<CasesTable> casesTables;
    private Elements rowsSeries14days;

    public CasesDB(Elements rowsSeries14days,
                   HashMap<Integer, HashMap<String, Integer>> dateCampusHM,
                   HashMap<Integer, Integer> dayCounts,
                   HashMap<String, Integer> dateHM,
                   HashMap<String, Integer> personHM,
                   HashMap<String, Integer> campusHM,
                   List<CasesTable> casesTables){
        this.dateCampusHM = dateCampusHM;
        this.rowsSeries14days = rowsSeries14days;
        this.dayCounts = dayCounts;
        this.dateHM = dateHM;
        this.personHM = personHM;
        this.campusHM = campusHM;
        this.casesTables = casesTables;
    }

    public Elements getRowsSeries14days() {
        return rowsSeries14days;
    }

    public HashMap<String, Integer> getDateHM(){
        return dateHM;
    }

    public HashMap<String, Integer> getPersonHM(){
        return personHM;
    }

    public HashMap<String, Integer> getCampusHM(){
        return campusHM;
    }

    public List<CasesTable> getCasesTables(){
        return casesTables;
    }

    public HashMap<Integer, Integer> getDayCounts() {
        return dayCounts;
    }

    public HashMap<Integer, HashMap<String, Integer>> getDateCampusHM() {
        return dateCampusHM;
    }

    public String toString(){
        return Integer.toString(getCampusHM().size()) + " & " + getCampusHM();
    }
}
