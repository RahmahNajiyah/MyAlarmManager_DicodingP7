package com.hafidzqode.myalarmmanager_dicodingp7;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//mengimplementasikan one-time-alarm untuk tanggal dan jam yang telah ditentukan.
//Fungsi ini bisa berguna untuk beragam fitur lain.
//Misalnya jika Anda membuat aplikasi tentang event
//todo 4
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvOneTimeDate, tvOneTimeTime;
    private TextView tvRepeatingTime;
    private EditText edtOneTimeMessage, edtRepeatingMessage;
    private Button btnOneTimeDate, btnOneTimeTime, btnOneTime, btnRepeatingTime, btnRepeating, btnCancelRepeatingAlarm;
    private Calendar calOneTimeDate, calOneTimeTime, calRepeatTimeTime;
    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOneTimeDate = (TextView)findViewById(R.id.tv_one_time_alarm_date);
        tvOneTimeTime = (TextView)findViewById(R.id.tv_one_time_alarm_time);
        edtOneTimeMessage = (EditText) findViewById(R.id.edt_one_time_alarm_message);
        btnOneTimeDate = (Button) findViewById(R.id.btn_one_time_alarm_date);
        btnOneTimeTime = (Button) findViewById(R.id.btn_one_time_alarm_time);
        btnOneTime = (Button) findViewById(R.id.btn_set_one_time_alarm);
        tvRepeatingTime = (TextView) findViewById(R.id.tv_repeating_alarm_time);
        edtRepeatingMessage = (EditText) findViewById(R.id.edt_repeating_alarm_message);
        btnRepeatingTime = (Button) findViewById(R.id.btn_repeating_time_alarm_time);
        btnRepeating = (Button) findViewById(R.id.btn_repeating_time_alarm);
        btnCancelRepeatingAlarm = (Button)findViewById(R.id.btn_cancel_repeating_alarm);

        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
        btnCancelRepeatingAlarm.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();

        if (!TextUtils.isEmpty(alarmPreference.getOneTimeDate())){
            setOneTimeText();
        }

        //todo 8 alarm manager 2 - repeating alarm
        if (!TextUtils.isEmpty(alarmPreference.getRepeatingTime())){
            setRepeatingText();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_one_time_alarm_date) {
            final Calendar currentDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calOneTimeDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    tvOneTimeDate.setText(dateFormat.format(calOneTimeDate.getTime()));
                }

            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        }
        else if (view.getId() == R.id.btn_one_time_alarm_time){
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calOneTimeTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    tvOneTimeTime.setText(dateFormat.format(calOneTimeTime.getTime()));
                  //Log.v(TAG, "The choosen one " + date.getTime());

                }
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();

        }
        else if (view.getId() == R.id.btn_set_one_time_alarm){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());

            String oneTimeMessage = edtOneTimeMessage.getText().toString();

            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    alarmPreference.getOneTimeDate(),
                    alarmPreference.getOneTimeTime(),
                    alarmPreference.getOneTimeMessage());
        }
        //todo 6 alarm manager 2 - repeating part
        else if (view.getId() == R.id.btn_repeating_time_alarm){
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String repeatTimeTime = timeFormat.format(calRepeatTimeTime.getTime());
            String repeatTimeMessage = edtRepeatingMessage.getText().toString();
            alarmPreference.setRepeatingTime(repeatTimeTime);
            alarmPreference.setRepeatingMessage(repeatTimeMessage);

            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
        }
        //alarm manager 3 - cancel part
        else if (view.getId() == R.id.btn_cancel_repeating_alarm){
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
        }
    }

    private void setOneTimeText() {
        tvOneTimeTime.setText(alarmPreference.getOneTimeTime());
        tvOneTimeDate.setText(alarmPreference.getOneTimeDate());
        edtOneTimeMessage.setText(alarmPreference.getOneTimeMessage());
    }

    //todo 7 alarm manager 2 - repeating part
    private void setRepeatingText(){
        tvRepeatingTime.setText(alarmPreference.getRepeatingTime());
        edtRepeatingMessage.setText(alarmPreference.getRepeatingMessage());
    }
}

//Setelah tanggal, waktu dan pesan telah Anda isi, lanjutkan dengan klik tombol SET ONE TIME ALARM dan silakan tunggu sampai waktu yang ditentukan, sehingga alarm akan dijalankan. Jika sukses, maka waktu yang kita set di awal akan tampil pada obyek TextView yang telah disediakan.
//Ketika waktunya sudah sesuai, maka alarm akan dijalankan dan akan menampilkan notifikasi dengan getaran dan suara seperti berikut.