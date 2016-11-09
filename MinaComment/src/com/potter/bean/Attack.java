package com.potter.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * 攻击,没想好 TODO
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-11-1 下午5:28:43
 * @version 1.0
 */
public class Attack {
	private long attackTargetId;
	@DatabaseField
	private short id;
	private short lv;
	private int x, y;
	@DatabaseField
	private int range;
	@DatabaseField
	private int minAttack, maxAttack;

}
