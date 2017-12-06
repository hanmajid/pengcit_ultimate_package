package com.hanmajid.pengcitultimatepackage.thinning;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Created by user on 12/6/2017.
 */

public class ZhangSuen implements Thinning {

    @Override
    public MyImage doThinning(MyImage src, Color targetColor) {
        MyImage imgOut = src.clone(src);



        return imgOut;
    }
}
