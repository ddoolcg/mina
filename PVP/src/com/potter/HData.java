package com.potter;

import java.nio.charset.Charset;

/**
 * 数据
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-9-3 下午4:26:51
 * @version 1.0
 */
public class HData {
	/** 主要用于心跳 */
	public static final short TYPE_HEART = 0;
	public static final short TYPE_MSG = 1;
	public static final short TYPE_ACCOUNT_REGISTER = 2;
	public static final short TYPE_ACCOUNT_LOGIN = 3;
	public static final short TYPE_ACCOUNT_CHANGE_PASSWORDR = 4;
	public static final short TYPE_ACCOUNT_LOGOUT = 5;
	private short type;
	private String data;

	public HData() {
		super();
	}

	public HData(short type, String data) {
		super();
		this.type = type;
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	/** 获取数据长度 */
	public int getLength(Charset charset) {
		int len = 4;// 头部
		len += 2;// short
		if (data != null && !"".equals(data)) {
			len += data.getBytes(charset).length;
		}
		return len;
	}

	@Override
	public String toString() {
		return "HData [type=" + type + ", data=" + data + "]";
	}

}
