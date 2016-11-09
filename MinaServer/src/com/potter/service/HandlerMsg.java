package com.potter.service;

import org.apache.mina.core.session.IoSession;

import com.potter.DataHandler;
import com.potter.HData;

/**
 * 消息处理器
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-31 下午5:56:48
 * @version 1.0
 */
public class HandlerMsg extends DataHandler<String> {

	@Override
	protected void onHandler(IoSession session, HData originalData, String data) {
		logger.debug("客户端发送了一个消息：" + data);
		// TODO Auto-generated method stub

	}

}
