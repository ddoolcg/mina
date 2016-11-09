package com.potter;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.potter.cache.UsersManager;

public class MinaServerHanlder extends IoHandlerAdapter {
	private Logger logger = Logger.getLogger(getClass());
	private HashMap<Short, Class<? extends DataHandler<?>>> handlers = new HashMap<Short, Class<? extends DataHandler<?>>>();

	public void sessionCreated(IoSession session) {
		logger.debug("新客户端连接");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		String address = session.getRemoteAddress().toString();
		logger.debug("client 登陆！address:" + address);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		UsersManager.getManager().remove(session);
		session.close(true);
		logger.debug("I'm Client &&  I closed!");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof HData) {
			HData data = (HData) message;
			logger.debug(data.toString());
			short type = data.getType();
			Class<? extends DataHandler<?>> clazz = handlers.get(type);
			if (clazz == null) {
				logger.error("不包含为处理的数据类型type=" + type);
			} else {
				DataHandlerPool.getInstance().handler(clazz, session, data);
			}
		}
	}

	/** 添加数据处理器类 */
	public void addDataHandlerType(Short type,
			Class<? extends DataHandler<?>> clazz) {
		handlers.put(type, clazz);
	}

	/** 移除数据处理器 */
	public void removeDataHandler(Short type) {
		handlers.remove(type);
	}

}
