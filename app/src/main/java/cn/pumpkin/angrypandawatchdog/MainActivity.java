package cn.pumpkin.angrypandawatchdog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.pumpkin.angrypandawatchdog.dog.WatchDog;
import cn.pumpkin.angrypandawatchdog.thread.Thread1;
import cn.pumpkin.angrypandawatchdog.thread.Thread2;

public class MainActivity extends Activity {

    private Thread1 th1;
    private Thread2 th2;

    private Button mBtn1;
    private Button mBtn2;
    private WatchDog mDog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        th1=new Thread1("Thread1");
        th2=new Thread2("Thread2");

        mDog=new WatchDog("watchdog");
        mDog.start();

        mBtn1=(Button)findViewById(R.id.release1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                th1.finishWork();
            }
        });

        mBtn2=(Button)findViewById(R.id.release2);
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                th2.finishWork();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        th1.start();
        th2.start();
    }
}
