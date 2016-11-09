package com.lcggame.pvp.net;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.simple.eventbus.EventBus;

import com.alibaba.fastjson.JSON;
import com.potter.HCoderFactory;
import com.potter.HData;
import com.potter.bean.UserInfor;
import com.potter.utils.MD5;

public class MinaClient {
	private static final int bindPort = 51266;
	private static final String address = "tunnel.qydev.com";
	private static boolean isInit = true;

	private static void init(final boolean isRegist, final String username,
			final String password) {
		isInit = false;
		new Thread() {
			public void run() {
				MinaClientHanlder hanlder = new MinaClientHanlder();
				// 创建一个socket连接
				NioSocketConnector connector = new NioSocketConnector();
				// 获取过滤器链
				DefaultIoFilterChainBuilder chain = connector.getFilterChain();

				ProtocolCodecFilter filter = new ProtocolCodecFilter(
						new HCoderFactory(Charset.forName("UTF-8")));
				// 添加编码过滤器 处理乱码、编码问题
				chain.addLast("objectFilter", filter);
				// 消息核心处理器
				connector.setHandler(hanlder);
				// 设置链接超时时间
				connector.setConnectTimeoutCheckInterval(30);
				// 连接服务器，知道端口、地址
				ConnectFuture cf = connector.connect(new InetSocketAddress(
						address, bindPort));
				// 等待连接创建完成
				cf.awaitUninterruptibly();
				creat(isRegist, username, password);
				cf.getSession().getCloseFuture().awaitUninterruptibly();
				isInit = true;
				connector.dispose();
			}
		}.start();
	}

	/** 创建时注册或登录 */
	public static void creat(boolean isRegist, String username, String password) {
		if (isInit) {
			init(isRegist, username, password);
			return;
		}
		HData data = new HData();
		if (isRegist) {
			data.setType(HData.TYPE_ACCOUNT_REGISTER);
		} else {
			data.setType(HData.TYPE_ACCOUNT_LOGIN);
		}
		UserInfor userInfor = new UserInfor();
		userInfor.setUsername(username);
		userInfor.setPassword(MD5.GetMD5Code(password));
		data.setData(JSON.toJSONString(userInfor));
		EventBus.getDefault().post(data, "send");
	}

}
