package com.xuan.lovean.content.address.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.xuan.lovean.content.address.ContentAddressUtils;

public class AddressContentObserver extends ContentObserver {
	private AddressChangeListener addressChangeListener;

	private final Context context;
	private final String dataBaseName;
	private final Handler handler;

	public AddressContentObserver(Handler handler, Context context,
			String dataBaseName, AddressChangeListener addressChangeListener) {
		super(handler);
		this.context = context;
		this.dataBaseName = dataBaseName;
		this.handler = handler;
		this.addressChangeListener = addressChangeListener;
	}

	/**
	 * 当所监听的Uri发生改变时，就会回调此方法
	 * 
	 * @param selfChange
	 *            此值意义不大 一般情况下该回调值false
	 */
	@Override
	public void onChange(boolean selfChange) {
		if (null == addressChangeListener) {
			// 没设置监听器不错处理
			return;
		}

		// 原有数据
		List<AddressEntity> oldAeList = AddressModuel.getAeList(context,
				dataBaseName);
		Map<String, AddressEntity> oldTmp = new HashMap<String, AddressEntity>();
		for (AddressEntity ae : oldAeList) {
			oldTmp.put(ae.getPhone(), ae);
		}

		// 变化后的数据
		List<AddressEntity> newAeList = ContentAddressUtils
				.getAllContacts(context);

		// 开始比较，先找新加的
		final List<AddressEntity> addList = new ArrayList<AddressEntity>();
		Set<String> noChangePhoneSet = new HashSet<String>();

		for (AddressEntity ae : newAeList) {
			if (!oldTmp.containsKey(ae.getPhone())) {
				addList.add(ae);
			} else {
				noChangePhoneSet.add(ae.getPhone());
			}
		}

		// 比较查找删除的
		final List<AddressEntity> delList = new ArrayList<AddressEntity>();
		for (AddressEntity ae : oldAeList) {
			if (!noChangePhoneSet.contains(ae.getPhone())) {
				delList.add(ae);
			}
		}

		addressChangeListener.onChange(addList, delList);

		// 保持数据一致
		AddressModuel.keepAddressSame(context, dataBaseName);
	}

	/**
	 * 设置监听
	 * 
	 * @param addressChangeListener
	 */
	public void setAddressChangeListener(
			AddressChangeListener addressChangeListener) {
		this.addressChangeListener = addressChangeListener;
	}

	/**
	 * 当通讯录发生变化时调接口
	 * 
	 * @author xuan
	 */
	public interface AddressChangeListener {

		/**
		 * 变化方法
		 * 
		 * @param addAeList
		 *            新增
		 * @param delAeList
		 *            删除
		 * @param isFirst
		 *            是否是第一次添加
		 * 
		 */
		public void onChange(List<AddressEntity> addAeList,
				List<AddressEntity> delAeList);
	}
}
