package cn.pumpkin.angrypandawatchdog.thread;

import cn.pumpkin.angrypandawatchdog.base.ThreadBase;
import cn.pumpkin.angrypandawatchdog.share.ShareObjectInstance;

/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/13 14:49
 * @des:
 * @see {@link }
 */

public class Thread1 extends ThreadBase {

    public Thread1(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        ShareObjectInstance.getInstance().doWork("thread work 1",10000);
    }

    @Override
    public void doWork(String workname) {
        run();
    }

    @Override
    public void finishWork() {
        ShareObjectInstance.getInstance().finishWork();
    }
}
