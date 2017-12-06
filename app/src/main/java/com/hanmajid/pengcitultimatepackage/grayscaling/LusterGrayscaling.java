package com.hanmajid.pengcitultimatepackage.grayscaling;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class LusterGrayscaling implements IGrayscaling {
    @Override
    public MyImage doGrayscaling(MyImage src) {
        MyImage img = src.clone(src);

        int width = img.getWidth();
        int height = img.getHeight();
        int R, G, B;
        int map;
        int max, min;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                R = img.getRed(x, y);
                G = img.getGreen(x, y);
                B = img.getBlue(x, y);
                max = R;
                min = R;
                if(max < G)
                    max = G;
                if(max < B)
                    max = B;
                if(min > G)
                    min = G;
                if(min > B)
                    min = B;
                map = (max + min) / 2;
                img.setColor(Color.rgb(map, map, map), x, y);
            }
        }

        return img;
    }
}
