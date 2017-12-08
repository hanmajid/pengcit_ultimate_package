package com.hanmajid.pengcitultimatepackage.bordertracing;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class ChainCodeBorderTracing implements IBorderTracing {

    private int[][] chainCode;
    private int maxObject;

    ChainCodeBorderTracing(int maxObject) {
        this.maxObject = maxObject;
    }

    @Override
    public MyImage doBorderTracing(MyImage src) {
        MyImage img = src.clone();

        int width = img.getWidth();
        int height = img.getHeight();

        int countObject = 0;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(img.getColor(x, y) == MyColor.BLACK && countObject < maxObject ) {

                }
            }
        }

        return img;
    }

    public int[][] getChainCode() {
        return chainCode;
    }

    public void setChainCode(int[][] chainCode) {
        this.chainCode = chainCode;
    }
}
