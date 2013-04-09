package com.xuan.lovean.anDb.helper.sqlite;

import java.util.HashMap;
import java.util.Map.Entry;

import android.database.Cursor;

import com.xuan.lovean.anDb.helper.table.Property;
import com.xuan.lovean.anDb.helper.table.TableInfo;

/**
 * 数据库返回结果处理工具
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-8 下午2:03:15 $
 */
public class CursorUtils {

    /**
     * 解析Cursor对象，把值设置到具体对象中去
     * 
     * @param cursor
     * @param clazz
     * @return
     */
    public static <T> T getEntity(Cursor cursor, Class<T> clazz) {
        try {
            if (cursor != null) {
                TableInfo table = TableInfo.get(clazz);
                int columnCount = cursor.getColumnCount();
                if (columnCount > 0) {
                    T entity = clazz.newInstance();
                    for (int i = 0; i < columnCount; i++) {

                        String column = cursor.getColumnName(i);

                        Property property = table.propertyMap.get(column);
                        if (property != null) {
                            property.setValue(entity, cursor.getString(i));
                        }
                        else {
                            if (table.getId().getColumn().equals(column)) {
                                table.getId().setValue(entity, cursor.getString(i));
                            }
                        }

                    }
                    return entity;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 把Cursor中的数据存放到DbModel中
     * 
     * @param cursor
     * @return
     */
    public static DbModel getDbModel(Cursor cursor) {
        if (cursor != null && cursor.getColumnCount() > 0) {
            DbModel model = new DbModel();
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                model.set(cursor.getColumnName(i), cursor.getString(i));
            }
            return model;
        }
        return null;
    }

    /**
     * 把dbModel的数据存放到clazz中去
     * 
     * @param dbModel
     * @param clazz
     * @return
     */
    public static <T> T dbModel2Entity(DbModel dbModel, Class<?> clazz) {
        if (dbModel != null) {
            HashMap<String, Object> dataMap = dbModel.getDataMap();
            try {
                @SuppressWarnings("unchecked")
                T entity = (T) clazz.newInstance();
                for (Entry<String, Object> entry : dataMap.entrySet()) {
                    String column = entry.getKey();
                    TableInfo table = TableInfo.get(clazz);
                    Property property = table.propertyMap.get(column);
                    if (property != null) {
                        property.setValue(entity, entry.getValue() == null ? null : entry.getValue().toString());
                    }
                    else {
                        if (table.getId().getColumn().equals(column)) {
                            table.getId().setValue(entity,
                                    entry.getValue() == null ? null : entry.getValue().toString());
                        }
                    }

                }
                return entity;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
