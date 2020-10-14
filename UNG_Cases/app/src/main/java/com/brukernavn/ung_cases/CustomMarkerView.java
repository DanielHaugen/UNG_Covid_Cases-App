package com.brukernavn.ung_cases;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent;
    private RelativeLayout rel;

    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);

        // This markerView only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
        rel = (RelativeLayout) findViewById(R.id.relLayout);
        //rel.offsetLeftAndRight(getXOffset());
        rel.offsetTopAndBottom(getYOffset());
    }

    // Callbacks everytime the MarkerView is redrawn, can be used to update the content (UI)
    @Override
    public void refreshContent(Entry e, Highlight highlight){
        // Set the entry-value as the display text
        if((int) e.getY() == 1)
            tvContent.setText((int) e.getY() + " case");
        else
            tvContent.setText((int) e.getY() + " cases");

        //tvContent.setText("(" + (int) e.getX() + ") " + (int) e.getY() + " cases");
    }

    //@Override
    public int getXOffset() {
        // This will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    //@Override
    public int getYOffset() {
        // This will cause the marker-view to be above the selected value
        return -getHeight();
    }
}
