package com.example.zch.imspeak.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 
 * @author bamboo E-mail:bzy601638015@126.com
 * @version 创建时间：2015-1-20 下午8:44:23 <br/>
 *          类说明:获取屏幕的大小
 */
public class ScreenUtils {

	/**
	 * 获取屏幕的宽、高
	 * 
	 * @param activity
	 * @return [0] width<br/>
	 *         [1] height
	 */
	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new int[] { dm.widthPixels, dm.heightPixels };
	}

	/**
	 * 获取屏幕的宽、高
	 * 
	 * @param context
	 * @param type <br/>0 deprecated<br/>
	 *             1 not deprecated
	 * @return [0] width<br/>
	 *         [1] height
	 */
	public static int[] getScreenSize(Context context, int type) {
		int width = 0;
		int height = 0;
		if (type == 0) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			width = display.getWidth();
			height = display.getHeight();
		} else {
			DisplayMetrics dm = new DisplayMetrics();
			dm = context.getResources().getDisplayMetrics();
			width = dm.widthPixels;
			height = dm.heightPixels;
		}
		return new int[] { width, height };
	}

}
