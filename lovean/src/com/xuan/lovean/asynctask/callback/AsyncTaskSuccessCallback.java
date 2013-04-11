/* 
 * @(#)AsyncTaskSuccessCallback.java    Created on 2013-2-17
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.asynctask.callback;

import com.xuan.lovean.asynctask.helper.Result;

/**
 * 耗时任务成功回调接口
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-2-17 下午4:28:41 $
 */
public interface AsyncTaskSuccessCallback<T> {
    /**
     * 执行方法
     * 
     * @param result
     */
    public void successCallback(Result<T> result);
}
