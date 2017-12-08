package com.hanmajid.pengcitultimatepackage.grayscaling;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class LightnessGrayscaling implements IGrayscaling {

    @Override
    public MyImage doGrayscaling(MyImage src) {
        MyImage img = src.clone();

        int width = img.getWidth();
        int height = img.getHeight();
        int map;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                map = (int)((116 * fLightness(img.getRed(x, y), img.getGreen(x, y), img.getBlue(x, y)) - 16) / 100);
                img.setColor(Color.rgb(map, map, map), x, y);
            }
        }

        return img;
    }

    private double fLightness(int r, int g, int b) {
        double y = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
        double result;
        if(y > Math.pow(((double)6/29), 3)) {
            result = Math.pow(y, (double) 1 / 3);
        }
        else {
            result = (((double)1 / 3) * Math.pow((double) 29/6, 2) * y) + ((double) 4 / 29);
        }
        return result;
    }
}
