package com.hanmajid.pengcitultimatepackage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hanmajid.pengcitultimatepackage.bordertracing.ChainCodeBorderTracing;
import com.hanmajid.pengcitultimatepackage.facerecognition.ColorModelFaceRecognition;
import com.hanmajid.pengcitultimatepackage.facerecognition.GoldenRatioFaceRecognition;
import com.hanmajid.pengcitultimatepackage.grayscaling.GleamGrayscaling;
import com.hanmajid.pengcitultimatepackage.grayscaling.IntensityGrayscaling;
import com.hanmajid.pengcitultimatepackage.grayscaling.ValueGrayscaling;
import com.hanmajid.pengcitultimatepackage.histogram.MyHistogram;
import com.hanmajid.pengcitultimatepackage.shared.Distribution;
import com.hanmajid.pengcitultimatepackage.shared.MyImage;
import com.hanmajid.pengcitultimatepackage.thinning.ZhangSuenThinning;
import com.hanmajid.pengcitultimatepackage.thresholding.ManualThresholding;
import com.hanmajid.pengcitultimatepackage.thresholding.OtsuThresholding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgViewOriginal;
    ImageView imgViewProcessed;

    Button btnProcess;

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();

        // TODO
        Bitmap bitmapOriginal =((BitmapDrawable)imgViewOriginal.getDrawable()).getBitmap();
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);

        LineChart chart = (LineChart) findViewById(R.id.chart);
        MyHistogram myHistogram = new MyHistogram();
        Distribution distribution = myHistogram.countDistribution(imgOriginal);
//        Distribution distribution = myHistogram.countCummulativeDistribution(imgOriginal);
        myHistogram.drawColorHistogram(chart, distribution);
    }

    private void setupView() {
        imgViewOriginal = (ImageView) findViewById(R.id.img_original);
        imgViewProcessed = (ImageView) findViewById(R.id.img_processed);

        btnProcess = (Button) findViewById(R.id.btn_process);
        btnProcess.setOnClickListener(this);

        text1 = (TextView) findViewById(R.id.text_1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_process:
                process();
                break;
            default:
                break;
        }
    }

    private void process() {
        // convert to MyImage
        Bitmap bitmapOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.group);
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed, imgProcessed2;

        // process
//        GleamGrayscaling gleamGrayscaling = new GleamGrayscaling();
//        imgProcessed = gleamGrayscaling.doGrayscaling(imgOriginal);
//        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
//        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
//        ValueGrayscaling valueGrayscaling = new ValueGrayscaling();
//        imgProcessed = valueGrayscaling.doGrayscaling(imgOriginal);
//        MyHistogram myHistogram = new MyHistogram();
//        imgProcessed2 = myHistogram.equalizeHistogram(imgProcessed);

//        OtsuThresholding otsuThresholding = new OtsuThresholding();
//        imgProcessed = otsuThresholding.doThresholding(imgProcessed);
//        ManualThresholding manualThresholding = new ManualThresholding(100);
//        imgProcessed = manualThresholding.doThresholding(imgProcessed);
//        ZhangSuenThinning zhangSuenThinning = new ZhangSuenThinning();
//        imgProcessed = zhangSuenThinning.doThinning(imgOriginal);

        // border tracing
//        ChainCodeBorderTracing chainCodeBorderTracing = new ChainCodeBorderTracing(10, 100);
//        imgProcessed = chainCodeBorderTracing.doBorderTracing(imgProcessed);

//        GoldenRatioFaceRecognition goldenRatioFaceRecognition = new GoldenRatioFaceRecognition();
//        imgProcessed = goldenRatioFaceRecognition.doFaceRecognition(imgOriginal);

//        text1.setText("Ratio d/m: "+goldenRatioFaceRecognition.getEyeDistance()/goldenRatioFaceRecognition.getMouthWidth());

        ColorModelFaceRecognition colorModelFaceRecognition = new ColorModelFaceRecognition();
        imgProcessed = colorModelFaceRecognition.doFaceRecognition(imgOriginal);

        // convert to bitmap
//        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        Bitmap bitmapProcessed2 = MyImageToBitmap(imgProcessed);
//        imgViewOriginal.setImageBitmap(bitmapProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed2);
    }

    private Bitmap MyImageToBitmap(MyImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        Bitmap bitmap = img.getBitmap().copy(img.getBitmap().getConfig(), true);
        int color;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                color = Color.rgb(img.getRed(x, y), img.getGreen(x, y), img.getBlue(x, y));
                bitmap.setPixel(x, y, color);
            }
        }

        return bitmap;
    }

    private MyImage BitmapToMyImage(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[][] red = new int[height][width];
        int[][] green = new int[height][width];
        int[][] blue = new int[height][width];
        int R, G, B;
        int pixel;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                red[y][x] = R;
                green[y][x] = G;
                blue[y][x] = B;
            }
        }
        MyImage img = new MyImage(red, green, blue, src);

        return img;
    }
}
