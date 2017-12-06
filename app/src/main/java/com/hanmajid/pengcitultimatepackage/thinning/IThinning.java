package com.hanmajid.pengcitultimatepackage.thinning;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */


public interface IThinning {
    public MyImage doThinning(MyImage src, Color targetColor);
}
