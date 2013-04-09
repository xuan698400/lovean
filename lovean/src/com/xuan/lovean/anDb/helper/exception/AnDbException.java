/* 
 * @(#)AnDbException.java    Created on 2013-4-8
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.anDb.helper.exception;

/**
 * 数据库操作异常
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-8 上午9:43:39 $
 */
public class AnDbException extends RuntimeException {
    private static final long serialVersionUID = -4102595288914379571L;

    private String strMsg = null;

    public AnDbException() {
    }

    public AnDbException(String strMsg) {
        this.strMsg = strMsg;
    }

    public void printStackTrace() {
        if (strMsg != null) {
            System.err.println(strMsg);
        }

        super.printStackTrace();
    }

}
