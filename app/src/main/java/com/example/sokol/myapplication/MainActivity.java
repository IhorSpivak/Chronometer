package com.example.sokol.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    Button btnStart;
    Button btnLap;
    Button btnStop;

    private EditText lapResult;
    private int laps = 1;
    private ScrollView svLap;

    private Context context;
    private Chronometr chronometr;
    private Thread mThreadChrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        tvTime = (TextView) findViewById(R.id.tvTime);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnLap = (Button) findViewById(R.id.btnLap);
        btnStop = (Button) findViewById(R.id.btnStop);
        svLap = (ScrollView) findViewById(R.id.svLap);
        lapResult = (EditText) findViewById(R.id.lapResult);

        lapResult.setEnabled(false);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chronometr == null){
                    chronometr = new Chronometr(context);
                    mThreadChrono = new Thread(chronometr);
                    mThreadChrono.start();
                    chronometr.start();

                    laps = 1;
                    lapResult.setText("");

                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometr != null){
                    chronometr.stop();
                    mThreadChrono.interrupt();
                    mThreadChrono = null;

                    chronometr = null;

                }
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chronometr == null){
                    return;
                }

                lapResult.append("LAP " + String.valueOf(laps) + String.valueOf(tvTime.getText()) + "\n");

                laps++;

                svLap.post(new Runnable() {
                    @Override
                    public void run() {

                        svLap.smoothScrollTo(0, svLap.getBottom());
                    }
                });
            }
        });

    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTime.setText(time);
            }
        });
    }
}
