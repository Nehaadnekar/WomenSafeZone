package com.neha.womensafezone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allyants.notifyme.NotifyMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class PeriodTracker extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    TextView editText_input, editText_output;
    CalendarView calender;
    String Date_new="";
    Button btnNotify;
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_tracker);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }

        editText_input = findViewById(R.id.input_date);
        editText_output = findViewById(R.id.output_date);
        calender = findViewById(R.id.calendarView);
        btnNotify= findViewById(R.id.btnNotify);

        final String c= editText_output.getText().toString();

        dpd = DatePickerDialog.newInstance(
                PeriodTracker.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        tpd = TimePickerDialog.newInstance(
                PeriodTracker.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        );

        calender.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                    @Override

                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String Date = dayOfMonth + "-" + (month + 1) + "-" + year;

                        editText_input.setText(Date);
                        if (dayOfMonth >= 1 && dayOfMonth <= 31) {
                            int dayGap = 28;
                            int nextDate = dayOfMonth + dayGap;
                            if (nextDate > 30) {
                                int periodDate = nextDate - 30;
                                String Date_new
                                        = periodDate + "-"
                                        + (month + 2) + "-" + year;

                                editText_output.setText(Date_new);
                            } else {
                                int periodDate = nextDate;
                                Date_new
                                        = periodDate + "-"
                                        + (month + 2) + "-" + year;

                                editText_output.setText(Date_new);
//
                            }

                        }
                    }

                });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(), "Datepickerdialog");
                String message= "Hey!! may be you have your periods today.Don't forget to take proper safety materials with you." +
                        "Thank you..";
                Toast.makeText(getApplicationContext(), "Reminder set for upcoming Period date,stay tuned, Stay safe", Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);
        Intent intent = new Intent(getApplicationContext(),PeriodTracker.class);
        intent.putExtra("test","I am a String");
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title("Period Tracker")
                .content("Beware!!You may menstruate today!!")
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(intent,"Snooze",false)
                .key("test")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(intent,"Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
                .build();
    }
}
