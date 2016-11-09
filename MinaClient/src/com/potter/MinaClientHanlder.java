package com.potter;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.potter.bean.UserInfor;

public class MinaClientHanlder extends IoHandlerAdapter {
	private IoSession mSession;
	private UserInfor me;

	public void sessionOpened(IoSession session) throws Exception {
		mSession = session;
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof HData) {
			HData data = (HData) message;
			String content = data.getData();
			System.out.println("Receive-->" + content);
			switch (data.getType()) {
			case HData.TYPE_MSG:
				// TODO
				break;
			case HData.TYPE_ACCOUNT_REGISTER:
				if (content == null || "".equals(content)) {
					System.out.println("用户名已存在");
				} else {
					setMe(JSON.parseObject(data.getData(), UserInfor.class));
					System.out.println("注册成功");
				}
				break;
			case HData.TYPE_ACCOUNT_LOGIN:
				if (content == null || "".equals(content)) {
					System.out.println("用户名或密码错误");
				} else {
					setMe(JSON.parseObject(data.getData(), UserInfor.class));
					System.out.println("登陆成功");
				}
				break;
			case HData.TYPE_ACCOUNT_CHANGE_PASSWORDR:
				try {
					boolean change = Boolean.valueOf(content);
					if (change) {
						System.out.println("修改密码成功");
					} else {
						System.out.println("您的老密码不正确");
					}
				} catch (Exception e) {
					System.out.println("服务器异常！");
				}
				break;
			default:
				System.out.println("Server Say:undefined");
				break;
			}
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.close(true);
	}

	public UserInfor getMe() {
		return me;
	}

	public void setMe(UserInfor me) {
		this.me = me;
	}

	public void send(HData data) {
		mSession.write(data);
	}
}
