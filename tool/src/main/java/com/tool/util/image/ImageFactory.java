package com.tool.util.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * JNI图片压缩工具类
 *
 * @author XiaoSai
 * @version V1.0.0
 */
public class ImageFactory {

    private static int DEFAULT_QUALITY = 95;
    private static String TAG = "ImageFactory";

    /**
     * 开启一个线程进行压缩
     *
     * @param curFilePath
     */
    public static void compressImageOnThread(final String curFilePath, final String tempPath, final ImageCompressCallBack listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String localPath = Environment.getExternalStorageDirectory().getPath() + "/lifeguard/tmep/" + tempPath + ".jpg";
                compressImageToFile2(curFilePath, localPath, listener);
            }
        }).start();
    }

    /**
     * 开启一个线程进行压缩
     *
     * @param curFilePath
     */
    public static void compressImageOnThread(final String curFilePath, final ImageCompressCallBack listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String localPath = Environment.getExternalStorageDirectory().getPath() + "/lifeshs/tmep/img_tmp.jpg";
                compressImageToFile2(curFilePath, localPath, listener);
            }
        }).start();
    }

    /**
     * 将图片在线程中压缩并转成Base64字符串
     * dengfeng add at 20170304
     *
     * @param filePath 文件路径
     * @return base64 string
     */
    public static void compressImageToBase64(final String filePath, final ImageCompressToBase64CallBack listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String localPath = Environment.getExternalStorageDirectory().getPath() + "/lifeshs/tmep/avator_tmp.jpg";
                compressImageToFile2(filePath, localPath, new ImageCompressCallBack() {

                    @Override
                    public void compressImageSuccess(File file) {
                        String base64 = fileToBase64(file);
                        file.delete();
                        listener.compressSuccess(base64);
                    }

                    @Override
                    public void compressImageErr(String msg) {

                    }
                });
            }
        }).start();
    }

    /**
     * 综合压缩
     *
     * @param curFilePath
     */
    private static void compressImageToFile2(String curFilePath, String localPath, ImageCompressCallBack listener) {
        final File file = new File(localPath);//本地照片保存路径
        if (!file.getParentFile().exists()) {//如果本地没有此文件
            file.getParentFile().mkdirs();//创建该目录下的所有文件
        }
        Bitmap image = getBitmapFromFile(curFilePath);
        // 最大图片大小 150KB
        int maxSize = 100;
        // 获取尺寸压缩倍数
        int ratio = ImageFactory.getRatioSize(image.getWidth(), image.getHeight());
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(image.getWidth() / ratio, image.getHeight() / ratio, Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, image.getWidth() / ratio, image.getHeight() / ratio);
        canvas.drawBitmap(image, null, rect, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int quality = 100;
        result.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        Log.e(TAG, "baos.toByteArray().length==" + baos.toByteArray().length / 1024 + "kb");
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > maxSize) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            quality -= 10;
            Log.e(TAG, "quality:" + quality);
            // 这里压缩quality，把压缩后的数据存放到baos中
            result.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            listener.compressImageErr(e.toString());
            e.printStackTrace();
        }
        // JNI保存图片到SD卡 这个关键
//		ImageFactory.saveBitmap(result, quality, targetFilePath, true);
        // 释放Bitmap
        if (!result.isRecycled()) {
            result.recycle();
        }
        listener.compressImageSuccess(file);
    }

    /**
     * 计算缩放比
     *
     * @param bitWidth  当前图片宽度
     * @param bitHeight 当前图片高度
     * @return int 缩放比
     */
    public static int getRatioSize(int bitWidth, int bitHeight) {
        // 图片最大分辨率
        int imageHeight = 1280;
        int imageWidth = 960;
        // 缩放比
        int ratio = 1;
        // 缩放比,由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (bitWidth > bitHeight && bitWidth > imageWidth) {
            // 如果图片宽度比高度大,以宽度为基准
            ratio = bitWidth / imageWidth;
        } else if (bitWidth < bitHeight && bitHeight > imageHeight) {
            // 如果图片高度比宽度大，以高度为基准
            ratio = bitHeight / imageHeight;
        }
        // 最小比率为1
        if (ratio <= 0)
            ratio = 1;
        return ratio;
    }

    /**
     * 通过文件路径读获取Bitmap防止OOM以及解决图片旋转问题
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapFromFile(String filePath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        BitmapFactory.decodeFile(filePath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 获取尺寸压缩倍数
        newOpts.inSampleSize = ImageFactory.getRatioSize(w, h);
        newOpts.inJustDecodeBounds = false;//读取所有内容
        newOpts.inDither = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inTempStorage = new byte[32 * 1024];
        Bitmap bitmap = null;
        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fs != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, newOpts);
                //旋转图片
                int photoDegree = readPictureDegree(filePath);
                if (photoDegree != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(photoDegree);
                    // 创建新的图片
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public interface ImageCompressCallBack {
        void compressImageSuccess(File file);

        void compressImageErr(String msg);
    }

    public interface ImageCompressToBase64CallBack {
        void compressSuccess(String base64);

        void compressErr(String msg);
    }

    /**
     * 将图片转成Base64字符串
     * dengfeng add at 20170304
     *
     * @param file 文件
     * @return
     */
    public static String fileToBase64(File file) {
        // 将Bitmap转换成字符串
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bStream);//强行将图片转为JPEG,30表示压缩掉70%
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
}