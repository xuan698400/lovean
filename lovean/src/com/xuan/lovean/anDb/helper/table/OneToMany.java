package com.xuan.lovean.anDb.helper.table;

/**
 * 一对多
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-8 上午9:38:13 $
 */
public class OneToMany extends Property {

    private Class<?> oneClass;

    public Class<?> getOneClass() {
        return oneClass;
    }

    public void setOneClass(Class<?> oneClass) {
        this.oneClass = oneClass;
    }

}
