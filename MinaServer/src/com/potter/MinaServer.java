package com.potter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.potter.service.HandlerChangePassword;
import com.potter.service.HandlerHeart;
import com.potter.service.HandlerLogout;
import com.potter.service.HandlerMsg;
import com.potter.service.HandlerRegisterAndLogin;

public class MinaServer {
	private static final int bindPort = 8899;
	private static Logger logger = Logger.getLogger(MinaServer.class);

	public static void main(String[] args) {
		SqliteOpenHelper.init();
		//
		SocketAcceptor acceptor = new NioSocketAcceptor();

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

		ProtocolCodecFilter filter = new ProtocolCodecFilter(new HCoderFactory(
				Charset.forName("UTF-8")));
		// 添加编码过滤器 处理乱码问题、编码问题
		chain.addLast("objectFilter", filter);
		chain.addLast("executor", new ExecutorFilter(100, 2000000));
		// 设置核心消息业务处理器
		MinaServerHanlder hanlder = new MinaServerHanlder();
		initHanlder(hanlder);
		acceptor.setHandler(hanlder);
		//
		try {
			// 绑定端口
			acceptor.bind(new InetSocketAddress(bindPort));
			logger.debug("Mina Server run done! on port:" + bindPort);
		} catch (IOException e) {
			logger.error("Mina Server start for error!" + bindPort, e);
		}
	}

	/** 初始化处理器 */
	private static void initHanlder(MinaServerHanlder hanlder) {
		hanlder.addDataHandlerType(HData.TYPE_MSG, HandlerMsg.class);
		hanlder.addDataHandlerType(HData.TYPE_ACCOUNT_CHANGE_PASSWORDR,
				HandlerChangePassword.class);
		hanlder.addDataHandlerType(HData.TYPE_ACCOUNT_LOGOUT,
				HandlerLogout.class);
		hanlder.addDataHandlerType(HData.TYPE_ACCOUNT_REGISTER,
				HandlerRegisterAndLogin.class);
		hanlder.addDataHandlerType(HData.TYPE_ACCOUNT_LOGIN,
				HandlerRegisterAndLogin.class);
		hanlder.addDataHandlerType(HData.TYPE_HEART, HandlerHeart.class);
	}
}
