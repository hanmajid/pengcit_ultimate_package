package com.hanmajid.pengcitultimatepackage.facerecognition;

import com.hanmajid.pengcitultimatepackage.bordertracing.ChainCodeBorderTracing;
import com.hanmajid.pengcitultimatepackage.grayscaling.IntensityGrayscaling;
import com.hanmajid.pengcitultimatepackage.shared.ChainCode;
import com.hanmajid.pengcitultimatepackage.shared.MyColor;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;
import com.hanmajid.pengcitultimatepackage.thresholding.OtsuThresholding;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/8/2017.
 */

public class GoldenRatioFaceRecognition implements IFaceRecognition {

    private double mouthWidth;
    private double eyeDistance;

    @Override
    public MyImage doFaceRecognition(MyImage src) {
        MyImage img = src.clone();
        // grayscaling
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        img = intensityGrayscaling.doGrayscaling(img);
        // thresholding
        OtsuThresholding otsuThresholding = new OtsuThresholding();
        img = otsuThresholding.doThresholding(img);
        // border tracing
        ChainCodeBorderTracing chainCodeBorderTracing = new ChainCodeBorderTracing(50, 1);
        img = chainCodeBorderTracing.doBorderTracing(img);
        // find chain code for eyes
        ChainCode chainCode[] = chainCodeBorderTracing.getChainCode();
        int width = img.getWidth();
        int height = img.getHeight();

        // left eye
        int leftEye = -1;
        double distanceLeftEye = 9999;
        int leftEyeX = width / 3;
        int leftEyeY = height / 2;
        // right eye
        int rightEye = -1;
        double distanceRightEye = 9999;
        int rightEyeX = (width / 3) * 2;
        int rightEyeY = height / 2;
        // left mouth
        int leftMouth = -1;
        double distanceLeftMouth = 9999;
        int leftMouthX = width / 3;
        int leftMouthY = (height / 4)*3;
        // right mouth
        int rightMouth = -1;
        double distanceRightMouth = 9999;
        int rightMouthX = (width / 3) * 2;
        int rightMouthY = (height / 4)*3;

        ChainCode cc;
        double distance;
        for(int i = 0; i < chainCodeBorderTracing.getCountChainCode(); i++) {
            cc = chainCode[i];
            if(cc == null) {
                continue;
            }
            // left eye
            distance = getDistance(cc.getCentroidX(), cc.getCentroidY(), leftEyeX, leftEyeY);
            if(distance < distanceLeftEye) {
                distanceLeftEye = distance;
                leftEye = i;
            }
            // right eye
            distance = getDistance(cc.getCentroidX(), cc.getCentroidY(), rightEyeX, rightEyeY);
            if(distance < distanceRightEye) {
                distanceRightEye = distance;
                rightEye = i;
            }
            // left mouth
            distance = getDistance(cc.getCentroidX(), cc.getCentroidY(), leftMouthX, leftMouthY);
            if(distance < distanceLeftMouth) {
                distanceLeftMouth = distance;
                leftMouth = i;
            }
            // right mouth
            distance = getDistance(cc.getCentroidX(), cc.getCentroidY(), rightMouthX, rightMouthY);
            if(distance < distanceRightMouth) {
                distanceRightMouth = distance;
                rightMouth = i;
            }
        }
        // draw eye lines and mouth lines
        // eye
        img = drawLine(img, chainCode[leftEye].getCentroidX(), chainCode[leftEye].getCentroidY(), chainCode[rightEye].getCentroidX(), chainCode[rightEye].getCentroidY());
        this.eyeDistance = getDistance(chainCode[leftEye].getCentroidX(), chainCode[leftEye].getCentroidY(), chainCode[rightEye].getCentroidX(), chainCode[rightEye].getCentroidY());

        // mouth
        img = drawLine(img, chainCode[leftMouth].getMinX(), chainCode[leftMouth].getCentroidY(), chainCode[rightMouth].getMaxX(), chainCode[rightMouth].getCentroidY());
        this.mouthWidth = getDistance(chainCode[leftMouth].getMinX(), chainCode[leftMouth].getCentroidY(), chainCode[rightMouth].getMaxX(), chainCode[rightMouth].getCentroidY());

        return img;
    }

    private MyImage drawLine(MyImage src, int x0, int y0, int x1, int y1) {
        MyImage img = src.clone();
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            img.setColor(MyColor.GREEN, x0, y0);

            if (x0 == x1 && y0 == y1)
                break;

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x0 = x0 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y0 = y0 + sy;
            }
        }

        return img;
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    @Override
    public MyImage identifyRotatedObject(MyImage src1, MyImage rotated) {
        return null;
    }

    public double getMouthWidth() {
        return mouthWidth;
    }

    public void setMouthWidth(double mouthWidth) {
        this.mouthWidth = mouthWidth;
    }

    public double getEyeDistance() {
        return eyeDistance;
    }

    public void setEyeDistance(double eyeDistance) {
        this.eyeDistance = eyeDistance;
    }
}
