package com.hanmajid.pengcitultimatepackage.thinning;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Created by user on 12/6/2017.
 */

public interface Thinning {
    public MyImage doThinning(MyImage src, Color targetColor);
}
