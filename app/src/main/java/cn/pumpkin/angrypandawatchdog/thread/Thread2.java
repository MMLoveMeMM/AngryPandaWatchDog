package cn.pumpkin.angrypandawatchdog.thread;

import cn.pumpkin.angrypandawatchdog.base.ThreadBase;
import cn.pumpkin.angrypandawatchdog.share.ShareObjectInstance;

/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/13 14:50
 * @des:
 * @see {@link }
 */

public class Thread2 extends ThreadBase {

    public Thread2(String name) {
        super(name);
    }

    @Override
    public void doWork(String workname) {
        run();
    }

    @Override
    public void finishWork() {
        ShareObjectInstance.getInstance().finishWork();
    }

    @Override
    public void run() {
        super.run();
        ShareObjectInstance.getInstance().doWork("thread work 2",1000);
    }
}
