package com.android.jd.utils;

import android.widget.ImageView;

import com.android.jd.R;
import com.bumptech.glide.Glide;

import static com.android.jd.app.BaseApplication.getInstance;

/**
 * @Author android
 * @date 2018/6/6
 * @return
 */

public class ImageLoadUtils {


    public static void showImage(String url, ImageView image) {
        Glide.with(getInstance()).load(url).thumbnail(0.1f).into(image);
    }


    public static void showCirImage(String url, final ImageView image) {
        if (url == null || url.isEmpty() || "".equals(url)) {
            image.setImageResource(R.mipmap.error_img);
            return;
        }
        Glide.with(getInstance())
                .load( url)
                .into(image);
    }

    public static void showLoginCriImage(String url, final ImageView image) {
        if (url == null || url.isEmpty() || "".equals(url)) {
            image.setImageResource(R.mipmap.error_img);
            return;
        }
        Glide.with(getInstance())
                .load( url)
                .into(image);
    }

}
