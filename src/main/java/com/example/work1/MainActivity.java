package com.example.work1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer htimer = new Timer();  //小时分钟计时器
    Timer stimer = new Timer();  //秒钟计时器
    Timer ptimer = new Timer();  //pm，am计时器
    Timer dtimer = new Timer();  //日期计时器
    Timer wtimer = new Timer();  //周几

    TimerTask hourtask = new TimerTask() {         //时钟
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv1 = (TextView) findViewById(R.id.hourshow);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH : mm", Locale.CHINA);
                    String currentTime = dateFormat.format(new Date());
                    tv1.setText(currentTime);
                }
            });
        }
    };
    TimerTask secondtask = new TimerTask() {        //秒钟
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv2 = (TextView) findViewById(R.id.sshow);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("ss", Locale.CHINA);
                    String currentTime1 = dateFormat.format(new Date());
                    tv2.setText(currentTime1);
                }
            });
        }
    };
    TimerTask ampmTask = new TimerTask() {      //am/pm
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv3 = (TextView) findViewById(R.id.ampmshow);
                    SimpleDateFormat ampmFormat = new SimpleDateFormat("a", Locale.US);
                    String currentTime2 = ampmFormat.format(new Date());
                    tv3.setText(currentTime2);
                }
            });
        }
    };
    TimerTask weektask = new TimerTask() {      //周时
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv4 = (TextView) findViewById(R.id.wshow);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.CHINA);
                    String currentTime = dateFormat.format(new Date());
                    tv4.setText(currentTime);
                }
            });
        }
    };
    TimerTask daytask = new TimerTask() {       //日期
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv5 = (TextView) findViewById(R.id.cshow);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
                    String currentTime = dateFormat.format(new Date());
                    tv5.setText(currentTime);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        htimer.schedule(hourtask, 0, 1000);
        stimer.schedule(secondtask, 0, 1000);
        ptimer.schedule(ampmTask, 0, 1000 * 60 * 60);   //每小时更新一次
        dtimer.schedule(daytask, 0, 1000 * 60 * 60 * 24); //每天更新一次
        wtimer.schedule(weektask, 0, 1000 * 60 * 60 * 24);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        htimer.cancel();
        stimer.cancel();
        ptimer.cancel();
        wtimer.cancel();
        dtimer.cancel();
    }
}
