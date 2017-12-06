package com.hanmajid.pengcitultimatepackage.grayscaling;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class LumaGrayscaling implements IGrayscaling {

    private final static double GAMMA = 2.2;

    @Override
    public MyImage doGrayscaling(MyImage src) {
        MyImage img = src.clone(src);

        int width = img.getWidth();
        int height = img.getHeight();
        double dR, dG, dB;
        int map;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                dR = 255 * Math.pow(((double) img.getRed(x, y)/255), (1/GAMMA));
                dG = 255 * Math.pow(((double) img.getGreen(x, y)/255), (1/GAMMA));
                dB = 255 * Math.pow(((double) img.getBlue(x, y)/255), (1/GAMMA));
                map = (int)((0.2126 * dR) + (0.7152 * dG) + (0.0722 * dB));
                img.setColor(Color.rgb(map, map, map), x, y);
            }
        }

        return img;
    }
}
