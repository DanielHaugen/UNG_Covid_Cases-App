package com.brukernavn.ung_cases.ui.recent;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.brukernavn.ung_cases.CustomExpandableListAdapter;
import com.brukernavn.ung_cases.CustomMarkerView;
import com.brukernavn.ung_cases.ExpandableListDataPump;
import com.brukernavn.ung_cases.MainActivity;
import com.brukernavn.ung_cases.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class RecentFragment extends Fragment {

    private LineChart mCharts;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recent, container, false);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        mCharts = (LineChart) getView().findViewById(R.id.chart1);

        LineData data = getData(14, 100); // count of points = 14, range (height) = 100
        setupChart(mCharts, data, Color.argb(0,0,0,0));

        expandableListView = (ExpandableListView) getView().findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void setupChart(LineChart chart, LineData data, int color){
        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);

        chart.setBackgroundColor(color);
        chart.setViewPortOffsets(10, 0, 10, 0);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.custom_marker_view_layout);
        chart.setMarker(mv);
        chart.setData(data);
    }

    private LineData getData(int count, int range){
        ArrayList<Entry> yVals = new ArrayList<>();
        HashMap<Integer, Integer> tmpMap = new HashMap<>();
        if(MainActivity.getCasesDB() != null)
            tmpMap = MainActivity.getCasesDB().getDayCounts();

        int[] recent = new int[14];
        int tmp_idx = 0;

        // Needed in case they have cases listed from more than 14 days ago (IndexOutOfBounds)
        Calendar cal = new GregorianCalendar();
        int today = cal.getTime().getDate();


        cal.add(Calendar.DAY_OF_MONTH, -14);
        int dayInt;
        while(cal.getTime().getDate() != today) {
            dayInt = cal.getTime().getDate();

            if(MainActivity.getCasesDB() != null) {
                recent[tmp_idx] = tmpMap.get(dayInt);
                yVals.add(new Entry(tmp_idx++, tmpMap.get(dayInt)));
            } else {
                yVals.add(new Entry(tmp_idx++, 0));
            }

            //Iterate through the last 14 days
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        LineDataSet set1 = new LineDataSet(yVals, "Data Set");

        set1.setLineWidth(3f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setDrawHorizontalHighlightIndicator(false);

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);

        set1.setDrawFilled(true);
        set1.setFillColor(Color.CYAN);
        set1.setFillAlpha(80);

        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.gradient_graph);
        set1.setFillDrawable(drawable);

        return new LineData(set1);
    }

}