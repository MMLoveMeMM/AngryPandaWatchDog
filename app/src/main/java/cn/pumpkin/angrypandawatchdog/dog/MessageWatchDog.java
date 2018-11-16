package cn.pumpkin.angrypandawatchdog.dog;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/16 15:50
 * @des:
 * @see {@link }
 */

public class MessageWatchDog extends Thread {

    private static final String TAG = MessageWatchDog.class.getName();
    private Handler mHandler;

    public MessageWatchDog(String name, Handler mHandler) {
        super(name);
        this.mHandler = mHandler;
    }

    private  class TimerThread extends Thread{
        @Override
        public void run() {
            super.run();

            while (true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final long latency = SystemClock.uptimeMillis();
                long end = SystemClock.uptimeMillis();
                if(mHandler!=null){
                    mHandler.postAtFrontOfQueue(new Runnable(){
                        @Override
                        public void run() {
                            long end = SystemClock.uptimeMillis();
                            if(end-latency>2000){

                                Log.e(TAG,"message queue block !");

                            }

                        }
                    });
                }

            }

        }
    }

    @Override
    public void run() {
        super.run();
        new TimerThread().start();
    }
}
