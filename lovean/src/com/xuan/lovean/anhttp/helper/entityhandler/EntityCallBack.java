package com.xuan.lovean.anhttp.helper.entityhandler;

/**
 * 从服务器上获取数据时的回调方法
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-7 下午6:42:41 $
 */
public interface EntityCallBack {
    /**
     * 获取数据的回调接口
     * 
     * @param count
     *            本次请求的数据长度
     * @param current
     *            数据读取的当前位置
     * @param mustNoticeUI
     *            数据读取完成时true
     */
    public void callBack(long count, long current, boolean mustNoticeUI);
}
