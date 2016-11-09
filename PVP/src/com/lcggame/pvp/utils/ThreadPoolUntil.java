package com.lcggame.pvp.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import android.os.SystemClock;

/** 线程管理工具 */
public class ThreadPoolUntil {
	public final ArrayList<Future<?>> futureList = new ArrayList<Future<?>>();
	private ThreadPoolExecutor server;

	public ThreadPoolExecutor getExecutor() {
		return server;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		server = executor;
	}

	private final LinkedList<Runnable> runnables = new LinkedList<Runnable>();
	AtomicInteger currentCount = new AtomicInteger(0);

	private ThreadPoolUntil() {
		int cpuNumCores = getCpuNumCores();
		server = new ThreadPoolExecutor(4, 4 + cpuNumCores, 10,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						4 * cpuNumCores),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * 获取CPU核心数
	 * 
	 * @return
	 */
	private int getCpuNumCores() {
		int cpuNumCores = 1;
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String filename) {
					if (Pattern.matches("cpu[0-9]", filename)) {
						return true;
					}
					return false;
				}
			});
			cpuNumCores = files.length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpuNumCores;
	}

	private final static ThreadPoolUntil threadPool = new ThreadPoolUntil();

	public static ThreadPoolUntil getInstance() {
		return threadPool;
	}

	/**
	 * 加入线程池队列，并不一定立刻执行。
	 * 
	 * @param runnable
	 * @return 如果加入成功，返回一个Future。
	 */
	public Future<?> run(Runnable runnable) {
		Future<?> submit = server.submit(runnable);
		futureList.add(submit);
		return submit;

	}

	/**
	 * 加入单一线程去排队执行，并不一定立刻执行。
	 * 
	 * @param runnable
	 * @return 如果加入成功，返回一个Future。
	 */
	public void runSingle(Runnable runnable) {
		synchronized (runnables) {
			runnables.addFirst(runnable);
		}
		if (currentCount.getAndIncrement() < 1) {
			createThread();
		}
	}

	public void createThread() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				int count = 0;
				while (true) {
					Runnable first = null;
					synchronized (runnables) {
						if (!runnables.isEmpty()) {
							first = runnables.removeLast();
							currentCount.getAndDecrement();
						}
					}
					if (first != null) {
						first.run();
						count = 0;
					} else {
						SystemClock.sleep(10);
						count++;
					}
					if (count > 100) {
						break;
					}
				}
			}
		};
		thread.setName("SingleThread");
		thread.start();
	}

	public void cancelAll(boolean mayInterruptIfRunning) {
		for (int i = 0; i < futureList.size(); i++) {
			futureList.get(i).cancel(mayInterruptIfRunning);
		}
		futureList.clear();
	}
}