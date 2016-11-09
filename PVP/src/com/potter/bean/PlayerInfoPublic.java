package com.potter.bean;


/**
 * ��������Ϣ
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-11-1 ����9:09:42
 * @version 1.0
 */
public class PlayerInfoPublic {
	private long id;
	private int lv;// �ȼ�
	private String nickName;
	private int currentHp, maxHp;
	private int comprehensive;// �ۺ�ʵ��

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getComprehensive() {
		return comprehensive;
	}

	public void setComprehensive(int comprehensive) {
		this.comprehensive = comprehensive;
	}
}
