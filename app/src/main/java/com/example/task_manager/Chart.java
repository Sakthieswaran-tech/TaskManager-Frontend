package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chart extends AppCompatActivity {
    BarChart barChart;
    //    ArrayList<String> dates = new ArrayList<>();
    String dates[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        barChart = findViewById(R.id.barchart);

        Call<List<GraphData>> call = APIClient.getTaskManage(Chart.this).getTaskByGroup();
        call.enqueue(new Callback<List<GraphData>>() {
            @Override
            public void onResponse(Call<List<GraphData>> call, Response<List<GraphData>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Chart.this, "Check console", Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<BarEntry> list = new ArrayList<>();
                    try {
                        dates = new String[response.body().size()];
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                        for (int i = 0; i < response.body().size(); i++) {
//                            Date date= simpleDateFormat.parse(String.valueOf(response.body().get(i).getCompleted_count()));
//                            simpleDateFormat.setTimeZone(TimeZone.getDefault());
                            System.out.println("Response" + response.body().get(i).getCompleted_count());
                            String format = simpleDateFormat.format(response.body().get(i).getCompleted_count());
                            System.out.println("Formatted" + format);
                            dates[i] = format;
                            System.out.println("List" + dates[i]);
                            list.add(new BarEntry(i, response.body().get(i).getId_count()));
                        }
                        createChart(list, dates);

                    } catch (Exception e) {
                        Toast.makeText(Chart.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GraphData>> call, Throwable t) {
                Toast.makeText(Chart.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createChart(ArrayList<BarEntry> list, String[] dates) {
        BarDataSet barDataSet = new BarDataSet(list, "Completed details");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        barChart.setData(barData);
        barChart.invalidate();

    }
}