package com.example.happykohli.timer;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity {

    SeekBar s;
    int VaryVal=0;
    TextView t;
    Button start,stop;
    CountDownTimer c;
    Calendar cal;
    SimpleDateFormat sdf;
    String Date;
    MediaPlayer play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        start=(Button)findViewById(R.id.start);
        stop=(Button)findViewById(R.id.stop);
        stop.setEnabled(false);

        t=(TextView)findViewById(R.id.t1);
        s=(SeekBar)findViewById(R.id.s);
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean User) {
                VaryVal=progress;
                String a=String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(VaryVal*1000),TimeUnit.MILLISECONDS.toMinutes(VaryVal*1000)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(VaryVal*1000)),TimeUnit.MILLISECONDS.toSeconds(VaryVal*1000)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(VaryVal*1000)));
                t.setText(a);
                }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String a=String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(VaryVal*1000),TimeUnit.MILLISECONDS.toMinutes(VaryVal*1000)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(VaryVal*1000)),TimeUnit.MILLISECONDS.toSeconds(VaryVal*1000)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(VaryVal*1000)));
                t.setText(a);

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VaryVal!=0){
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    cal=Calendar.getInstance();
                    sdf=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    Date=sdf.format(cal.getTime());

                    c=new CountDownTimer(VaryVal*1000,1000){
                        String starttime= String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(VaryVal*1000),TimeUnit.MILLISECONDS.toMinutes(VaryVal*1000)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(VaryVal*1000)),TimeUnit.MILLISECONDS.toSeconds(VaryVal*1000)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(VaryVal*1000)));

                        @Override
                        public void onTick(long millisUntilFinished) {
                            String a= String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                            t.setText(a);
                            s.setProgress((int)(millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {

                            s.setProgress(0);
                            t.setText("00:00:00");
                            start.setEnabled(true);
                            stop.setEnabled(false);
                            recordtime(starttime,Date);
                            play=MediaPlayer.create(getApplicationContext(),R.raw.iphone_5_original);
                            play.start();


                        }
                    }.start();
                }
                else
                {
                    if(VaryVal==0){
                        Toast.makeText(Timer.this,"SET THE TIMER FIRST",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.cancel();
                stop.setEnabled(false);
                start.setEnabled(true);
                s.setProgress(0);
                t.setText("00:00:00");


            }
        });

    }
    void recordtime(String time,String date){
        try{
            SQLiteDatabase db=this.openOrCreateDatabase("RECORD",MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data(date VARCHAR, time VARCHAR)");
            Log.i("date:",date);
            db.execSQL("INSERT INTO data(date , time) VALUES('"+date+"','"+time+"')");
            Toast.makeText(this,"TASK IS DONE AND SUBMITTED",Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
