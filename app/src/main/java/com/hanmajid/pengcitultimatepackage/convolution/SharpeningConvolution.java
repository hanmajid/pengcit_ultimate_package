package com.hanmajid.pengcitultimatepackage.convolution;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/12/2017.
 */

public class SharpeningConvolution implements IConvolution {

    int[][] kernel;

    public SharpeningConvolution() {
        this.kernel = new int[][] {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
    }

    @Override
    public MyImage doConvolution(MyImage src) {
        MyImage img = src.clone();
        int width = img.getWidth();
        int height = img.getHeight();

        int acc;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(x == 0 || x == width-1 || y == 0 || y == height-1) {
                    img.setColor(MyColor.BLACK, x, y);
                }
                else {
                    acc = 0;
                    for(int j = 0; j < kernel.length; j++) {
                        for(int i = 0; i < kernel.length; i++) {
                            acc += src.getRed(x+i-1, y+j-1) * kernel[j][i];
                        }
                    }
                    if(acc < 0)
                        img.setColor(MyColor.BLACK, x, y);
                    else
                        img.setColor(Color.rgb(acc, acc, acc), x, y);
                }
            }
        }
        return img;
    }
}
