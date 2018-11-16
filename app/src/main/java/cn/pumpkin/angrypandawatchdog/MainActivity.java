package cn.pumpkin.angrypandawatchdog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import cn.pumpkin.angrypandawatchdog.dog.MessageWatchDog;
import cn.pumpkin.angrypandawatchdog.dog.MessageWatchDogInst;
import cn.pumpkin.angrypandawatchdog.dog.WatchDog;
import cn.pumpkin.angrypandawatchdog.thread.Thread1;
import cn.pumpkin.angrypandawatchdog.thread.Thread2;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    private Thread1 th1;
    private Thread2 th2;

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private WatchDog mDog;
    private MessageWatchDog mMessageWatchDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        th1 = new Thread1("Thread1");
        th2 = new Thread2("Thread2");

        mDog = new WatchDog("watchdog");
        mDog.start();

        mMessageWatchDog=new MessageWatchDog("MessageWatchDog",mWorkHandler);
        // mMessageWatchDog.start();

        mBtn1 = (Button) findViewById(R.id.release1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                th1.finishWork();
            }
        });

        mBtn2 = (Button) findViewById(R.id.release2);
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                th2.finishWork();
            }
        });

        mBtn3=(Button)findViewById(R.id.post);
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkHandler.postAtFrontOfQueue(new Runnable() {
                    @Override
                    public void run() {
                        android.util.Log.e(TAG,"postAtFrontOfQueue ...");
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        th1.start();
        th2.start();

        mWorkAsyncTask.execute();

        MessageWatchDogInst.getInstance().addMonitor("MessageWatchDogInst",mWorkHandler);
    }

    private int mCnt=0;

    private WorkAsyncTask mWorkAsyncTask = new WorkAsyncTask();

    private class WorkAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(mCnt<10) {
                    android.util.Log.e(TAG,"doInBackground mCnt : "+mCnt++);
                    mWorkHandler.sendEmptyMessage(mCnt);
                }

            }

            // return "";
        }
    }

    private WorkHandler mWorkHandler = new WorkHandler();

    private class WorkHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                // 这个地方加延时,将会堵塞或者延迟下一个MessageQueue中的事物处理
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.util.Log.e(TAG,"WorkHandler mCnt : "+msg.what);
        }
    }
}
