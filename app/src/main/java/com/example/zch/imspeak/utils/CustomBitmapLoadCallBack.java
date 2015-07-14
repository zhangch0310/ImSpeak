package com.example.zch.imspeak.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {

	@Override
	public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
		super.onLoadCompleted(container, uri, bitmap, config, from);
		// 变成正圆显示
		bitmap = BitmapIncise.getCroppedBitmap(bitmap);
		container.setImageBitmap(bitmap);
	}

	@Override
	public void onLoadFailed(ImageView container, String uri, Drawable drawable) {
		super.onLoadFailed(container, uri, drawable);

	}
}
