package com.hanmajid.pengcitultimatepackage.facerecognition;

import android.graphics.Color;

import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/11/2017.
 */

public class ColorModelFaceRecognition implements IFaceRecognition {

    int[] redArray;
    int[] greenArray;
    int[] blueArray;

    float[] hArray;
    float[] sArray;
    float[] vArray;

    int[] yArray;
    int[] crArray;
    int[] cbArray;

    @Override
    public MyImage doFaceRecognition(MyImage src) {
        MyImage img = src.clone();
        int width = img.getWidth();
        int height = img.getHeight();
        this.redArray = new int[width*height];
        this.greenArray= new int[width*height];
        this.blueArray = new int[width*height];
        this.hArray = new float[width*height];
        this.sArray = new float[width*height];
        this.vArray = new float[width*height];
        this.yArray = new int[width*height];
        this.crArray = new int[width*height];
        this.cbArray = new int[width*height];

        int pixel, r, g, b;

        int count = 0;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                float[] hsv = new float[3];
                pixel = img.getColor(x, y);
                r = Color.red(pixel);
                g = Color.green(pixel);
                b = Color.blue(pixel);
                // RGB
                this.redArray[count] = r;
                this.greenArray[count] = g;
                this.blueArray[count] = b;
                // HSV
                Color.RGBToHSV(r, g, b, hsv);
                this.hArray[count] = hsv[0];
                this.sArray[count] = hsv[1];
                this.vArray[count] = hsv[2];
                // YCrCb
                this.yArray[count] = (int)(0.299*r+0.587*g+0.114*b);
                this.cbArray[count] =(int)(128-0.169*r-0.331*g+0.500*b);
                this.crArray[count++] =(int)(128+0.500*r-0.419*g-0.081*b);
            }
        }

        int ya, cr, cb;
        float h, s, v;
        count = 0;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                r = redArray[count];
                g = greenArray[count];
                b = blueArray[count];
                h = hArray[count];
                s = sArray[count];
                //v = vArray[count];
                ya = yArray[count];
                cr = crArray[count];
                cb = cbArray[count];

                if(0 <= h && 50 >= h && 0.23 <= s && s <= 0.68 && r > 95 && g > 40 && b > 20 && r > g && r > b && Math.abs(r-g) > 15)
                    img.setColor(MyColor.BLACK, x, y);
                else if(r > 95 && g > 40 && b > 20 && r > g && r > b && Math.abs(r-g) > 15 && cr > 135 && cb > 85 && ya > 80 && cr <= (1.5862*cb)+20 && cr >= (0.3448*cb)+76.2069 && cr >= (-4.5652*cb)+234.5652 && cr <= (-1.15*cb)+301.75 && cr <= (-2.2857*cb)+432.85)
                    img.setColor(MyColor.BLACK, x, y);
                else
                    img.setColor(MyColor.WHITE, x, y);
                count++;
            }
        }

        return img;
    }

    @Override
    public MyImage identifyRotatedObject(MyImage src1, MyImage rotated) {
        return null;
    }
}
