package com.android.sframe.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lin on 2017/9/7.
 * 功能：压缩图片
 */

public class PicUtils {
    /**
     * @param filePath   原图路径
     * @param targetPath 压缩后的图片临时路径
     * @param quality    压缩率
     * @return
     */
    public static File compressImage(String filePath, String targetPath, int quality) {
        // 获取一定尺寸的图片
        Bitmap bitmap = getSmallBitmap(filePath);
        // 获取相片拍摄角度
        int degree = readPicDegree(filePath);
        if (degree != 0) {
            bitmap = rotateBitmap(bitmap, degree);
        }
        File outputFile = new File(targetPath);
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        } else {
            outputFile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    public static File compressImage(String filePath, int quality) {
        String targetPath = Environment.getExternalStorageDirectory() + File.separator +
                "compressPic.jpg";
        return compressImage(filePath, targetPath, quality);
    }

    public static String uri4CompressImage(String filePath, String targetPath, int quality) {
        return compressImage(filePath, targetPath, quality).getPath();
    }

    public static String uri4CompressImage(String filePath, int quality) {
        String targetPath = Environment.getExternalStorageDirectory() + File.separator +
                "compressPic.jpg";
        return compressImage(filePath, targetPath, quality).getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只解析图片边缘，获取宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比例
        options.inSampleSize = calcInSampleSize(options, 480, 800);
        // 完整解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calcInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取图片角度
     *
     * @param path
     * @return
     */
    public static int readPicDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: {
                    degree = 90;
                    break;
                }
                case ExifInterface.ORIENTATION_ROTATE_180: {
                    degree = 180;
                    break;
                }
                case ExifInterface.ORIENTATION_ROTATE_270: {
                    degree = 270;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degrees
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degrees);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m,
                    true);
        }
        return bitmap;
    }
}
