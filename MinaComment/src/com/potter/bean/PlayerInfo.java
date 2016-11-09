package com.potter.bean;

import com.j256.ormlite.field.DatabaseField;

/**
 * 玩家信息
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-27 上午10:44:36
 * @version 1.0
 */
public class PlayerInfo extends PlayerInfoPublic {
	@DatabaseField
	private int currentExp, maxExp;// 当前经验、最大升级经验。
	@DatabaseField
	private int minAttack, maxAttack, defense, crit, critDefense;// 最小攻击、最大攻击、防御、暴击、韧性。
	@DatabaseField
	private float moveSpeed, attackSpeed;// 移动速度、攻击速度10秒为单位。
	@DatabaseField
	private int perHP;
	public Personnel[][] personnels = new Personnel[3][3];// 3种类型的人才，最多各允许3个。
	public int coins, coinPer;// 金币,每秒生成的金币数量。

	public PlayerInfo() {
		super();
		currentExp = 0;
		maxExp = 100;
		minAttack = 1;
		maxAttack = 3;
		defense = 1;
		crit = 0;
		critDefense = 0;
		moveSpeed = 10.0f;
		attackSpeed = 2.0f;
		coins = 100;
		coinPer = 2;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	public int getMinAttack() {
		return minAttack;
	}

	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}

	public int getMaxAttack() {
		return maxAttack;
	}

	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getCrit() {
		return crit;
	}

	public void setCrit(int crit) {
		this.crit = crit;
	}

	public int getCritDefense() {
		return critDefense;
	}

	public void setCritDefense(int critDefense) {
		this.critDefense = critDefense;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getPerHP() {
		return perHP;
	}

	public void setPerHP(int perHP) {
		this.perHP = perHP;
	}
}
