package cn.pumpkin.angrypandawatchdog.dog;

import cn.pumpkin.angrypandawatchdog.base.ThreadBase;
import cn.pumpkin.angrypandawatchdog.share.ShareObjectInstance;

/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/13 15:43
 * @des:
 * @see {@link }
 */

public class WatchDog extends Thread implements IDog {

    private static final int ACCESS_RES_TIMEOUT = 3 * 1000;
    private long startTime = System.currentTimeMillis();
    private long endTime = System.currentTimeMillis();
    private boolean checkTimeout = false;

    public WatchDog(String name) {
        super(name);
        ShareObjectInstance.getInstance().addDog(this);

        loopAccess();

    }

    private void loopAccess(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    startTime = System.currentTimeMillis();
                    endTime = System.currentTimeMillis();
                    checkTimeout = false;
                    ShareObjectInstance.getInstance().lookWork("watch dog",10);
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    @Override
    public void run() {
        super.run();

        while (true) {
            if (endTime - startTime > ACCESS_RES_TIMEOUT && !checkTimeout) {
                android.util.Log.e("ShareObjectInstance", "timeout ----------------------");
                startTime = System.currentTimeMillis();
                endTime = System.currentTimeMillis();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endTime = System.currentTimeMillis();
        }

    }

    @Override
    public void dogBackHome() {
        ShareObjectInstance.getInstance().finishWork();
        checkTimeout = true;
        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
    }
}
