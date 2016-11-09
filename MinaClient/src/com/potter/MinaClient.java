package com.potter;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.alibaba.fastjson.JSON;
import com.potter.bean.UserInfor;
import com.potter.utils.MD5;

public class MinaClient {
	private static final int bindPort = 51266;
	private static final String address = "tunnel.qydev.com";

	public static void main(String[] args) {
		MinaClientHanlder clientHanlder = new MinaClientHanlder();
		test(clientHanlder);
		// 创建一个socket连接
		NioSocketConnector connector = new NioSocketConnector();
		// 获取过滤器链
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		ProtocolCodecFilter filter = new ProtocolCodecFilter(new HCoderFactory(
				Charset.forName("UTF-8")));
		// 添加编码过滤器 处理乱码、编码问题
		chain.addLast("objectFilter", filter);
		// 消息核心处理器
		connector.setHandler(clientHanlder);
		// 设置链接超时时间
		connector.setConnectTimeoutCheckInterval(30);
		// 连接服务器，知道端口、地址
		ConnectFuture cf = connector.connect(new InetSocketAddress(address,
				bindPort));
		// 等待连接创建完成
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}

	/** 测试 */
	public static void test(final MinaClientHanlder clientHanlder) {
		new Thread() {
			@Override
			public void run() {
				Scanner s = new Scanner(System.in);
				while (true) {
					try {
						sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("请输入您的指令:");
					HData data = new HData();
					UserInfor userInfor;
					try {
						String str = s.next();
						short type = Short.valueOf(str);
						data.setType(type);
						switch (type) {
						case HData.TYPE_MSG:
							// TODO
							break;
						case HData.TYPE_ACCOUNT_REGISTER:
						case HData.TYPE_ACCOUNT_LOGIN:
							userInfor = new UserInfor();
							System.out.println("请输入您的用户名：");
							userInfor.setUsername(s.next());
							System.out.println("请输入您的密码：");
							userInfor.setPassword(MD5.GetMD5Code(s.next()));
							data.setData(JSON.toJSONString(userInfor));
							break;
						case HData.TYPE_ACCOUNT_CHANGE_PASSWORDR:
							userInfor = clientHanlder.getMe();
							if (userInfor == null) {
								System.out.println("你还没有登陆");
								continue;
							}
							System.out.println("请输入您的老密码：");
							userInfor.setPasswordOld(MD5.GetMD5Code(s.next()));
							System.out.println("请输入您的新密码：");
							userInfor.setPassword(MD5.GetMD5Code(s.next()));
							data.setData(JSON.toJSONString(userInfor));
							break;
						case HData.TYPE_ACCOUNT_LOGOUT:
							userInfor = clientHanlder.getMe();
							if (userInfor == null) {
								System.out.println("你还没有登陆");
								continue;
							}
							clientHanlder.setMe(null);
							data.setData(userInfor.getToken());
							break;
						default:
							System.out.println("指令无法识别");
							continue;
						}
					} catch (Exception e) {
						System.out.println("指令无法识别");
						continue;
					}
					clientHanlder.send(data);
				}
			}
		}.start();
	}
}
