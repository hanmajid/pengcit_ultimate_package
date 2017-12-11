package com.hanmajid.pengcitultimatepackage.edgedetection;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/8/2017.
 */

public class SobelEdgeDetection implements IEdgeDetection {

    int[][] kernelX;
    int[][] kernelY;

    public SobelEdgeDetection() {
        this.kernelX = new int[][] {
                {1, 0, -1},
                {2, 0, -2},
                {1, 0, -1}
        };
        this.kernelY = new int[][] {
                {1, 2, 1},
                {0, 0, 0},
                {-1, -2, -1}
        };
    }

    @Override
    public MyImage doEdgeDetection(MyImage src) {
        MyImage img = src.clone();
        int width = img.getWidth();
        int height = img.getHeight();

        int accX, accY, acc;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(x == 0 || x == width-1 || y == 0 || y == height-1) {
                    img.setColor(MyColor.BLACK, x, y);
                }
                else {
                    accX = 0;
                    accY = 0;
                    for(int j = 0; j < kernelX.length; j++) {
                        for(int i = 0; i < kernelX.length; i++) {
                            accX += src.getRed(x+i-1, y+j-1) * kernelX[j][i];
                        }
                    }
                    for(int j = 0; j < kernelY.length; j++) {
                        for(int i = 0; i < kernelY.length; i++) {
                            accY += src.getRed(x+i-1, y+j-1) * kernelY[j][i];
                        }
                    }
                    acc = (int) Math.sqrt(Math.pow(accX, 2) + Math.pow(accY, 2));
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
