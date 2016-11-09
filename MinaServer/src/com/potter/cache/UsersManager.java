package com.potter.cache;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.mina.core.session.IoSession;

import com.potter.bean.UserInfor;
import com.potter.model.User;

/**
 * 用户管理
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-31 下午8:40:16
 * @version 1.0
 */
public class UsersManager {
	private static UsersManager manager;
	private HashMap<IoSession, UserInfor> users = new HashMap<IoSession, UserInfor>();

	private UsersManager() {
		final long period = 180000;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Set<Entry<IoSession, UserInfor>> entrySet = users.entrySet();
				long millis = System.currentTimeMillis() - period;
				for (Entry<IoSession, UserInfor> entry : entrySet) {
					if (entry.getValue().getLastLoginTime() < millis) {
						entry.getKey().close(true);
					}
				}
			}
		}, period, period);
	}

	public static UsersManager getManager() {
		if (manager == null)
			manager = new UsersManager();
		return manager;
	}

	/** 添加用户 */
	public void add(IoSession session, UserInfor user) {
		// TODO 需要优化 增加二级缓存
		users.put(session, user);
	}

	/** 根据远端地址获取用户 */
	public UserInfor getUser(IoSession session) {
		return users.get(session);
	}

	/** 移除一个用户 */
	public void remove(IoSession session) {
		UserInfor remove = users.remove(session);
		User.logout(remove.getToken());
	}

}
