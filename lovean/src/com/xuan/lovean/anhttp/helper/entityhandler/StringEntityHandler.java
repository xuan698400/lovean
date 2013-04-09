package com.xuan.lovean.anhttp.helper.entityhandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

/**
 * 用String方式处理服务器返回的内容
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-7 下午6:27:30 $
 */
public class StringEntityHandler {

    /**
     * 处理方法
     * 
     * @param entity
     *            获取到的结果
     * @param callback
     *            结果处理回调接口
     * @param charset
     *            字符编码
     * @return
     * @throws IOException
     */
    public Object handleEntity(HttpEntity entity, EntityCallBack callback, String charset) throws IOException {
        if (entity == null) {
            return null;
        }

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        long count = entity.getContentLength();
        long curCount = 0;
        int len = -1;
        InputStream is = entity.getContent();
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
            curCount += len;
            if (callback != null) {
                callback.callBack(count, curCount, false);
            }
        }

        // 数据全部读取完
        if (callback != null) {
            callback.callBack(count, curCount, true);
        }

        byte[] data = outStream.toByteArray();
        outStream.close();
        is.close();
        return new String(data, charset);
    }

}
