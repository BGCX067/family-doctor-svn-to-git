package com.pega.familydoctor.data.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pega.familydoctor.config.Defines;

@DatabaseTable
public class Pill implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1136218651147410358L;
	
	@DatabaseField(generatedId=true)
	private int pillID;
	
	@DatabaseField(columnName="preID")
	private int preID;
	
	@DatabaseField(columnName="amount")
	private int amount;
	
	@DatabaseField(columnName="amountRemain")
	private int amountRemain;
	
	@DatabaseField(columnName="amountMorning")
	private int amountMorning;
	
	@DatabaseField(columnName="amountNoon")
	private int amountNoon;
	
	@DatabaseField(columnName="amountEverning")
	private int amountEverning;
	
	@DatabaseField(columnName="linkImage")
	private String linkImage;
	
	@DatabaseField(columnName="namePill")
	private String namePill;

	public Pill(int preID, int amount, int amountRemain, int amountMorning,
			int amountNoon, int amountEverning, String linkImage, String namePill) {
		super();
		this.preID = preID;
		this.amount = amount;
		this.amountRemain = amountRemain;
		this.amountMorning = amountMorning;
		this.amountNoon = amountNoon;
		this.amountEverning = amountEverning;
		this.linkImage = linkImage;
		this.namePill = namePill;
	}
	
	public Pill() {
		
	}

	public int getPillID() {
		return pillID;
	}

	public void setPillID(int pillID) {
		this.pillID = pillID;
	}

	public int getPreID() {
		return preID;
	}

	public void setPreID(int preID) {
		this.preID = preID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmountRemain() {
		return amountRemain;
	}

	public void setAmountRemain(int amountRemain) {
		this.amountRemain = amountRemain;
	}

	public int getAmountMorning() {
		return amountMorning;
	}

	public void setAmountMorning(int amountMorning) {
		this.amountMorning = amountMorning;
	}

	public int getAmountNoon() {
		return amountNoon;
	}

	public void setAmountNoon(int amountNoon) {
		this.amountNoon = amountNoon;
	}

	public int getAmountEverning() {
		return amountEverning;
	}

	public void setAmountEverning(int amountEverning) {
		this.amountEverning = amountEverning;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}		
	
	public String getNamePill() {
		return namePill;
	}

	public void setNamePill(String namePill) {
		this.namePill = namePill;
	}

	public void reduceAmount(int type) {
		int subAmount = 0;
		switch (type) {
		case Defines.TYPE_MORNING:
			subAmount = this.amountMorning;
			break;
		case Defines.TYPE_NOON:
			subAmount = this.amountNoon;
			break;
		case Defines.TYPE_EVERNING:
			subAmount = this.amountEverning;
			break;
		default:
			break;
		}
		this.amountRemain = this.amountRemain - subAmount;
	}
	
	
}
