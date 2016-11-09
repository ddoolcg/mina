package com.potter.service;

import org.apache.mina.core.session.IoSession;

import com.potter.DataHandler;
import com.potter.HData;
import com.potter.bean.UserInfor;
import com.potter.model.User;

/**
 * 修改密码处理器
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-31 下午5:56:48
 * @version 1.0
 */
public class HandlerChangePassword extends DataHandler<UserInfor> {

	@Override
	protected void onHandler(IoSession session, HData originalData,
			UserInfor obj) {
		boolean password = User.changePassword(obj.getToken(),
				obj.getPasswordOld(), obj.getPassword());
		originalData.setData(String.valueOf(password));
		session.write(originalData);
	}

}
