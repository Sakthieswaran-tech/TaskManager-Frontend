package com.example.task_manager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

//public class PickStartDate extends DialogFragment {
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Calendar calendar=Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);
//        int month=calendar.get(Calendar.MONTH);
//        int day=calendar.get(Calendar.DAY_OF_MONTH);
//        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(),year,month,day);
//    }
//}
public class PickStartDate extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),
                hour,minute,false );
    }

//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        TextView startTime=view.findViewById(R.id.startTimePost);
//        TextView completeTime=view.findViewById(R.id.completeTimePost);
//
//    }
}
