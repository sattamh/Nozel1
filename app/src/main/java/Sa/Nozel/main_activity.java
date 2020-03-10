package Sa.Nozel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import Sa.Nozel.R;


public class main_activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    private TextView checkinDate;
    private TextView checkoutDate;
    Calendar mCurrentDate;
    int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mCurrentDate= Calendar.getInstance();
        checkinDate = findViewById(R.id.txtvcheckinDate);
        checkoutDate = findViewById(R.id.txtvcheckoutDate);
        day= mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mCurrentDate.get(Calendar.MONTH);
        year= mCurrentDate.get(Calendar.YEAR);
        month=month+1;
        final String checkin=day+"/"+month+"/"+year;

        checkinDate.setText(checkin);
        day= day+1;
        final String checkout=day+"/"+month+"/"+year;
        checkoutDate.setText(checkout);


        checkoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(main_activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month= month+1;
                        checkoutDate.setText(dayOfMonth+"/"+month+"/"+year);

                    }
                },year, month, day);
                datePicker.show();
            }
        });
        checkinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month =month+1;
        String checkin =  dayOfMonth + "/" + month + "/" + year;
        checkinDate.setText("");
        checkinDate.setText(checkin);

    }



}

