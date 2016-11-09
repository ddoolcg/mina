package com.potter;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

/**
 * 数据处理器池
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-9-21 下午9:16:59
 * @version 1.0
 */
public class DataHandlerPool {
	private static DataHandlerPool instance;
	protected Logger logger = Logger.getLogger(this.getClass());
	private HashMap<Class<?>, ArrayList<DataHandler<?>>> map = new HashMap<Class<?>, ArrayList<DataHandler<?>>>();

	private DataHandlerPool() {
	}

	public static DataHandlerPool getInstance() {
		if (instance == null)
			instance = new DataHandlerPool();
		return instance;
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public <T extends DataHandler<?>> T getHandler(Class<T> clazz) {
		ArrayList<DataHandler<?>> list = map.get(clazz);
		if (list == null) {
			list = new ArrayList<DataHandler<?>>();
			map.put(clazz, list);
		}
		//
		T t;
		if (list.isEmpty()) {
			try {
				t = clazz.newInstance();
			} catch (Exception e) {
				logger.error("", e);
				t = null;
			}
		} else {
			t = (T) list.remove(0);
		}
		return t;
	}

	@Deprecated
	public void returnHandler(DataHandler<?> handler) {
		@SuppressWarnings("unchecked")
		Class<? extends DataHandler<?>> clazz = (Class<? extends DataHandler<?>>) handler
				.getClass();
		ArrayList<DataHandler<?>> list = map.get(clazz);
		if (list == null) {
			list = new ArrayList<DataHandler<?>>();
			map.put(clazz, list);
		}
		list.add(handler);
	}

	/** 代理执行 */
	public void handler(Class<? extends DataHandler<?>> clazz,
			IoSession session, HData data) {
		DataHandler<?> handler = getHandler(clazz);
		handler.handler(session, data);
		returnHandler(handler);
	}
}
