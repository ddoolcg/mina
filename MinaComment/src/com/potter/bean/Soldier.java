package com.potter.bean;

/**
 * ʿ��
 * 
 * @author lei.chuguang Email:475825657@qq.com
 * @since 2016-11-1 ����8:17:17
 * @version 1.0
 */
public class Soldier {
	private int id;
	private int lv;
	private int attack, defense;// ������������
	private float moveSpeed, attackSpeed;// �ƶ��ٶȡ������ٶ�10��Ϊ��λ��
	private int currentHp, maxHp;
	private int range;// ��������

	public Soldier() {
		super();
		this.lv = 1;
		this.attack = 2;
		this.defense = 1;
		this.currentHp = 200;
		this.maxHp = 200;
		this.moveSpeed = 10;// ��ʱ�̶�
		this.attackSpeed = 5;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
}
