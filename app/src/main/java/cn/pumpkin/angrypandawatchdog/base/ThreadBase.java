package cn.pumpkin.angrypandawatchdog.base;

/**
 * @author: zhibao.Liu
 * @version:
 * @date: 2018/11/13 15:12
 * @des:
 * @see {@link }
 */

public abstract class ThreadBase extends Thread {

    public ThreadBase(String name) {
        super(name);
    }

    public abstract void doWork(String workname);
    public abstract void finishWork();

}
