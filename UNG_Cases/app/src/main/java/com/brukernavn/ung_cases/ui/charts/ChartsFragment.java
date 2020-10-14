package com.brukernavn.ung_cases.ui.charts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brukernavn.ung_cases.CasesTable;
import com.brukernavn.ung_cases.MainActivity;
import com.brukernavn.ung_cases.R;

public class ChartsFragment extends Fragment {

    private ChartsViewModel chartsViewModel;

    private TextView tmpStudTextView;
    private TextView tmpEmpTextView;
    private TextView tmpCaseTextView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_charts, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        getView().findViewById(R.id.resButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ung.edu/together/managing-covid.php";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    /** Building the Recent Table **/
        if(MainActivity.getCasesDB() != null) {
            CasesTable recentTable = MainActivity.getCasesDB().getCasesTables().get(1);

            /** Cumming Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.CummingStudTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.CummingEmpTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.CummingCaseTextView);

            setTextViews(recentTable, "Cumming",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Dahlonega Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.DahStudTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.DahEmpTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.DahCaseTextView);

            setTextViews(recentTable, "Dahlonega",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Gainesville Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.GainStudTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.GainEmpTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.GainCaseTextView);

            setTextViews(recentTable, "Gainesville",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Oconee Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.OconStudTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.OconEmpTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.OconCaseTextView);

            setTextViews(recentTable, "Oconee",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Total Row **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.totalStudTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.totalEmpTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.totalCaseTextView);

            tmpStudTextView.setText(recentTable.getColTotal("Student"));
            tmpEmpTextView.setText(recentTable.getColTotal("Employee"));
            tmpCaseTextView.setText(recentTable.getTotal());

            /** Building the Total Table **/
            CasesTable totalTable = MainActivity.getCasesDB().getCasesTables().get(0);

            /** Cumming Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.CummingStudFullTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.CummingEmpFullTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.CummingCaseFullTextView);

            setTextViews(totalTable, "Cumming",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Dahlonega Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.DahStudFullTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.DahEmpFullTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.DahCaseFullTextView);

            setTextViews(totalTable, "Dahlonega",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Gainesville Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.GainStudFullTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.GainEmpFullTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.GainCaseFullTextView);

            setTextViews(totalTable, "Gainesville",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Oconee Campus **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.OconStudFullTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.OconEmpFullTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.OconCaseFullTextView);

            setTextViews(totalTable, "Oconee",
                    tmpStudTextView, tmpEmpTextView, tmpCaseTextView);

            /** Total Row **/
            tmpStudTextView = (TextView) getView().findViewById(R.id.totalStudFullTextView);
            tmpEmpTextView = (TextView) getView().findViewById(R.id.totalEmpFullTextView);
            tmpCaseTextView = (TextView) getView().findViewById(R.id.totalCaseFullTextView);

            tmpStudTextView.setText(totalTable.getColTotal("Student"));
            tmpEmpTextView.setText(totalTable.getColTotal("Employee"));
            tmpCaseTextView.setText(totalTable.getTotal());
        }
    }

    private void setTextViews(CasesTable cTable, String campus,
                              TextView studV, TextView empV, TextView caseV){

        studV.setText(cTable.getCell(campus, "Student"));
        empV.setText(cTable.getCell(campus, "Employee"));
        caseV.setText(cTable.getRowTotal(campus));

    }

}