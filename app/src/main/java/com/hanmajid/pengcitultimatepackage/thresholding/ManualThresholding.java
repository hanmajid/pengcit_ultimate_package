package com.hanmajid.pengcitultimatepackage.thresholding;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;
import com.hanmajid.pengcitultimatepackage.thresholding.IThresholding;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class ManualThresholding implements IThresholding {

    private int threshold;

    ManualThresholding(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public MyImage doThresholding(MyImage src) {
        MyImage img = src.clone();

        int width = img.getWidth();
        int height = img.getHeight();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(img.getRed(x, y) < threshold) {
                    img.setColor(MyColor.BLACK, x, y);
                }
                else {
                    img.setColor(MyColor.WHITE, x, y);
                }
            }
        }

        return img;
    }
}
