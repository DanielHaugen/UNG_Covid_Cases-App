package com.brukernavn.ung_cases.ui.home;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.brukernavn.ung_cases.CasesDB;
import com.brukernavn.ung_cases.CasesTable;
import com.brukernavn.ung_cases.MainActivity;
import com.brukernavn.ung_cases.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private CasesDB casesDB;

    private View scrollView;
    private TextView dahlTextTotal;
    private TextView dahlText14day;
    private TextView gainesTextTotal;
    private TextView gainesText14day;
    private TextView oconTextTotal;
    private TextView oconText14day;
    private TextView cummingTextTotal;
    private TextView cummingText14day;
    private TextView studTextTotal;
    private TextView studText14day;
    private TextView emplTextTotal;
    private TextView emplText14day;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        scrollView = (View) getView().findViewById(R.id.mainScrollView);
        // Get references to programmatically manipulated TextViews
        dahlTextTotal = (TextView) getView().findViewById(R.id.dahlonegaTextTotal);
        dahlText14day = (TextView) getView().findViewById(R.id.dahlonegaText14day);
        gainesTextTotal = (TextView) getView().findViewById(R.id.gainesvilleTextTotal);
        gainesText14day = (TextView) getView().findViewById(R.id.gainesvilleText14day);
        oconTextTotal = (TextView) getView().findViewById(R.id.oconeeTextTotal);
        oconText14day = (TextView) getView().findViewById(R.id.oconeeText14day);
        cummingTextTotal = (TextView) getView().findViewById(R.id.cummingTextTotal);
        cummingText14day = (TextView) getView().findViewById(R.id.cummingText14day);
        studTextTotal = (TextView) getView().findViewById(R.id.studentTextTotal);
        studText14day = (TextView) getView().findViewById(R.id.studentText14day);
        emplTextTotal = (TextView) getView().findViewById(R.id.employeeTextTotal);
        emplText14day = (TextView) getView().findViewById(R.id.employeeText14day);

        if(MainActivity.getCasesDB() == null) {
            WebScraper tmpScraper = new WebScraper();
            tmpScraper.execute();
        } else {
            // Parsing already occurred
            CasesTable totalTable = MainActivity.getCasesDB().getCasesTables().get(0);
            CasesTable recentTable = MainActivity.getCasesDB().getCasesTables().get(1);

            // Programmatically set the TextView values
            // Dahlonega
            dahlTextTotal.setText(String.valueOf(totalTable.getRowTotal("Dahlonega")));
            setRecentTextImage(dahlText14day, recentTable.getRowTotal("Dahlonega"), (ImageView) getView().findViewById(R.id.dahlonegaArrow));
            // Gainesville
            gainesTextTotal.setText(String.valueOf(totalTable.getRowTotal("Gainesville")));
            setRecentTextImage(gainesText14day, recentTable.getRowTotal("Gainesville"), (ImageView) getView().findViewById(R.id.gainesvilleArrow));
            // Oconee
            oconTextTotal.setText(String.valueOf(totalTable.getRowTotal("Oconee")));
            setRecentTextImage(oconText14day, recentTable.getRowTotal("Oconee"), (ImageView) getView().findViewById(R.id.oconeeArrow));
            // Cumming
            cummingTextTotal.setText(String.valueOf(totalTable.getRowTotal("Cumming")));
            setRecentTextImage(cummingText14day, recentTable.getRowTotal("Cumming"), (ImageView) getView().findViewById(R.id.cummingArrow));
            // Student
            studTextTotal.setText(String.valueOf(totalTable.getColTotal("Student")));
            setRecentTextImage(studText14day, recentTable.getColTotal("Student"), (ImageView) getView().findViewById(R.id.studentArrow));
            // Employee
            emplTextTotal.setText(String.valueOf(totalTable.getColTotal("Employee")));
            setRecentTextImage(emplText14day, recentTable.getColTotal("Employee"), (ImageView) getView().findViewById(R.id.employeeArrow));

        }
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

    public void refreshViews(){

        CasesTable totalTable = MainActivity.getCasesDB().getCasesTables().get(0);
        CasesTable recentTable = MainActivity.getCasesDB().getCasesTables().get(1);

        // Programmatically set the TextView values
        // Dahlonega
        dahlTextTotal.setText(String.valueOf(totalTable.getRowTotal("Dahlonega")));
        setRecentTextImage(dahlText14day, recentTable.getRowTotal("Dahlonega"), (ImageView) getView().findViewById(R.id.dahlonegaArrow));
        // Gainesville
        gainesTextTotal.setText(String.valueOf(totalTable.getRowTotal("Gainesville")));
        setRecentTextImage(gainesText14day, recentTable.getRowTotal("Gainesville"), (ImageView) getView().findViewById(R.id.gainesvilleArrow));
        // Oconee
        oconTextTotal.setText(String.valueOf(totalTable.getRowTotal("Oconee")));
        setRecentTextImage(oconText14day, recentTable.getRowTotal("Oconee"), (ImageView) getView().findViewById(R.id.oconeeArrow));
        // Cumming
        cummingTextTotal.setText(String.valueOf(totalTable.getRowTotal("Cumming")));
        setRecentTextImage(cummingText14day, recentTable.getRowTotal("Cumming"), (ImageView) getView().findViewById(R.id.cummingArrow));
        // Student
        studTextTotal.setText(String.valueOf(totalTable.getColTotal("Student")));
        setRecentTextImage(studText14day, recentTable.getColTotal("Student"), (ImageView) getView().findViewById(R.id.studentArrow));
        // Employee
        emplTextTotal.setText(String.valueOf(totalTable.getColTotal("Employee")));
        setRecentTextImage(emplText14day, recentTable.getColTotal("Employee"), (ImageView) getView().findViewById(R.id.employeeArrow));
    }

    public void useCovidDB(CasesDB casesDB) {
        if(casesDB != null) {
            this.casesDB = casesDB;

            /**
            System.out.println("\nTotal Cases, in last 14 days, per Date: ");
            for (HashMap.Entry<String, Integer> entry : this.casesDB.getDateHM().entrySet())
                System.out.printf(" %-8s : %-5d (%.2f%%)%n", entry.getKey(), entry.getValue(),
                        ((double) entry.getValue() / this.casesDB.getRowsSeries14days().size() * 100));

            System.out.println("\nTotal Cases, in last 14 days, per Person Type: ");
            for (HashMap.Entry<String, Integer> entry : this.casesDB.getPersonHM().entrySet())
                System.out.printf(" %-8s : %-5d (%.2f%%)%n", entry.getKey(), entry.getValue(),
                        ((double) entry.getValue() / this.casesDB.getRowsSeries14days().size() * 100));

            System.out.println("\nTotal Cases, in last 14 days, per Campus: ");
            for (HashMap.Entry<String, Integer> entry : this.casesDB.getCampusHM().entrySet())
                System.out.printf(" %-11s : %-5d (%.2f%%)%n", entry.getKey(), entry.getValue(),
                        ((double) entry.getValue() / this.casesDB.getRowsSeries14days().size() * 100));
            **/
            CasesTable totalTable = this.casesDB.getCasesTables().get(0);
            CasesTable recentTable = this.casesDB.getCasesTables().get(1);

            // Programmatically set the TextView values
            // Dahlonega
            dahlTextTotal.setText(String.valueOf(totalTable.getRowTotal("Dahlonega")));
            setRecentTextImage(dahlText14day, recentTable.getRowTotal("Dahlonega"), (ImageView) getView().findViewById(R.id.dahlonegaArrow));
            // Gainesville
            gainesTextTotal.setText(String.valueOf(totalTable.getRowTotal("Gainesville")));
            setRecentTextImage(gainesText14day, recentTable.getRowTotal("Gainesville"), (ImageView) getView().findViewById(R.id.gainesvilleArrow));
            // Oconee
            oconTextTotal.setText(String.valueOf(totalTable.getRowTotal("Oconee")));
            setRecentTextImage(oconText14day, recentTable.getRowTotal("Oconee"), (ImageView) getView().findViewById(R.id.oconeeArrow));
            // Cumming
            cummingTextTotal.setText(String.valueOf(totalTable.getRowTotal("Cumming")));
            setRecentTextImage(cummingText14day, recentTable.getRowTotal("Cumming"), (ImageView) getView().findViewById(R.id.cummingArrow));
            // Student
            studTextTotal.setText(String.valueOf(totalTable.getColTotal("Student")));
            setRecentTextImage(studText14day, recentTable.getColTotal("Student"), (ImageView) getView().findViewById(R.id.studentArrow));
            // Employee
            emplTextTotal.setText(String.valueOf(totalTable.getColTotal("Employee")));
            setRecentTextImage(emplText14day, recentTable.getColTotal("Employee"), (ImageView) getView().findViewById(R.id.employeeArrow));
        }
    }

    private void setRecentTextImage(TextView textView, String count, ImageView imageView){
        textView.setText(String.valueOf(Math.abs(Integer.parseInt(count))));
        if(Integer.parseInt(count) <= 0) {
            textView.setTextColor(getResources().getColor(R.color.green));
            imageView.setImageResource(R.drawable.ic_arrow_drop_24);
        }
    }

    class WebScraper extends AsyncTask<Void, Void, CasesDB> {

        private Exception exception;

        private HashMap<String, Integer> dateHM = new HashMap<>();
        private HashMap<String, Integer> personHM = new HashMap<>();
        private HashMap<String, Integer> campusHM = new HashMap<>();

        private HashMap<Integer, HashMap<String, Integer>> dateCampusHM = new HashMap<>();

        List<CasesTable> tmp_list = new ArrayList<>();

        @RequiresApi(api = Build.VERSION_CODES.O)
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

        protected void onPostExecute(CasesDB result) {
            // TODO: check this.exception
            // TODO: do something with the feed
            useCovidDB(result);
            MainActivity.setCasesDB(result);
        }
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