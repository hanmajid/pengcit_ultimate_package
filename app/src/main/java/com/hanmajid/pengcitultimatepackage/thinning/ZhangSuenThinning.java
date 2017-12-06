package com.hanmajid.pengcitultimatepackage.thinning;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

public class ZhangSuenThinning implements IThinning {

    @Override
    public MyImage doThinning(MyImage src, Color targetColor) {
        MyImage img = src.clone(src);

        int width = img.getWidth();
        int height = img.getHeight();

        int countStep1 = 1; // counter of marked pixels on step 1
        int countStep2 = 1; // counter of marked pixels on step 2
        while(countStep1 > 0 && countStep2 > 0) {
            // reset counters
            countStep1 = 0;
            countStep2 = 0;
            // iterate each pixel on image to be marked if the pixel
            // satisfied conditions on step 1
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    int countNeighbour = countNeighbour(x, y, width, height);
                    if(countNeighbour == 8) {
                        int color = img.getColor(x, y);
                        if(color == MyColor.BLACK) {
                            int BP1 = bP1(img, x, y);
                            if(2 <= BP1 && BP1 <= 6) {
                                int AP1 = aP1(img, x, y);
                                if(AP1 == 1) {
                                    if(existsWhiteP2P4P6(img, x, y)) {
                                        if(existsWhiteP4P6P8(img, x, y)) {
                                            img.setColor(MyColor.WHITE, x, y);
                                            countStep1++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Log.d("COUNT STEP 1", countStep1+"");
            // iterate each pixel on image to be marked if the pixel
            // satisfied conditions on step 2
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    int countNeighbour = countNeighbour(x, y, width, height);
                    if(countNeighbour == 8) {
                        int color = img.getColor(x, y);
                        if(color == MyColor.BLACK) {
                            int BP1 = bP1(img, x, y);
                            if(2 <= BP1 && BP1 <= 6) {
                                int AP1 = aP1(img, x, y);
                                if(AP1 == 1) {
                                    if(existsWhiteP2P4P8(img, x, y)) {
                                        if(existsWhiteP2P6P8(img, x, y)) {
                                            img.setColor(MyColor.WHITE, x, y);
                                            countStep2++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Log.d("COUNT STEP 2", countStep2+"");
        }

        return img;
    }

    private boolean existsWhiteP2P4P8(MyImage img, int x, int y) {
        // P2
        int color = img.getColor(x, y-1);
        if(color == MyColor.WHITE)
            return true;
        // P4
        color = img.getColor(x+1, y);
        if(color == MyColor.WHITE)
            return true;
        // P8
        color = img.getColor(x-1, y);
        if(color == MyColor.WHITE)
            return true;
        return false;
    }

    private boolean existsWhiteP2P6P8(MyImage img, int x, int y) {
        // P2
        int color = img.getColor(x, y-1);
        if(color == MyColor.WHITE)
            return true;
        // P6
        color = img.getColor(x, y+1);
        if(color == MyColor.WHITE)
            return true;
        // P8
        color = img.getColor(x-1, y);
        if(color == MyColor.WHITE)
            return true;
        return false;
    }

    private boolean existsWhiteP4P6P8(MyImage img, int x, int y) {
        // P4
        int color = img.getColor(x+1, y);
        if(color == MyColor.WHITE)
            return true;
        // P6
        color = img.getColor(x, y+1);
        if(color == MyColor.WHITE)
            return true;
        // P8
        color = img.getColor(x-1, y);
        if(color == MyColor.WHITE)
            return true;
        return false;
    }

    private boolean existsWhiteP2P4P6(MyImage img, int x, int y) {
        // P2
        int color = img.getColor(x, y-1);
        if(color == MyColor.WHITE)
            return true;
        // P4
        color = img.getColor(x+1, y);
        if(color == MyColor.WHITE)
            return true;
        // P6
        color = img.getColor(x, y+1);
        if(color == MyColor.WHITE)
            return true;
        return false;
    }

    private int bP1(MyImage img, int x, int y) {
        int countBlack = 0;
        for(int j = -1; j <= 1; j++) {
            for(int i = -1; i <= 1; i++) {
                if(i != 0 || j != 0) {
                    if (img.getColor(i, j) == MyColor.BLACK) {
                        countBlack++;
                    }
                }
            }
        }
        return countBlack;
    }

    private int aP1(MyImage img, int x, int y) {
        int countTransition = 0;
        int prevColor = MyColor.BLACK;
        // P2
        int color = img.getColor(x, y-1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P3
        prevColor = color;
        color = img.getColor(x+1, y-1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P4
        prevColor = color;
        color = img.getColor(x+1, y);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P5
        prevColor = color;
        color = img.getColor(x+1, y+1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P6
        prevColor = color;
        color = img.getColor(x, y+1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P7
        prevColor = color;
        color = img.getColor(x-1, y+1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P8
        prevColor = color;
        color = img.getColor(x-1, y);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P9
        prevColor = color;
        color = img.getColor(x-1, y-1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        // P2
        prevColor = color;
        color = img.getColor(x, y-1);
        if(prevColor == MyColor.WHITE && color == MyColor.BLACK)
            countTransition++;
        return countTransition;
    }

    private int countNeighbour(int x, int y, int width, int height) {
        if(x == 0 || x == width-1 || y == 0 || y == height-1)
            return -1;
        return 8;
    }
}
