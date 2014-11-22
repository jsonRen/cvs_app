package org.cv.spider.thread;

public class SpiderStart implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new AndroidThread();
		new IphoneThread();
		new IpadThread();
	}
}
