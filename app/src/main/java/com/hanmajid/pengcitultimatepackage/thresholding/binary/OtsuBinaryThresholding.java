package com.hanmajid.pengcitultimatepackage.thresholding.binary;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;
import com.hanmajid.pengcitultimatepackage.thresholding.IThresholding;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class OtsuBinaryThresholding implements IThresholding {

    public OtsuBinaryThresholding() {
    }

    @Override
    public MyImage doThresholding(MyImage src) {
        MyImage img = src.clone(src);

        int width = img.getWidth();
        int height = img.getHeight();
        int threshold = getOtsuThreshold(img);

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

    private static int getOtsuThreshold(MyImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        int[] histogram = new int[256];
        for(int i=0; i<256; i++) {
            histogram[i] = 0;
        }

        // Calculate histogram
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                histogram[img.getRed(x, y)]++;
            }
        }

        // Total number of pixels
        int total = width * height;

        float sum = 0;
        for (int t=0 ; t<256 ; t++) sum += t * histogram[t];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t=0 ; t<256 ; t++) {
            wB += histogram[t];               // Weight Background
            if (wB == 0) continue;

            wF = total - wB;                 // Weight Foreground
            if (wF == 0) break;

            sumB += (float) (t * histogram[t]);

            float mB = sumB / wB;            // Mean Background
            float mF = (sum - sumB) / wF;    // Mean Foreground

            // Calculate Between Class Variance
            float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }
}
