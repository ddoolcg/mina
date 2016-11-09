package com.potter.service;

import org.apache.mina.core.session.IoSession;

import com.potter.DataHandler;
import com.potter.HData;
import com.potter.cache.UsersManager;

/**
 * 心跳处理器
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-31 下午5:56:48
 * @version 1.0
 */
public class HandlerHeart extends DataHandler<String> {

	@Override
	protected void onHandler(IoSession session, HData originalData, String data) {
		session.write(originalData);
		String address = session.getRemoteAddress().toString();
		UsersManager.getManager().getUser(session)
				.setLastLoginTime(System.currentTimeMillis());
		logger.debug(address + "-->发送了一个心跳");
	}

}
