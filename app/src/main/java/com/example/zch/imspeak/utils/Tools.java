package com.example.zch.imspeak.utils;

import java.io.File;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore.Images;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class Tools {

	/**
	 * 得到唯一字符串序列
	 * 
	 * @return
	 */
	public static synchronized String getNonce() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取android系统的版本
	 * 
	 * @return
	 */
	public static int getAndroidVersion() {
		return Build.VERSION.SDK_INT;
	}


	/**
	 * 长视频录制时间格式化
	 * 
	 * @param i
	 * @return
	 */
	public static String format(int i) {
		String s = i + "";
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 长视频录制时间格式化时、分、秒
	 * 
	 * @return string
	 */
	public static String formatTime(int hour, int minute, int second) {
		String s = "";
		if (hour == 0) {
			s = format(minute) + ":" + format(second);
		} else {
			s = format(hour) + ":" + format(minute) + ":" + format(second);
		}
		return s;
	}

	/**
	 * 格式化时间字符串
	 * 
	 * @param timeMs
	 *            毫秒
	 * @return 返回格式00:00:00
	 */
	public static String stringForTime(int timeMs) {

		StringBuilder formatBuilder = new StringBuilder();
		Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

		try {
			int totalSeconds = timeMs / 1000;

			int seconds = totalSeconds % 60;
			int minutes = (totalSeconds / 60) % 60;
			int hours = totalSeconds / 3600;

			formatBuilder.setLength(0);

			if (hours > 0) {
				return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
			} else {
				return formatter.format("%02d:%02d", minutes, seconds).toString();
			}
		} finally {
			formatter.close();
		}
	}

	/**
	 * 获得视频缩略图
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap createVideoThumbnail(String path) {
		Bitmap bitmap = null;
		try {
			bitmap = ThumbnailUtils.createVideoThumbnail(path, Images.Thumbnails.FULL_SCREEN_KIND);
		} catch (IllegalArgumentException ex) {
			return null;
		} catch (RuntimeException ex) {
			return null;
		} catch (OutOfMemoryError ex) {
			System.gc();
			return null;
		}
		return bitmap;
	}

	/**
	 * 获取当前程序的版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取当前程序的版本号
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取sd卡剩余空间大小
	 * 
	 * @return
	 */
	public static long getSdcardAvailableSpace() {

		if (!isSdcardAvailable()) {

			return -1;
		}

		File pathFile = Environment.getExternalStorageDirectory();

		StatFs statFs = new StatFs(pathFile.getPath());

		long block = statFs.getAvailableBlocks();

		long size = statFs.getBlockSize();

		return size * block;
	}

	/**
	 * 检查sd是否可用
	 * 
	 * @return
	 */
	public static boolean isSdcardAvailable() {
		boolean exist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
		return exist;
	}

	/**
	 * 计算可录制视频的总时长
	 * 
	 * @param videoBiteRate
	 * @param audioBiteRate
	 * @return
	 */
	public static String getRemainRecorderTime(int videoBiteRate, int audioBiteRate) {
		long sdcardAvailableSpace = getSdcardAvailableSpace();
		int remainTime = 0;
		if (sdcardAvailableSpace != -1) {
			remainTime = (int) (sdcardAvailableSpace / ((videoBiteRate + audioBiteRate) / 8));
		}
		return recorderTimeConvert2String(remainTime);
	}

	/***
	 * 录像剩余时间 s（秒） 转换为 00:00:00
	 * 
	 * @param time
	 * @return
	 */
	public static String recorderTimeConvert2String(int time) {
		int hour = 0;
		int minute = 0;
		int second = 0;

		second = time;

		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		return (format(hour) + ":" + format(minute) + ":" + format(second));
	}

	/**
	 * 获取屏幕密度
	 * 
	 * @param ctx
	 * @return
	 */
	public static float getDisplayDensity(Context ctx) {
		float desity = ctx.getResources().getDisplayMetrics().density;
		return desity;
	}



	/**
	 * 得到设备名字
	 * */
	public static String getDeviceName() {
		String model = Build.MODEL;
		if (model == null || model.length() <= 0) {
			return "";
		} else {
			return model;
		}
	}

	/**
	 * 得到品牌名字
	 * */
	public static String getBrandName() {
		String brand = Build.BRAND;
		if (brand == null || brand.length() <= 0) {
			return "";
		} else {
			return brand;
		}
	}

	/**
	 * 得到操作系统版本号
	 */
	public static String getOSVersionName() {
		return Build.VERSION.RELEASE;
	}

	

	/**
	 * 获取移动设备国际识别码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		if (context == null) {
			return "";
		}
		try {
			String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			if (null == deviceId || deviceId.length() <= 0) {
				return "";
			} else {
				return deviceId.replace(" ", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}


	// 设备唯一标识
	public static String getDeviceId(Context context) {
		String deviceId = "";
		deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		return deviceId;
	}


	/**
	 * 获取MAC地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		if (context == null) {
			return "";
		}
		try {
			String macAddress = null;
			WifiInfo wifiInfo = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
			macAddress = wifiInfo.getMacAddress();
			if (macAddress == null || macAddress.length() <= 0) {
				return "";
			} else {
				return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取分辨率 按xxx_xxx格式输出
	 * 
	 * @param context
	 * @return
	 */
	public static String getResolution(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return new StringBuilder().append(dm.widthPixels).append("_").append(dm.heightPixels).toString();
	}

	public static long getVideoDuration(Context context, String path) {
		long duration = 0L;
		File file = new File(path);
		if (file.exists()) {
			try {
				MediaPlayer mp = MediaPlayer.create(context, Uri.parse(path));
				duration = mp.getDuration();
				mp.release();
				mp = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return duration;
	}

	/**
	 * 获取手机imsi号
	 * */
	public static String getImsi(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String _imsi = tm.getSubscriberId();
		if (_imsi != null && !_imsi.equals("")) {
			return _imsi;
		}
		return "未知";
	}

	/***
	 * 功能:获取当前位置
	 * @param context
	 */
	public static String getLonLat(Context context) {
		double latitude = 0.0;
		double longitude = 0.0;

		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				return longitude + ","+latitude;
			}
		} else {
			LocationListener locationListener = new LocationListener() {

				// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {

				}

				// Provider被enable时触发此函数，比如GPS被打开
				@Override
				public void onProviderEnabled(String provider) {

				}

				// Provider被disable时触发此函数，比如GPS被关闭
				@Override
				public void onProviderDisabled(String provider) {

				}

				// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {
						Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
					}
				}
			};
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude(); // 经度
				longitude = location.getLongitude(); // 纬度
				return longitude + ","+latitude;
			}
		}
		return null;
	}
	
	/**
	 * 获取SIM卡运营商
	 * 
	 * @param context
	 * @return
	 */ 
	public static String getOperators(Context context) { 
	    TelephonyManager tm = (TelephonyManager) context 
	            .getSystemService(Context.TELEPHONY_SERVICE); 
	    String operator = null; 
	    String IMSI = tm.getSubscriberId(); 
	    if (IMSI == null || IMSI.equals("")) { 
	        return operator; 
	    } 
	    if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) { 
	        operator = "中国移动"; 
	    } else if (IMSI.startsWith("46001")) { 
	        operator = "中国联通"; 
	    } else if (IMSI.startsWith("46003")) { 
	        operator = "中国电信"; 
	    } 
	    return operator; 
	}

}
