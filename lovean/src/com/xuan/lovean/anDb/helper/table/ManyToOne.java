package com.xuan.lovean.anDb.helper.table;

/**
 * 多对一
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-8 上午9:37:45 $
 */
public class ManyToOne extends Property {
    private Class<?> manyClass;

    public Class<?> getManyClass() {
        return manyClass;
    }

    public void setManyClass(Class<?> manyClass) {
        this.manyClass = manyClass;
    }

}
