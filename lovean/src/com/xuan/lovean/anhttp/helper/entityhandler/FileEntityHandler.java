package com.xuan.lovean.anhttp.helper.entityhandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

import android.text.TextUtils;

/**
 * 文件结果处理
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-7 下午6:32:27 $
 */
public class FileEntityHandler {

    private boolean mStop = false;// 是否停止标识

    public boolean isStop() {
        return mStop;
    }

    public void setStop(boolean stop) {
        this.mStop = stop;
    }

    /**
     * 结果处理
     * 
     * @param entity
     *            结果
     * @param callback
     *            接口回调
     * @param target
     *            文件存放路径
     * @param isResume
     *            是否是断点续传
     * @return
     * @throws IOException
     */
    public Object handleEntity(HttpEntity entity, EntityCallBack callback, String target, boolean isResume)
            throws IOException {
        if (TextUtils.isEmpty(target) || target.trim().length() == 0) {
            return null;
        }

        File targetFile = new File(target);

        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }

        if (mStop) {
            return targetFile;
        }

        long current = 0;
        FileOutputStream os = null;
        if (isResume) {
            current = targetFile.length();
            os = new FileOutputStream(target, true);// 往原有程序添加
        }
        else {
            os = new FileOutputStream(target);
        }

        if (mStop) {
            os.close();// add by xuan
            return targetFile;
        }

        InputStream input = entity.getContent();
        long count = entity.getContentLength() + current;

        if (current >= count || mStop) {
            os.close();// add by xuan
            return targetFile;
        }

        int readLen = 0;
        byte[] buffer = new byte[1024];
        while (!mStop && !(current >= count) && ((readLen = input.read(buffer, 0, 1024)) > 0)) {// 未全部读取
            os.write(buffer, 0, readLen);
            current += readLen;

            if (callback != null) {
                callback.callBack(count, current, false);
            }
        }

        if (callback != null) {
            callback.callBack(count, current, true);
        }

        if (mStop && current < count) { // 用户主动停止
            os.close();// add by xuan
            throw new IOException("user stop download thread");
        }

        os.close();// add by xuan
        return targetFile;
    }

}
