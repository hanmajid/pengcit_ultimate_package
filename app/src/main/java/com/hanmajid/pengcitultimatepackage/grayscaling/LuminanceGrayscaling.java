package com.hanmajid.pengcitultimatepackage.grayscaling;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class LuminanceGrayscaling implements IGrayscaling {

    @Override
    public MyImage doGrayscaling(MyImage src) {
        MyImage img = src.clone();

        int width = img.getWidth();
        int height = img.getHeight();
        int map;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                map = (int) ((0.3 * img.getRed(x, y)) + (0.59 * img.getGreen(x, y)) + (0.11 * img.getBlue(x, y)));
                img.setColor(Color.rgb(map, map, map), x, y);
            }
        }

        return img;
    }
}
