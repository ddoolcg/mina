package com.potter.bean;

/**
 * 人才，每分钟系统生成一些优质人才。
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-11-1 下午7:57:38
 * @version 1.0
 */
public class Personnel {
	private int id;
	private Type type;
	private int productivityRate, improve, proficiency;// 生产力、研发力、熟练度。每分钟产生多少点。
	private int pay;// 薪水每隔1分钟扣一次。

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getProductivityRate() {
		return productivityRate;
	}

	public void setProductivityRate(int productivityRate) {
		this.productivityRate = productivityRate;
	}

	public int getImprove() {
		return improve;
	}

	public void setImprove(int improve) {
		this.improve = improve;
	}

	public int getProficiency() {
		return proficiency;
	}

	public void setProficiency(int proficiency) {
		this.proficiency = proficiency;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private enum Type {
		CRAFTSMAN, RESEARCHER, TRAINER
	}
}
