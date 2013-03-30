/* 
 * @(#)UUIDUtils.java    Created on 2012-9-28
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.lovean.utils.uuid;

/**
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2012-9-28 下午07:30:51 $
 */
public class UUIDUtils {
	private UUIDUtils() {
	}

	/**
	 * 生成32位的uuid字符串
	 * 
	 * @return 32位的uuid字符串
	 */
	public static String createId() {
		return UUIDGenerator.generateHex();
	}

}
