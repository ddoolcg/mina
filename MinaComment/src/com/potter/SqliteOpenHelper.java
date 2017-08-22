package com.potter;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.potter.bean.PlayerInfo;
import com.potter.bean.UserInfor;

/**
 * TODO
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-9-3 上午11:42:01
 * @version 1.0
 */
public class SqliteOpenHelper {
	private static Dao<PlayerInfo, Long> playerDao;
	private static Dao<UserInfor, Long> userDao;
	private static JdbcConnectionSource connectionSource;

	public static Dao<PlayerInfo, Long> GetPlayerDao() throws SQLException {
		if (playerDao == null) {
			playerDao = DaoManager.createDao(GetConnectionSource(),
					PlayerInfo.class);
		}
		return playerDao;
	}

	public static Dao<UserInfor, Long> GetUserDao() throws SQLException {
		if (userDao == null) {
			userDao = DaoManager.createDao(GetConnectionSource(),
					UserInfor.class);
		}
		return userDao;
	}

	public static void init() {
		try {
			TableUtils.createTableIfNotExists(GetConnectionSource(),
					UserInfor.class);
			TableUtils.createTableIfNotExists(GetConnectionSource(),
					PlayerInfo.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static ConnectionSource GetConnectionSource() throws SQLException {
		if (connectionSource == null) {
			// String connectionString = "jdbc:sqlite:data.db";
			String connectionString = "jdbc:mysql://localhost/mina";
			connectionSource = new JdbcConnectionSource(connectionString,
					"root", "ddoolcg");
		}
		return connectionSource;
	}
}
