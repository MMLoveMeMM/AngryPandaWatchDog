package cn.pumpkin.angrypandawatchdog.share;

import cn.pumpkin.angrypandawatchdog.dog.IDog;

/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/13 14:46
 * @des:
 * @see {@link }
 */

public class ShareObjectInstance {

    private static final String TAG = ShareObjectInstance.class.getName();

    private static final ShareObjectInstance ourInstance = new ShareObjectInstance();

    public static ShareObjectInstance getInstance() {
        return ourInstance;
    }

    private boolean mReleaseResource = false;

    private IDog mDog;

    private ShareObjectInstance() {
    }

    public void addDog(IDog dog){
        mDog = dog;
    }
    /**
     * 某个时刻只允许一个线程使用
     * */
    public synchronized void doWork(String name,int worktime) {
        mReleaseResource = false;

        android.util.Log.e(TAG, name);
        /*
        * 利用延时占据该实例资源
        * */
        while(!mReleaseResource){
            try {
                Thread.sleep(worktime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mReleaseResource = true;
        }

        android.util.Log.e(TAG, name+" release");

    }

    public void finishWork(){
        // 释放资源
        mReleaseResource = true;

    }

    public void lookWork(String name,int worktime){
        doWork(name,worktime);
        mDog.dogBackHome();
    }

}
