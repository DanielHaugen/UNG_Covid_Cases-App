package com.brukernavn.ung_cases;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class SplashScreen extends AsyncTask<Void, Void, CasesDB> {

    Context ctxt;
    Dialog splash;

    private Exception exception;

    private HashMap<String, Integer> dateHM = new HashMap<>();
    private HashMap<String, Integer> personHM = new HashMap<>();
    private HashMap<String, Integer> campusHM = new HashMap<>();

    private HashMap<Integer, HashMap<String, Integer>> dateCampusHM = new HashMap<>();

    List<CasesTable> tmp_list = new ArrayList<>();

    public SplashScreen(Context ctxt)
    {
        this.ctxt = ctxt;
    }

    @Override
    protected void onPreExecute()
    {
        splash = new Dialog(ctxt, R.style.full_screen);
        splash.setContentView(R.layout.splash);
        splash.show();
    }

    @Override
    protected CasesDB doInBackground(Void... params) {
        try {

            Document doc = Jsoup.connect("https://ung.edu/together/managing-covid.php").get();

            Element disclaimer = doc.selectFirst("p > em");
            String lastUpdated = disclaimer.text().substring(0, disclaimer.text().indexOf(" This"));

            // Stores a parsed table, but only pulls the first table on the page which should be the most recent month
            Elements tables = doc.select("div.no-sidebar-2-col-large > div > div.responsive-stacktable table");

            Element last14days = tables.get(0);
            Element sinceAug1st = tables.get(1);

            CasesTable cTable14days = new CasesTable();
            cTable14days = parseTable(last14days);

            CasesTable cTableAug1st = new CasesTable();
            cTableAug1st = parseTable(sinceAug1st);

            Elements rowsSeries14days = doc.select("div.accordionContainer table tr");
            dateCampusHM = getInnerMap();

            HashMap<Integer, Integer> dayCounts = getMap();

            for (Element row: rowsSeries14days) {
                List<String> cols = row.select("td").eachText();

                if (cols.size() > 0) {
                    if (cols.get(2).indexOf(' ') > -1)
                        cols.set(2, cols.get(2).substring(0,cols.get(2).indexOf(' ')));

                    String colZero = cols.get(0);
                    colZero = colZero.substring(colZero.indexOf('/')+1, colZero.lastIndexOf('/'));
                    // Account for number of cases per date
                    if (dateHM.containsKey(colZero))
                        dateHM.put(colZero, dateHM.get(colZero)+1);
                    else
                        dateHM.put(colZero, 1);

                    int tmp_idx = Integer.parseInt(colZero);
                    if (dayCounts.containsKey(tmp_idx))
                        dayCounts.put(tmp_idx, dayCounts.get(tmp_idx)+1);
                    else
                        dayCounts.put(tmp_idx, 1);

                    if(dateCampusHM.get(tmp_idx) != null) {
                        if (dateCampusHM.get(tmp_idx).containsKey(cols.get(2)))
                            dateCampusHM.get(tmp_idx).put(cols.get(2), dateCampusHM.get(tmp_idx).get(cols.get(2)) + 1);
                        else
                            dateCampusHM.get(tmp_idx).put(cols.get(2), 1);
                    }

                    // Account for number of cases per person type
                    if (personHM.containsKey(cols.get(1)))
                        personHM.put(cols.get(1), personHM.get(cols.get(1))+1);
                    else
                        personHM.put(cols.get(1), 1);

                    // Account for number of cases per campus
                    if (campusHM.containsKey(cols.get(2)))
                        campusHM.put(cols.get(2), campusHM.get(cols.get(2))+1);
                    else
                        campusHM.put(cols.get(2), 1);
                }
            }

            tmp_list.add(cTableAug1st);
            tmp_list.add(cTable14days);

            return new CasesDB(rowsSeries14days, dateCampusHM, dayCounts, dateHM, personHM, campusHM, tmp_list);
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(CasesDB result)
    {
        MainActivity.setCasesDB(result);
        splash.dismiss();
    }

    public CasesTable parseTable(Element tmpTable) {
        CasesTable tmp_cTable = new CasesTable();

        List<String> colHeaders = tmpTable.select("th").eachText();
        tmp_cTable.setColHeaders(colHeaders);

        String disc = tmpTable.selectFirst("caption").text();
        tmp_cTable.setDisclaimer(disc);

        Elements rows = tmpTable.select("tbody tr");
        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            Element row = rows.get(i);
            List<String> cols = row.select("td").eachText();
            tmp_cTable.addRow(cols);
        }

        return tmp_cTable;
    }

    public static HashMap<Integer, Integer> getMap(){
        HashMap<Integer, Integer> tmpMap  = new HashMap<>();
        Calendar cal = new GregorianCalendar();
        int today = cal.getTime().getDate();

        cal.add(Calendar.DAY_OF_MONTH, -14);
        while(cal.getTime().getDate() != today) {
            tmpMap.put(cal.getTime().getDate(), 0);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return tmpMap;
    }

    public static HashMap<Integer, HashMap<String, Integer>> getInnerMap(){
        HashMap<Integer, HashMap<String, Integer>> tmpMap  = new HashMap<>();
        Calendar cal = new GregorianCalendar();
        int today = cal.getTime().getDate();

        cal.add(Calendar.DAY_OF_MONTH, -14);
        while(cal.getTime().getDate() != today) {
            tmpMap.put(cal.getTime().getDate(), new HashMap<String, Integer>());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return tmpMap;
    }
}
