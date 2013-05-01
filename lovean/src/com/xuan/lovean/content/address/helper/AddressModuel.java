package com.xuan.lovean.content.address.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xuan.lovean.content.address.ContentAddressUtils;

/**
 * 保存通讯录
 * 
 * @author xuan
 */
public abstract class AddressModuel {
	private static final String BIGAPPLE_ADDRESS_TABLE_NAME = "bigapple_address_table_name";// 存放通讯录的表

	private static boolean tableIsExists = false;// 标志表是否存在

	/**
	 * 获取临时保存的通讯录
	 * 
	 * @param context
	 * @param dataBaseName
	 * @return
	 */
	public static List<AddressEntity> getAeList(Context context,
			String dataBaseName) {
		List<AddressEntity> ret = new ArrayList<AddressEntity>();

		// 创建或者打开数据库
		SQLiteDatabase sqliteDatabase = context.openOrCreateDatabase(
				dataBaseName, Context.MODE_PRIVATE, null);

		try {
			// 判断表是否存在
			if (tableIsExist(BIGAPPLE_ADDRESS_TABLE_NAME, sqliteDatabase)) {
				getAeListByTableExists(sqliteDatabase, ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库不要忘记
			sqliteDatabase.close();
		}

		return ret;
	}

	/**
	 * 保存aeList数据，删除原先所有的，替换之
	 * 
	 * @param context
	 * @param dataName
	 * @param aeList
	 */
	public static void saveAeList(Context context, String dataBaseName,
			List<AddressEntity> aeList) {
		// 创建或者打开数据库
		SQLiteDatabase sqliteDatabase = context.openOrCreateDatabase(
				dataBaseName, Context.MODE_PRIVATE, null);

		try {
			// 判断表是否存在
			if (!tableIsExist(BIGAPPLE_ADDRESS_TABLE_NAME, sqliteDatabase)) {
				// 不存在新建之
				String CREATE_TABLE = "CREATE TABLE "
						+ BIGAPPLE_ADDRESS_TABLE_NAME
						+ "(name TEXT, phone TEXT)";
				sqliteDatabase.execSQL(CREATE_TABLE);
			}

			// 清空之前的数据
			String DELETE_DATA = "DELETE FROM " + BIGAPPLE_ADDRESS_TABLE_NAME;
			sqliteDatabase.execSQL(DELETE_DATA);

			// 循环插入数据
			String INSERT_DATA = "INSERT INTO " + BIGAPPLE_ADDRESS_TABLE_NAME
					+ "(name, phone) VALUES(?,?)";
			for (AddressEntity ae : aeList) {
				sqliteDatabase.execSQL(INSERT_DATA, new Object[] {
						ae.getName(), ae.getPhone() });
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			// 关闭数据库不要忘记
			sqliteDatabase.close();
		}
	}

	/**
	 * 保证通讯录的数据和我缓存的一致
	 * 
	 * @param context
	 * @param dataBaseName
	 */
	public static void keepAddressSame(Context context, String dataBaseName) {
		List<AddressEntity> aeList = ContentAddressUtils
				.getAllContacts(context);
		saveAeList(context, dataBaseName, aeList);
	}

	// 查询通讯录记录
	private static List<AddressEntity> getAeListByTableExists(
			SQLiteDatabase sqliteDatabase, List<AddressEntity> aeList) {
		Cursor cursor = null;
		try {
			String sql = "SELECT name, phone FROM "
					+ BIGAPPLE_ADDRESS_TABLE_NAME;
			cursor = sqliteDatabase.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				AddressEntity ae = new AddressEntity();
				ae.setName(cursor.getString(0));
				ae.setPhone(cursor.getString(1));

				aeList.add(ae);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			cursor = null;
		}

		return null;
	}

	// 判断表是否存在
	private static boolean tableIsExist(String tableName,
			SQLiteDatabase sqliteDatabase) {
		if (tableIsExists) {
			return true;
		}

		Cursor cursor = null;
		try {
			String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='"
					+ tableName + "' ";
			cursor = sqliteDatabase.rawQuery(sql, null);
			if (cursor != null && cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					tableIsExists = true;
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			cursor = null;
		}

		return false;
	}
}
