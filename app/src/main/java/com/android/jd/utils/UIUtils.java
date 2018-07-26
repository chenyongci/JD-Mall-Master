package com.android.jd.utils;

/**
 * Created by zpc.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;

import com.android.jd.app.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class UIUtils {

	public static Context getContext() {
		return BaseApplication.getInstance();
	}

	public static View inflate(int resId) {
		return LayoutInflater.from(getContext()).inflate(resId, null);
	}

	/**
	 * 获取资源
	 */
	public static Resources getResources() {
		return getContext().getResources();
	}

	/**
	 * 获取文字
	 */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}

	/**
	 * 获取文字数组
	 */
	public static String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	/**
	 * 获取dimen
	 */
	public static int getDimens(int resId) {
		return getResources().getDimensionPixelSize(resId);
	}

	/**
	 * 获取drawable
	 */
	public static Drawable getDrawable(int resId) {
		return getResources().getDrawable(resId);
	}

	/**
	 * 获取颜色
	 */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}

	/**
	 * 获取颜色选择器
	 */
	public static ColorStateList getColorStateList(int resId) {
		return getResources().getColorStateList(resId);
	}


	/**
	 * List<String> To String[]
	 */
	public static String[] ListStringToArray(List<String> ls) {
		return ls.toArray(new String[ls.size()]);
	}

	public static List<String> ArrayToListString(int resId) {
		String[] ss = getResources().getStringArray(resId);
		List<String> rls = new ArrayList<String>();
		for (int i = 0; i < ss.length; i++) {
			rls.add(ss[i]);
		}
		return rls;
	}

	/**
	 * 生成一个 透明的背景图片
	 *
	 * @return
	 */
	public static Drawable getDrawable() {
		ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
		bgdrawable.getPaint().setColor(getContext().getResources().getColor(android.R.color.transparent));
		return bgdrawable;
	}

}
