package com.pega.familydoctor.data.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.pega.familydoctor.data.model.Pill;
import com.pega.familydoctor.data.model.Prescription;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application -- change to something
		// appropriate for your app
		private static final String DATABASE_NAME = "eprescription_database";

		// any time you make changes to your database objects, you may have to
		// increase the database version
		private static final int DATABASE_VERSION = 1;

		private Dao<Pill, Integer> pillDao = null;
		private Dao<Prescription, Integer> prescriptionDao = null;
		

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
			// TODO Auto-generated method stub
			try {
				TableUtils.createTable(connectionSource, Pill.class);
				TableUtils.createTable(connectionSource, Prescription.class);				
			} catch (SQLException e) {
				//Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
				throw new RuntimeException(e);
			}
		}

		

		public Dao<Pill, Integer> getPillRecordDao() {
			if (null == pillDao) {
				try {
					pillDao = getDao(Pill.class);
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
			return pillDao;
		}

		public Dao<Prescription, Integer> getPrescriptionDao() {
			if (null == prescriptionDao) {
				try {
					prescriptionDao = getDao(Prescription.class);
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
			return prescriptionDao;
		}		
		
		@Override
		public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
}
