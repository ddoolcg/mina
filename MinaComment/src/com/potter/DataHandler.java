package com.potter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * 数据处理器
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-9-21 下午9:16:59
 * @version 1.0
 */
public abstract class DataHandler<T> {
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 处理
	 * 
	 * @param type
	 *            处理器处理类型。
	 * @param data
	 *            数据
	 */
	protected abstract void onHandler(IoSession session, HData originalData,
			T obj);

	@SuppressWarnings("unchecked")
	public void handler(IoSession session, HData data) {
		String content = data.getData();
		//
		ParameterizedType parameterizedType = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		Type type2 = parameterizedType.getActualTypeArguments()[0];
		//
		if (!type2.equals(String.class)) {
			try {
				Object obj = JSON.parseObject(content, type2, new Feature[0]);
				onHandler(session, data, (T) obj);
			} catch (Exception e) {
				logger.error("解析错误：" + content, e);
				onHandler(session, data, null);
			}
		} else {
			onHandler(session, data, (T) content);
		}
	}

}
