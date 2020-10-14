package com.brukernavn.ung_cases;

import java.util.ArrayList;
import java.util.List;

public class CasesTable {
    private List<String> rowHeaders, colHeaders;
    private List<List<String>> row;
    private String disclaimer;

    public CasesTable() {
        rowHeaders = new ArrayList<String>();
        colHeaders = new ArrayList<String>();
        row = new ArrayList<List<String>>();
        disclaimer = "";
    }

    public CasesTable(List<String> colHeaders){
        rowHeaders = new ArrayList<String>();
        colHeaders = new ArrayList<String>();
        row = new ArrayList<List<String>>();
        this.colHeaders = colHeaders;
        disclaimer = "";
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public List<String> getColHeaders() {
        return colHeaders;
    }

    public void setColHeaders(List<String> colHeaders) {
        this.colHeaders = colHeaders;
    }

    public List<String> getRow(String rowName) {
        for (List<String> i : row)
            if (i.get(0).equals(rowName))
                return i;

        return null;
    }

    public List<String> getCol(String colName) {
        int idx = colHeaders.indexOf(colName);
        List<String> tmpList = new ArrayList<>();
        tmpList.add(colHeaders.get(idx));

        for (List<String> i : row)
            tmpList.add(i.get(idx));

        return tmpList;
    }

    public String getCell(String rowName, String colName) {
        List<String> tmp_row = getRow(rowName);

        return (String) tmp_row.get(colHeaders.indexOf(colName));
    }

    public String getTotal() {
        return getCell(rowHeaders.get(rowHeaders.size()-1), colHeaders.get(colHeaders.size()-1));
    }

    public String getRowTotal(String rowName) {
        return getCell(rowName, colHeaders.get(colHeaders.size()-1));
    }

    public String getColTotal(String colName) {
        return getCell(rowHeaders.get(rowHeaders.size()-1), colName);
    }

    public void addRow(List<String> instance) {
        this.rowHeaders.add(instance.get(0));
        this.row.add(instance);
    }

    public String toString() {
        String str = String.format("%n* %s%n",getDisclaimer());

        for (String i : colHeaders)
            str += String.format("%-20s", i);
        for (List<String> list : row) {
            str += "\n";
            for (String col : list)
                str += String.format("%-20s", col);
        }

        return str + "\n";
    }
}
