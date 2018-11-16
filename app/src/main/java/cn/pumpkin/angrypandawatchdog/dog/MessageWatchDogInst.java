package cn.pumpkin.angrypandawatchdog.dog;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;


/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/16 16:24
 * @des:
 * @see {@link }
 */

public class MessageWatchDogInst {

    private static final String TAG = MessageWatchDogInst.class.getName();

    private static final MessageWatchDogInst ourInstance = new MessageWatchDogInst();

    private static final int BLOCK_TIMEOUT = 2 * 1000;

    private String mName;
    private Handler mHandler;// 如果监控多个,就用ArrayList保存,或者队列

    public static MessageWatchDogInst getInstance() {
        return ourInstance;
    }

    private MessageWatchDogInst() {
    }

    public void addMonitor(String name, Handler handler) {
        mName = name;
        mHandler = handler;

        new Monitor(mName).start();
    }

    public class Monitor extends Thread {

        public Monitor(String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();

            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final long latency = SystemClock.uptimeMillis();
                long end = SystemClock.uptimeMillis();
                if (mHandler != null) {
                    mHandler.postAtFrontOfQueue(new Runnable() {
                        @Override
                        public void run() {
                            long end = SystemClock.uptimeMillis();
                            if (end - latency > BLOCK_TIMEOUT) {
                                Log.e(TAG, "message queue block !");
                            }

                        }
                    });
                }

            }

        }
    }

}
