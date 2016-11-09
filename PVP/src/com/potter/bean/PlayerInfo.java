package com.potter.bean;


/**
 * �����Ϣ
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-10-27 ����10:44:36
 * @version 1.0
 */
public class PlayerInfo extends PlayerInfoPublic {
	private int currentExp, maxExp;// ��ǰ���顢����������顣
	private int minAttack, maxAttack, defense, crit, critDefense;// ��С��������󹥻������������������ԡ�
	private float moveSpeed, attackSpeed;// �ƶ��ٶȡ������ٶ�10��Ϊ��λ��
	private int perHP;
	public Personnel[][] personnels = new Personnel[3][3];// 3�����͵��˲ţ���������3����
	public int coins, coinPer;// ���,ÿ�����ɵĽ��������

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
