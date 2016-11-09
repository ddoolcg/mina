package com.potter.service;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.potter.DataHandler;
import com.potter.HData;
import com.potter.bean.UserInfor;
import com.potter.cache.UsersManager;
import com.potter.model.User;

/**
 * 注册和登录处理器
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-31 下午5:56:48
 * @version 1.0
 */
public class HandlerRegisterAndLogin extends DataHandler<UserInfor> {

	@Override
	protected void onHandler(IoSession session, HData originalData,
			UserInfor obj) {
		UserInfor infor = null;
		switch (originalData.getType()) {
		case HData.TYPE_ACCOUNT_LOGIN:
			infor = User.login(obj.getUsername(), obj.getPassword());
			break;
		case HData.TYPE_ACCOUNT_REGISTER:
			infor = User.register(obj.getUsername(), obj.getPassword());
			break;
		default:
			break;
		}
		if (infor == null) {
			originalData.setData("");
		} else {
			originalData.setData(JSON.toJSONString(infor));
			UsersManager.getManager().add(session, infor);
		}
		session.write(originalData);
	}

}
