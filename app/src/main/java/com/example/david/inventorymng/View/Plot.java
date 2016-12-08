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
        //final int pos = datosEnviados.getExtras().getInt( "pos" );

        ArrayList<Double> topProducts = app.getTopProducts();
        ArrayList<Double> topCods = app.getTopCods();
        GraphView graph = (GraphView) this.findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                //codigo, numero
                new DataPoint(topCods.get(0), topProducts.get(0)),
                new DataPoint(topCods.get(1), topProducts.get(1)),
                new DataPoint(topCods.get(2), topProducts.get(2)),
                new DataPoint(topCods.get(3), topProducts.get(3)),
                new DataPoint(topCods.get(4), topProducts.get(4))
        });
        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

        series.setSpacing(50);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        //series.setValuesOnTopSize(50);

        // legend
        series.setTitle("productos con mas stock");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setFixedPosition(244,0);
        graph.getLegendRenderer().setTextColor(Color.WHITE);
    }
}