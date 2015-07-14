package com.example.zch.imspeak.utils;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 
 * 图片切割工具 -项目中使用的地方：获取用户的等级、缩小图片的比例、图片裁减成正圆
 * 
 * 使用方式：
 * 
 * 创建：bitmapIncise = new BitmapIncise(MainActivity.this, R.drawable.num, 10);
 * 
 * 调用： txttime.setImageBitmap(bitmapIncise .getImgByIndex(0));
 * 
 * @author 鲍志远
 */
public class BitmapIncise {
	@SuppressWarnings("unused")
	private final String TAG = "BitmapIncise";
	/** 图片资源 */
	private Bitmap bitmap;
	/** 切分数量个数 */
	private int count;

	/**
	 * 初始化上下文对象以及含有数字的图片
	 * 
	 * @param context
	 *            将来图片要加载到哪个Activity上
	 * @param drawableId
	 *            含有数据的图片id
	 * @param count
	 *            切分数量个数
	 */
	public BitmapIncise(Context context, int drawableId, int count) {
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
		this.count = count;
	}

	/**
	 * 截取的图片是 横向的话 ，采用以下方法
	 * 
	 * @param num
	 *            截取的第几个位置，角标从0开始的
	 * @return 截取的图片
	 */
	public Bitmap getImgByHorizontalIndex(int index) {
		int indexW = bitmap.getWidth() / count;
		return Bitmap.createBitmap(bitmap, index * indexW, 0, indexW, bitmap.getHeight());
	}

	/**
	 * 截取的图片是纵向的话，采用以下这个方法
	 * 
	 * @param num
	 *            截取的第几个位置，角标从0开始的
	 * @return 截取的图片
	 */
	public Bitmap getImgByVerticalIndex(int index) {
		int indexH = bitmap.getHeight() / count;
		return Bitmap.createBitmap(bitmap, 0, index * indexH, bitmap.getWidth(), indexH);
	}

	/**
	 * 生成比例图片
	 * 
	 * @param bitmap
	 *            原图
	 * @param scaleW
	 *            宽的缩略比 范围在[0,1]
	 * @param scaleH
	 *            高的缩略比
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, float scaleW, float scaleH) {
		// 1.创建一个可以修改的图片 注： 如果创建可以修改的图片的时候大小不是按照比例缩放的话，虽然图片显示小了，但是宽高还是没有变化
		// 所以在创建的时候，宽高一开始就乘以缩放比
		Bitmap alertBm = Bitmap.createBitmap((int) (bitmap.getWidth() * scaleW), (int) (bitmap.getHeight() * scaleH), Config.ARGB_8888);
		Canvas canvas = new Canvas(alertBm);
		Paint paint = new Paint();
		Matrix matrix = new Matrix();
		// 缩放比例
		matrix.setScale(scaleW, scaleH);
		// 2.形成了图片
		canvas.drawBitmap(bitmap, matrix, paint);

		return alertBm;
	}

	/**
	 * 形成正圆图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getCroppedBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		// 设置一个图片大小的矩形
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		// bm是一个刚好canvas大小的空Bitmap ，画完后应该会自动保存到bm
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		paint.setAntiAlias(true);

		int halfWidth = bitmap.getWidth() / 2;
		int halfHeight = bitmap.getHeight() / 2;
		// 画圆 注：因为图片有可能是矩形，如果不用小的当作半径的话，正圆将画不出来
		canvas.drawCircle(halfWidth, halfHeight, Math.min(halfWidth, halfHeight), paint);
		// 设置为取两层图像交集部门,只显示上层图像
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 画图像
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 旋转图片
	 * 
	 * @param iv
	 *            图片控件
	 * @param angle
	 *            旋转的角度
	 * 
	 * @param bitmap
	 *            传入需要旋转的图片
	 * 
	 * @return Bitmap 旋转之后的图片
	 */
	public static void rotaingImageView(ImageView iv, int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		iv.setImageBitmap(resizedBitmap);
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 解决图片被旋转问题,例如：三星手机
	 * 
	 * @param filePath
	 * @param iv
	 */
	public static void rotateImage(ImageView iv, String filePath, Bitmap bitmap) {
		int degree = readPictureDegree(filePath);
		rotaingImageView(iv, degree, bitmap);
	}

	/**
	 * 功能:将view转换成bitmap
	 * 
	 * @param view
	 * @return
	 * @author: huchao
	 * @date:2015-7-8下午3:23:25
	 */
	public static Bitmap convertViewToBitmap(View view) {
		view = view.getRootView();
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();

		return bitmap;
	}


	/*
	 * 手机截屏
	 */
	public static Bitmap takeScreenShot(Activity activity) {

		// View是你需要截图的View

		View view = activity.getWindow().getDecorView();

		view.setDrawingCacheEnabled(true);

		view.buildDrawingCache();

		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度

		Rect frame = new Rect();

		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

		int statusBarHeight = frame.top;

		System.out.println(statusBarHeight);// 获取屏幕长和高

		int width = activity.getWindowManager().getDefaultDisplay().getWidth();

		int height = activity.getWindowManager().getDefaultDisplay().getHeight();// 去掉标题栏

		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);

		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);

		view.destroyDrawingCache();

		return b;

	}

}
