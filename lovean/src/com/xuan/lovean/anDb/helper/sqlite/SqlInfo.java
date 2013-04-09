package com.xuan.lovean.anDb.helper.sqlite;

import java.util.LinkedList;

public class SqlInfo {

    private String sql;
    private LinkedList<Object> bindArgs;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public LinkedList<Object> getBindArgs() {
        return bindArgs;
    }

    public void setBindArgs(LinkedList<Object> bindArgs) {
        this.bindArgs = bindArgs;
    }

    /**
     * 返回参数数组
     * 
     * @return
     */
    public Object[] getBindArgsAsArray() {
        if (bindArgs != null) {
            return bindArgs.toArray();
        }
        return null;
    }

    /**
     * 返回参数数组（字符串）
     * 
     * @return
     */
    public String[] getBindArgsAsStringArray() {
        if (bindArgs != null) {
            String[] strings = new String[bindArgs.size()];
            for (int i = 0; i < bindArgs.size(); i++) {
                strings[i] = bindArgs.get(i).toString();
            }
            return strings;
        }
        return null;
    }

    /**
     * 添加参数
     * 
     * @param obj
     */
    public void addValue(Object obj) {
        if (bindArgs == null) {
            bindArgs = new LinkedList<Object>();
        }

        bindArgs.add(obj);
    }

}
