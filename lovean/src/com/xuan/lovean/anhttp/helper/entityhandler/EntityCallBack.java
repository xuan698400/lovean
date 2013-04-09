package com.xuan.lovean.anhttp.helper.entityhandler;

/**
 * 从服务器上获取数据时的回调方法
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-7 下午6:42:41 $
 */
public interface EntityCallBack {
    public void callBack(long count, long current, boolean mustNoticeUI);
}
