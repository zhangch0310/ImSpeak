package com.example.zch.imspeak.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.zch.imspeak.R;
import com.lidroid.xutils.BitmapUtils;

/***
 * 用于管理缓存对象 为了是整个应用的缓存都指向同一个位置
 * 
 * 基本用法： bitmapUtils =
 * BitmapHelp.getBitmapUtils(this.getActivity().getApplicationContext());
 * bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
 * bitmapUtils.configDefaultLoadFailedImage(R.drawable.bitmap);
 * bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
 * 
 * @author 鲍志远
 */
public class BitmapHelp {

	private BitmapHelp() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(Context appContext) {
		if (bitmapUtils == null) {
			// 创建BitmapUtils对象，并创建文件缓存位置
			bitmapUtils = new BitmapUtils(appContext, getCacheRoot(appContext));
		}
		configDefaultInfo();
		return bitmapUtils;
	}

	/***
	 * 得到文件缓存的位置
	 * 
	 * @param appContext
	 * @return
	 * @update 2014-5-15 下午3:03:30
	 */
	public static String getCacheRoot(Context appContext) {
		String root = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ appContext.getString(R.string.dir)
				+ appContext.getString(R.string.app_dir)
				+ appContext.getString(R.string.cache);
		return root;
	}

	private static void configDefaultInfo() {
		bitmapUtils.configDefaultLoadingImage(R.mipmap.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}

}
