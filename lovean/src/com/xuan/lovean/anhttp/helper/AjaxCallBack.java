package com.xuan.lovean.anhttp.helper;

/**
 * 请求回调接口
 * 
 * @param <T>
 *            目前泛型支持 String,File, 以后扩展：JSONObject,Bitmap,byte[],XmlDom
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-7 下午5:53:01 $
 */
public abstract class AjaxCallBack<T> {

    private boolean progress = true;
    private int rate = 1000 * 1;// 每秒

    public boolean isProgress() {
        return progress;
    }

    public int getRate() {
        return rate;
    }

    /**
     * 设置进度,而且只有设置了这个了以后，onLoading才能有效。
     * 
     * @param progress
     *            是否启用进度显示
     * @param rate
     *            进度更新频率
     */
    public AjaxCallBack<T> progress(boolean progress, int rate) {
        this.progress = progress;
        this.rate = rate;
        return this;
    }

    public void onStart() {
    };

    /**
     * onLoading方法有效progress
     * 
     * @param count
     * @param current
     */
    public void onLoading(long count, long current) {
    };

    public void onSuccess(T t) {
    };

    public void onFailure(Throwable t, String strMsg) {
    };

}
