package com.lcggame.pvp.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import com.alibaba.fastjson.JSON;
import com.lcggame.pvp.utils.L;
import com.potter.HData;
import com.potter.bean.UserInfor;

public class MinaClientHanlder extends IoHandlerAdapter {
	private IoSession mSession;
	private UserInfor me;
	private int mHeartCount = 0;

	public void sessionOpened(IoSession session) throws Exception {
		EventBus.getDefault().register(this);
		mSession = session;
		mSession.getConfig().setBothIdleTime(10);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		EventBus.getDefault().post(
				new HData(HData.TYPE_HEART, mHeartCount++ + ""), "send");
		EventBus.getDefault().post(0, "heart_time");
		super.sessionIdle(session, status);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof HData) {
			HData data = (HData) message;
			String content = data.getData();
			L.i("Receive-->" + content);
			switch (data.getType()) {
			case HData.TYPE_MSG:
				// TODO
				break;
			case HData.TYPE_ACCOUNT_REGISTER:
				if (content == null || "".equals(content)) {
					L.i("用户名已存在");
					setMe(new UserInfor());
				} else {
					setMe(JSON.parseObject(data.getData(), UserInfor.class));
					L.i("注册成功");
				}
				EventBus.getDefault().post(getMe(), "regist");
				break;
			case HData.TYPE_ACCOUNT_LOGIN:
				if (content == null || "".equals(content)) {
					setMe(new UserInfor());
					L.i("用户名或密码错误");
				} else {
					setMe(JSON.parseObject(data.getData(), UserInfor.class));
					L.i("登陆成功");
				}
				EventBus.getDefault().post(getMe(), "login");
				break;
			case HData.TYPE_HEART:
				EventBus.getDefault().post(Integer.valueOf(data.getData()),
						"post_heart_time");
				break;
			case HData.TYPE_ACCOUNT_CHANGE_PASSWORDR:
				try {
					boolean change = Boolean.valueOf(content);
					if (change) {
						L.i("修改密码成功");
					} else {
						L.i("您的老密码不正确");
					}
				} catch (Exception e) {
					L.i("服务器异常！");
				}
				break;
			default:
				L.i("Server Say:undefined");
				break;
			}
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		EventBus.getDefault().unregister(this);
		session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		L.w("异常----");
		session.close(true);
		cause.printStackTrace();
		super.exceptionCaught(session, cause);
	}

	public UserInfor getMe() {
		return me;
	}

	public void setMe(UserInfor me) {
		this.me = me;
	}

	@Subscriber(tag = "send", mode = ThreadMode.ASYNC)
	public void send(HData data) {
		mSession.write(data);
	}

	@Subscriber(tag = "close_session", mode = ThreadMode.ASYNC)
	public void close(boolean b) {
		mSession.close(b);
	}
}
