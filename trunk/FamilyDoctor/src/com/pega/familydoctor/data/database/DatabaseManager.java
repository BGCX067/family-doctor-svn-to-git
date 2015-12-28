package com.pega.familydoctor.data.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.pega.familydoctor.config.Defines;
import com.pega.familydoctor.data.model.Pill;
import com.pega.familydoctor.data.model.Prescription;
import com.pega.familydoctor.util.Utils;

public class DatabaseManager {
	static private DatabaseManager instance;
	static public boolean DATABASE_DEBUG = false;


	static public DatabaseManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new DatabaseManager(ctx);
		}
		return instance;
	}

	private DatabaseHelper helper;

	private DatabaseManager(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}

	private DatabaseHelper getHelper() {
		return helper;
	}

	/******************************************
	 * Function with table PILL
	 ******************************************/

	public int addPillRecord(Pill record) {
		try {
			getHelper().getPillRecordDao().create(record);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		return record.getPillID();
	}

	public Pill getPillRecordWithId(int id) {
		try {
			return getHelper().getPillRecordDao().queryForId(id);
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<Pill> getAllPillRecord() {
		ArrayList<Pill> pillList = null;
		try {
			pillList = (ArrayList<Pill>) getHelper().getPillRecordDao()
					.queryForAll();
		} catch (SQLException e) {
			// e.printStackTrace();
			return pillList;
		}
		return pillList;
	}

	public ArrayList<Pill> getAllPillOfPrescription(Prescription pres) {
		ArrayList<Pill> pillList = null;
		try {
			pillList = (ArrayList<Pill>) getHelper().getPillRecordDao().query(
					getHelper().getPillRecordDao().queryBuilder().where()
							.eq("preID", pres.getPrescripId()).prepare());
		} catch (SQLException e) {
			// e.printStackTrace();
			return pillList;
		}
		return pillList;
	}

	public ArrayList<Pill> getAllPillOfPrescriptionInTimeOfDay(
			Prescription pres, int type) throws SQLException {
		ArrayList<Pill> pillListResult = new ArrayList<Pill>();
		ArrayList<Pill> pillList = getAllPillOfPrescription(pres);
		for (Pill pill : pillList) {
			int amount = 0;
			switch (type) {
			case Defines.TYPE_MORNING:
				amount = pill.getAmountMorning();
				break;
			case Defines.TYPE_NOON:
				amount = pill.getAmountNoon();
				break;
			case Defines.TYPE_EVERNING:
				amount = pill.getAmountEverning();
				break;
			default:
				break;
			}
			if (amount > 0)
				pillListResult.add(pill);
		}
		return pillListResult;
	}

	public boolean updatePillRecord(Pill record) {
		try {
			getHelper().getPillRecordDao().update(record);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public boolean deletePillRecord(Pill record) {
		try {
			getHelper().getPillRecordDao().delete(record);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/******************************************
	 * Function with table PRESCRIPTION
	 ******************************************/
	public int addPrescriptionRecord(Prescription record) {
		try {
			getHelper().getPrescriptionDao().create(record);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		return record.getPrescripId();
	}

	public Prescription getPrescriptionRecordWithId(int id) {
		try {
			return getHelper().getPrescriptionDao().queryForId(id);
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<Prescription> getAllPrescriptionRecord() {
		ArrayList<Prescription> presList = null;
		try {
			presList = (ArrayList<Prescription>) getHelper()
					.getPrescriptionDao().queryForAll();
		} catch (SQLException e) {
			// e.printStackTrace();
			return presList;
		}
		return presList;
	}
	
	public boolean updatePrescriptionRecord(Prescription record) {
		try {
			getHelper().getPrescriptionDao().update(record);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	

	public boolean deletePrescriptionRecord(Prescription record) {
		try {
			getHelper().getPrescriptionDao().delete(record);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public boolean isAvailbleRemind(int type) {
		Prescription pres = getCurrentPrescription();
		ArrayList<Pill> pillList = null;
		try {
			pillList = getAllPillOfPrescriptionInTimeOfDay(pres, type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Pill pill : pillList) {			
			if (pill.getAmountRemain() > 0)
				return false;
		}
		return true;
	}

	public Prescription getCurrentPrescription() {
		ArrayList<Prescription> presList = getAllPrescriptionRecord();
		for (Prescription prescription : presList) {
			if (prescription.isRemind()
					&& Utils.sharedInstance().getCurrentDate()
							.after(prescription.getStartDate())) {
				return prescription;
			}
		}
		return null;
	}

	public void checkTakeMedicineOk(Prescription pres, int type) {
		ArrayList<Pill> pillArray = getAllPillOfPrescription(pres);
		boolean isOutOfPill = true;
		for (Pill pill : pillArray) {
			pill.reduceAmount(type);
			if (pill.getAmountRemain() > 0)
				isOutOfPill = false;
		}
		if (isOutOfPill) {
			pres.setRemind(false);
			pres.setHistoryUsed("1");
		}
	}

	public void checkTakeMedicineFail(Prescription pres, int type) {
		pres.setHistoryUsed("0");
	}
	
	public ArrayList<String> getHistoryOfWeek(Date date, int day) {
		ArrayList<String> result = new ArrayList<String>();
		Prescription pres = getCurrentPrescription();
		int distance = date.compareTo(pres.getStartDate());
		String history= pres.getHistoryUsed();
		if (day == Calendar.SUNDAY) {
			day = 8;
		}
		for (int i = 2; i<=day; i++) {
			result.add(getStringOfDay(history, distance - (day-i+1)));
		}		
		return result;
	}
	
	private String getStringOfDay(String str, int day) {
		if (day-1<0) return "";
		if ((day-1)*3+3>str.length()) return str.substring((day-1)*3);   
		return str.substring((day-1)*3, (day-1)*3+3);   
	}
	
}
