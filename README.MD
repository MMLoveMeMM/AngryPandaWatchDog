查看android源码的watchdog.java,实现监听消息阻塞和共享资源被锁;
原理<br>
<1> : 对共享资源的监控来反应工程中其他工作线程访问资源是否及时释放,如果没有及时释放,说明该工作线程"假死"<br>
或者死锁,导致共享资源得不到释放,而监控线程(看门狗)是定时去看看共享资源是否可以访问,如果可以访问,说明访问<br>
该共享资源的工作线程都工作良好,否则工作线程存在有死锁的线程;<br>
<2> : 参考例子中可以设置线程Thread1,Thread2占有资源的时间,而watchdog监控共享资源被占用时间是不能够超过3s,<br>
超过3s,WatchDog将会立即提示超时,这里有日志的.实际项目中出现死锁可以重启.<br>
<br>
还有一种情况是,Handler用的比较多,导致MessageQueue 访问被阻塞,由于其中一个消息事件被阻塞<br>
