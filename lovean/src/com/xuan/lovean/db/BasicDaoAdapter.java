package com.xuan.lovean.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 操作数据库表的适配器基类
 * 
 * @author xuan
 */
public class BasicDaoAdapter {
	private final SQLiteDatabase sqliteDatabase;

	private final DBHelper dbHelper;
	private final Context context;

	public BasicDaoAdapter(Context context) {
		dbHelper = new DBHelper(context);
		this.context = context;
		sqliteDatabase = dbHelper.getWritableDatabase();
	}

	public SQLiteDatabase getSQLiteDatabase() {
		return sqliteDatabase;
	}

	/**
	 * 使用完后请Close数据库连接
	 */
	public void close() {
		dbHelper.close();
	}

	protected Context getContext() {
		return context;
	}

}
