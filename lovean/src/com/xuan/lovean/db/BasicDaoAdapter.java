package com.xuan.lovean.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.xuan.lovean.db.helper.SqlCreator;
import com.xuan.lovean.db.interfaces.MapRowMapper;
import com.xuan.lovean.db.interfaces.MultiRowMapper;
import com.xuan.lovean.db.interfaces.SingleRowMapper;

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

    // ----------------------------插入或者更新------------------------------------

    /**
     * 不带参数的插入或者更新
     * 
     * @param sql
     */
    protected void update(String sql) {
        sqliteDatabase.execSQL(sql);
    }

    /**
     * 带参数插入
     * 
     * @param sql
     * @param args
     */
    protected void update(String sql, Object[] args) {
        if (null == args) {
            update(sql);
        }
        else {
            sqliteDatabase.execSQL(sql, args);
        }
    }

    // ----------------------------查询---------------------------------------------------

    /**
     * 查询，返回多条记录
     * 
     * @param sql
     * @param args
     * @param multiRowMapper
     * @return
     */
    protected <T> List<T> query(String sql, String[] args, MultiRowMapper<T> multiRowMapper) {
        List<T> ret = new ArrayList<T>();

        Cursor cursor = sqliteDatabase.rawQuery(sql, args);

        // 没有数据
        if (false == cursor.moveToFirst()) {
            return ret;
        }

        // 便利数据
        try {
            int i = 0;
            while (cursor.moveToNext()) {
                T t = multiRowMapper.mapRow(cursor, i);
                ret.add(t);
                i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        return ret;
    }

    /**
     * 查询，返回单条记录
     * 
     * @param sql
     * @param args
     * @param singleRowMapper
     * @return
     */
    protected <T> T query(String sql, String[] args, SingleRowMapper<T> singleRowMapper) {
        T ret = null;

        Cursor cursor = sqliteDatabase.rawQuery(sql, args);

        // 没有数据
        if (false == cursor.moveToFirst()) {
            return ret;
        }

        // 便利数据
        try {
            cursor.moveToNext();
            ret = singleRowMapper.mapRow(cursor);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        return ret;
    }

    /**
     * 查询，返回MAP集合
     * 
     * @param sql
     * @param args
     * @param singleRowMapper
     * @return
     */
    protected <K, V> Map<K, V> query(String sql, String[] args, MapRowMapper<K, V> mapRowMapper) {
        Map<K, V> ret = new HashMap<K, V>();

        Cursor cursor = sqliteDatabase.rawQuery(sql, args);

        // 没有数据
        if (false == cursor.moveToFirst()) {
            return ret;
        }

        // 便利数据
        int i = 0;
        try {
            while (cursor.moveToNext()) {
                K k = mapRowMapper.mapRowKey(cursor, i);
                V v = mapRowMapper.mapRowValue(cursor, i);
                ret.put(k, v);
                i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }

        return ret;
    }

    /**
     * IN查询，返回LIST集合
     * 
     * @param sql
     * @param args
     * @param multiRowMapper
     * @return
     */
    protected <T> List<T> queryForInSQL(String prefix, String[] prefixArgs, String[] inArgs, String postfix,
            MultiRowMapper<T> multiRowMapper) {
        if (null == prefixArgs) {
            prefixArgs = new String[0];
        }

        StringBuilder sql = new StringBuilder();
        sql.append(prefix).append(SqlCreator.getInSQL(inArgs.length));

        if (!TextUtils.isEmpty(postfix)) {
            sql.append(postfix);
        }

        String[] args = new String[inArgs.length + prefixArgs.length];
        for (int i = 0, length = prefixArgs.length; length > i; i++) {
            args[i] = prefixArgs[i];
        }

        return query(sql.toString(), args, multiRowMapper);
    }

    /**
     * IN查询，返回MAP集合
     * 
     * @param sql
     * @param args
     * @param multiRowMapper
     * @return
     */
    protected <K, V> Map<K, V> queryForInSQL(String prefix, String[] prefixArgs, String[] inArgs, String postfix,
            MapRowMapper<K, V> mapRowMapper) {
        if (null == prefixArgs) {
            prefixArgs = new String[0];
        }

        StringBuilder sql = new StringBuilder();
        sql.append(prefix).append(SqlCreator.getInSQL(inArgs.length));

        if (!TextUtils.isEmpty(postfix)) {
            sql.append(postfix);
        }

        String[] args = new String[inArgs.length + prefixArgs.length];
        for (int i = 0, length = prefixArgs.length; length > i; i++) {
            args[i] = prefixArgs[i];
        }

        return query(sql.toString(), args, mapRowMapper);
    }

}
