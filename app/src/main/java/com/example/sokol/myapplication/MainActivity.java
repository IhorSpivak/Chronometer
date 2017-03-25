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
    Button btnReset;
    private static final String RESET_TIME = "00:00:00:000";

    private EditText lapResult;
    private int laps = 1;
    private ScrollView svLap;

    private Context mContext;

    private Chronometr chronometr;
    private Thread mThreadChrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        tvTime = (TextView) findViewById(R.id.tvTime);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnLap = (Button) findViewById(R.id.btnLap);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnReset = (Button) findViewById(R.id.btnReset);
        svLap = (ScrollView) findViewById(R.id.svLap);
        lapResult = (EditText) findViewById(R.id.lapResult);

        lapResult.setEnabled(false);



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chronometr == null){
                    chronometr = new Chronometr(mContext);
                    mThreadChrono = new Thread(chronometr);
                    mThreadChrono.start();
                    chronometr.start();

                    laps = 1;
                    lapResult.setText("");

                } else {
                    tvTime.getText();
                    mThreadChrono.start();
                    chronometr.start();

                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometr != null){
                    chronometr.stop();
                    mThreadChrono.interrupt();
                }

            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chronometr == null){
                    return;
                }

                lapResult.append("LAP " + String.valueOf(laps) + " " + String.valueOf(tvTime.getText()) + "\n");

                laps++;

                svLap.post(new Runnable() {
                    @Override
                    public void run() {

                        svLap.smoothScrollTo(0, svLap.getBottom());
                    }
                });
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(RESET_TIME);

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
