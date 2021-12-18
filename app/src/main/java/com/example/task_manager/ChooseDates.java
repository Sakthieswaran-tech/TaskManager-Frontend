package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChooseDates extends AppCompatActivity {
    CalendarPickerView calendarPickerView;
    Button button;
    List<Date> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dates);

        calendarPickerView=findViewById(R.id.calendarpicker);
        button=findViewById(R.id.postdates);

        Date today=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);
        Date nextYear=calendar.getTime();

        calendarPickerView.init(today,nextYear).inMode(CalendarPickerView.SelectionMode.MULTIPLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=calendarPickerView.getSelectedDates();
                Toast.makeText(ChooseDates.this,"Dates noted",Toast.LENGTH_LONG).show();
                ArrayList<String> arrayList=new ArrayList<>();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                for (int i=0;i<list.size();i++){
                    String mark=simpleDateFormat.format(list.get(i));
                    System.out.println("##########################"+mark);
                    arrayList.add(i,mark);
                }
                Intent intent=new Intent();
                intent.putStringArrayListExtra("dates",arrayList);
                setResult(2,intent);
                finish();
            }
        });
    }

//    @Override
//    public void finish() {
//        ArrayList<String> arrayList=new ArrayList<>();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        for (int i=0;i<list.size();i++){
//            String mark=simpleDateFormat.format(list.get(i));
//            System.out.println("##########################"+mark);
//            arrayList.add(i,mark);
//        }
//        Intent intent=new Intent();
//        intent.putStringArrayListExtra("dates",arrayList);
//        setResult(2,intent);
//        super.finish();
//    }
}