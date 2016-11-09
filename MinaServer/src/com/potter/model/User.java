package com.potter.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.potter.SqliteOpenHelper;
import com.potter.bean.UserInfor;
import com.potter.utils.MD5;

/**
 * 用户注册、登陆、修改密码、完善资料等。
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-27 上午9:53:40
 * @version 1.0
 */
public class User {
//	public static void main(String[] args) {
//		long millis = System.currentTimeMillis();
//		SqliteOpenHelper.init();
//		long millis1 = System.currentTimeMillis();
//		System.out.println("初始化时长：" + (millis1 - millis));
//		for (int i = 0; i < 100000; i++) {
//			register("tesZt" + i, "aaaa");
//		}
//		long millis2 = System.currentTimeMillis();
//		System.out.println("注册时长：" + (millis2 - millis1));
//		for (int i = 0; i < 100000; i++) {
//			login("teZst" + i, "aaaa");
//		}
//		long millis3 = System.currentTimeMillis();
//		System.out.println("登陆时长：" + (millis3 - millis2));
//	}

	/** 注册 */
	public static UserInfor register(String username, String password) {
		try {
			List<UserInfor> queryForEq = SqliteOpenHelper.GetUserDao()
					.queryForEq("username", username);
			if (queryForEq != null && !queryForEq.isEmpty()) {
				return null;
			}
			UserInfor userInfor = new UserInfor();
			userInfor.setUsername(username);
			userInfor.setPassword(password);
			userInfor.setLastLoginTime(System.currentTimeMillis());
			userInfor.setToken(MD5.GetMD5Code(userInfor.getUsername() + ""
					+ userInfor.getLastLoginTime()));
			SqliteOpenHelper.GetPlayerDao().create(userInfor.getPlayerInfo());
			int create = SqliteOpenHelper.GetUserDao().create(userInfor);
			if (create > 0) {
				userInfor.setPassword(null);
				return userInfor;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 登录 */
	public static UserInfor login(String username, String password) {
		try {
			Dao<UserInfor, Long> userDao = SqliteOpenHelper.GetUserDao();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);
			map.put("password", password);
			List<UserInfor> values = userDao.queryForFieldValues(map);
			if (values == null || values.isEmpty()) {
				return null;
			} else {
				UserInfor userInfor = values.get(0);
				long millis = System.currentTimeMillis();
				userInfor.setLastLoginTime(millis);
				userInfor.setToken(MD5.GetMD5Code(userInfor.getUsername() + ""
						+ userInfor.getLastLoginTime()));
				userDao.update(userInfor);
				userInfor.setPassword(null);
				return userInfor;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 登出 */
	public static void logout(String token) {
		try {
			SqliteOpenHelper
					.GetUserDao()
					.updateRaw(
							"UPDATE userinfor SET token = '', lastLoginTime = ? WHERE token = ? ",
							System.currentTimeMillis() + "", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 修改密码 */
	public static boolean changePassword(String token, String oldPassword,
			String newPassword) {
		try {
			Dao<UserInfor, Long> userDao = SqliteOpenHelper.GetUserDao();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("token", token);
			map.put("password", oldPassword);
			List<UserInfor> values = userDao.queryForFieldValues(map);
			if (values != null && !values.isEmpty()) {
				UserInfor userInfor = values.get(0);
				userInfor.setPassword(newPassword);
				int update = SqliteOpenHelper.GetUserDao().update(userInfor);
				return update > 0;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 更具ID获取用户信息 */
	public static UserInfor getUserByID(long id) {
		try {
			UserInfor infor = SqliteOpenHelper.GetUserDao().queryForId(id);
			infor.setPassword(null);
			return infor;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 更具token获取用户信息 */
	public static UserInfor getUserByToken(String token) {
		try {
			Dao<UserInfor, Long> userDao = SqliteOpenHelper.GetUserDao();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("token", token);
			List<UserInfor> values = userDao.queryForFieldValues(map);
			if (values != null && !values.isEmpty()) {
				UserInfor userInfor = values.get(0);
				userInfor.setPassword(null);
				return userInfor;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 更新用户信息,密码除外 */
	@Deprecated
	public static void updata(UserInfor userInfor) {
		try {
			UserInfor infor = SqliteOpenHelper.GetUserDao().queryForId(
					userInfor.getId());
			userInfor.setPassword(infor.getPassword());
			SqliteOpenHelper.GetUserDao().update(userInfor);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
