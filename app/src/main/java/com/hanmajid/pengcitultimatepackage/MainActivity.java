package com.hanmajid.pengcitultimatepackage;

/**
 * Project: PengcitUltimatePackage
 * by hanmajid (email: han.majid2004@gmail.com)
 *
 * Created on 12/6/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.hanmajid.pengcitultimatepackage.convolution.BlurringConvolution;
import com.hanmajid.pengcitultimatepackage.convolution.SharpeningConvolution;
import com.hanmajid.pengcitultimatepackage.edgedetection.SobelEdgeDetection;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    ImageView imgViewOriginal;
    ImageView imgViewProcessed;

    Button btnGrayscaling;
    Button btnThresholding;
    Button btnBorder;
    Button btnEdge;
    Button btnHistogram;
    Button btnThinning;
    Button btnFace;
    Button btnConvolution;
    Button btnSelectImage;
    Button btnResetImage;

    TextView text1;

    private boolean needReset;
    private Bitmap bitmapOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();

        needReset = false;
        // TODO
//        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
//
//        LineChart chart = (LineChart) findViewById(R.id.chart);
//        MyHistogram myHistogram = new MyHistogram();
//        Distribution distribution = myHistogram.countDistribution(imgOriginal);
////        Distribution distribution = myHistogram.countCummulativeDistribution(imgOriginal);
//        myHistogram.drawColorHistogram(chart, distribution);
    }

    private void setupView() {
        imgViewOriginal = (ImageView) findViewById(R.id.img_original);
        imgViewProcessed = (ImageView) findViewById(R.id.img_processed);

        btnGrayscaling = (Button) findViewById(R.id.btn_grayscaling);
        btnGrayscaling.setOnClickListener(this);
        btnThresholding = (Button) findViewById(R.id.btn_thresholding);
        btnThresholding.setOnClickListener(this);
        btnBorder = (Button) findViewById(R.id.btn_border);
        btnBorder.setOnClickListener(this);
        btnEdge = (Button) findViewById(R.id.btn_edge);
        btnEdge.setOnClickListener(this);
        btnHistogram = (Button) findViewById(R.id.btn_histogram);
        btnHistogram.setOnClickListener(this);
        btnThinning = (Button) findViewById(R.id.btn_thinning);
        btnThinning.setOnClickListener(this);
        btnFace = (Button) findViewById(R.id.btn_face);
        btnFace.setOnClickListener(this);
        btnConvolution = (Button) findViewById(R.id.btn_convolution);
        btnConvolution.setOnClickListener(this);

        btnSelectImage = (Button)findViewById(R.id.select_image);
        btnSelectImage.setOnClickListener(this);

        btnResetImage = (Button) findViewById(R.id.reset_image);
        btnResetImage.setOnClickListener(this);

        text1 = (TextView) findViewById(R.id.text_1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_grayscaling:
                grayscaling();
                break;
            case R.id.btn_thresholding:
                thresholding();
                break;
            case R.id.btn_border:
                border();
                break;
            case R.id.btn_edge:
                edge();
                break;
            case R.id.btn_histogram:
                histogram();
                break;
            case R.id.btn_thinning:
                thinning();
                break;
            case R.id.btn_face:
                face();
                break;
            case R.id.btn_convolution:
                convolution();
                break;
            case R.id.select_image:
                chooseImage();
                break;
            case R.id.reset_image:
                resetImage();
                break;
            default:
                break;
        }
    }

    private void thinning() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
        OtsuThresholding otsuThresholding = new OtsuThresholding();
        imgProcessed = otsuThresholding.doThresholding(imgProcessed);
        ZhangSuenThinning zhangSuenThinning = new ZhangSuenThinning();
        imgProcessed = zhangSuenThinning.doThinning(imgProcessed);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void convolution() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
        SharpeningConvolution sharpeningConvolution = new SharpeningConvolution();
        imgProcessed = sharpeningConvolution.doConvolution(imgProcessed);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void face() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        ColorModelFaceRecognition colorModelFaceRecognition = new ColorModelFaceRecognition();
        imgProcessed = colorModelFaceRecognition.doFaceRecognition(imgOriginal);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void histogram() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        // main process
        LineChart chart = (LineChart) findViewById(R.id.chart);
        MyHistogram myHistogram = new MyHistogram();
        Distribution distribution = myHistogram.countDistribution(imgOriginal);
        myHistogram.drawColorHistogram(chart, distribution);
        needReset = true;
    }

    private void edge() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
        SobelEdgeDetection sobelEdgeDetection = new SobelEdgeDetection();
        imgProcessed = sobelEdgeDetection.doEdgeDetection(imgProcessed);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void border() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
        OtsuThresholding otsuThresholding = new OtsuThresholding();
        imgProcessed = otsuThresholding.doThresholding(imgProcessed);
        ChainCodeBorderTracing chainCodeBorderTracing = new ChainCodeBorderTracing(20, 1);
        imgProcessed = chainCodeBorderTracing.doBorderTracing(imgProcessed);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void thresholding() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
        OtsuThresholding otsuThresholding = new OtsuThresholding();
        imgProcessed = otsuThresholding.doThresholding(imgProcessed);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void grayscaling() {
        if(needReset) {
            resetImage();
        }
        // bitmap to MyImage
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed;
        // main process
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);
        // MyImage to bitmap
        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
        imgViewProcessed.setImageBitmap(bitmapProcessed);
        needReset = true;
    }

    private void resetImage() {
        imgViewOriginal.setImageBitmap(bitmapOriginal);
        imgViewProcessed.setImageBitmap(null);
        text1.setText("-");
        needReset = false;
    }

    private void chooseImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_IMAGE) {

            if (resultCode == RESULT_OK) {
                if (intent != null) {
                    // Get the URI of the selected file
                    final Uri uri = intent.getData();
                    useImage(uri);
                }
            }
            super.onActivityResult(requestCode, resultCode, intent);

        }
    }

    void useImage(Uri uri)
    {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // resize image
        double scale = 0.05208333333;
        Bitmap resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*scale), (int)(bitmap.getHeight()*scale), true);
        Log.d("RESIZED SIZE", ""+resized.getWidth() + "x"+resized.getHeight());
        if (bitmap.getWidth() > 200)
            bitmapOriginal = resized;
        else
            bitmapOriginal = bitmap;
        imgViewOriginal.setImageBitmap(bitmapOriginal);
    }

    private void process() {

        // convert to MyImage
//        Bitmap bitmapOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.majid);
        MyImage imgOriginal = BitmapToMyImage(bitmapOriginal);
        MyImage imgProcessed, imgProcessed2;

        // process
//        GleamGrayscaling gleamGrayscaling = new GleamGrayscaling();
//        imgProcessed = gleamGrayscaling.doGrayscaling(imgOriginal);
        IntensityGrayscaling intensityGrayscaling = new IntensityGrayscaling();
        imgProcessed = intensityGrayscaling.doGrayscaling(imgOriginal);

        Bitmap bitmapProcessed = MyImageToBitmap(imgProcessed);
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

//        ColorModelFaceRecognition colorModelFaceRecognition = new ColorModelFaceRecognition();
//        imgProcessed = colorModelFaceRecognition.doFaceRecognition(imgOriginal);

//        SharpeningConvolution sharpeningConvolution = new SharpeningConvolution();
//        imgProcessed = sharpeningConvolution.doConvolution(imgProcessed);
//        BlurringConvolution blurringConvolution = new BlurringConvolution();
//        imgProcessed = blurringConvolution.doConvolution(imgProcessed);

        SobelEdgeDetection sobelEdgeDetection = new SobelEdgeDetection();
        imgProcessed = sobelEdgeDetection.doEdgeDetection(imgProcessed);

        // convert to bitmap
        Bitmap bitmapProcessed2 = MyImageToBitmap(imgProcessed);
        imgViewOriginal.setImageBitmap(bitmapProcessed);
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
