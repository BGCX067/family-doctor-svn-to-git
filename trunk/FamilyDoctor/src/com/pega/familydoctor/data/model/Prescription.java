package com.pega.familydoctor.data.model;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Prescription implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5831362409416406388L;
	
	@DatabaseField(generatedId = true)
	private int prescripId;
	
	@DatabaseField(columnName ="namePre")
	private String namePre;
	
	@DatabaseField(columnName="isReminded")
	private boolean isRemind;
	
	@DatabaseField(columnName="startDate")
	private Date startDate;
	
	@DatabaseField(columnName="morningTime")
	private String morningTime;
	
	@DatabaseField(columnName="noonTime")
	private String noonTime;
	
	@DatabaseField(columnName="everningTime")
	private String everningTime;
	
	@DatabaseField(columnName="historyUsed")
	private String historyUsed;

	public Prescription(String namePre, boolean isRemind, Date startDate,
			String morningTime, String noonTime, String everningTime) {
		super();
		this.namePre = namePre;
		this.isRemind = isRemind;
		this.startDate = startDate;
		this.morningTime = morningTime;
		this.noonTime = noonTime;
		this.everningTime = everningTime;
	}
	
	
	
	public Prescription() {
		
	}

	public int getPrescripId() {
		return prescripId;
	}

	public void setPrescripId(int prescripId) {
		this.prescripId = prescripId;
	}

	public String getNamePre() {
		return namePre;
	}

	public void setNamePre(String namePre) {
		this.namePre = namePre;
	}

	public boolean isRemind() {
		return isRemind;
	}

	public void setRemind(boolean isRemind) {
		this.isRemind = isRemind;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getMorningTime() {
		return morningTime;
	}

	public void setMorningTime(String morningTime) {
		this.morningTime = morningTime;
	}

	public String getNoonTime() {
		return noonTime;
	}

	public void setNoonTime(String noonTime) {
		this.noonTime = noonTime;
	}

	public String getEverningTime() {
		return everningTime;
	}

	public void setEverningTime(String everningTime) {
		this.everningTime = everningTime;
	}



	public String getHistoryUsed() {
		return historyUsed;
	}


	public void setHistoryUsed(String historyUsed) {
		this.historyUsed = this.historyUsed + historyUsed;
	}	
	
}
