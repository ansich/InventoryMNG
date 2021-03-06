package com.example.david.inventorymng.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.david.inventorymng.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class Plot extends AppCompatActivity{

    private InventoryMNG4App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_view);

        this.app = (InventoryMNG4App) this.getApplication();

        //Transferir datos de una actividad a otra
        Intent datosEnviados = this.getIntent();

        ArrayList<String> topNoms = app.getTopNoms();
        ArrayList<Double> topCods = app.getTopNums();
        if(topCods.size()<5) {
            for (int i = topCods.size(); i <= 5; i++) {
                topCods.add(i, 0.0);
                topNoms.add(i,"");
            }
        }


    GraphView graph = (GraphView) this.findViewById(R.id.graph);
    BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
            //codigo, numero
            new DataPoint(0,  0+topCods.get(0)),
            new DataPoint(1,  0+topCods.get(1)),
            new DataPoint(2,  0+topCods.get(2)),
            new DataPoint(3,  0+topCods.get(3)),
            new DataPoint(4,  0+topCods.get(4))
            });
        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });


        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setSpacing(10);

        // legend
        series.setTitle("0: " + app.getTopNoms().get(0) +
                        "  / 1: " + topNoms.get(1) +
                        "  / 2: " + topNoms.get(2) +
                        "  / 3: " + topNoms.get(3) +
                        "  / 4: " + topNoms.get(4) );

        series.setValuesOnTopSize(45);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setFixedPosition(244,0);
        graph.getLegendRenderer().setTextColor(Color.WHITE);
    }
}
