package com.hanmajid.pengcitultimatepackage.grayscaling;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class ValueGCGrayscaling implements IGrayscaling {

    private final static double GAMMA = 2.2;

    @Override
    public MyImage doGrayscaling(MyImage src) {
        MyImage img = src.clone();

        int width = img.getWidth();
        int height = img.getHeight();
        double dR, dG, dB;
        int map;
        double dMax;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                dR = 255 * Math.pow(((double) img.getRed(x, y)/255), (1/GAMMA));
                dG = 255 * Math.pow(((double) img.getGreen(x, y)/255), (1/GAMMA));
                dB = 255 * Math.pow(((double) img.getBlue(x, y)/255), (1/GAMMA));
                dMax = dR;
                if(dMax < dG)
                    dMax = dG;
                if(dMax < dB)
                    dMax = dB;
                map = (int) dMax;
                img.setColor(Color.rgb(map, map, map), x, y);
            }
        }

        return img;
    }
}
