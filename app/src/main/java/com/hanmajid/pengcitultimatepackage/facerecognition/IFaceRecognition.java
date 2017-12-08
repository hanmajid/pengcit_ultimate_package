package com.hanmajid.pengcitultimatepackage.facerecognition;

import com.hanmajid.pengcitultimatepackage.shared.MyImage;

/**
 * Project: pengcit_ultimate_package
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/8/2017.
 */

public interface IFaceRecognition {
    public MyImage doFaceRecognition(MyImage src);

    public MyImage identifyRotatedObject(MyImage src1, MyImage rotated);
}
