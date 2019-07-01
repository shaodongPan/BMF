package dream.api.dmf.cn.dreaming.utils;

import android.net.Uri;
import android.os.Environment;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;

public class CustomHelper {

    private static final String MAX_WIDTH = "800";
    private static final String MAX_HEIGHT = "800";

    private TakePhoto mTakePhoto;

    public CustomHelper(TakePhoto takePhoto) {
        this.mTakePhoto = takePhoto;

        configCompress();
        configTakePhotoOption();
    }

    /**
     * 配置相册
     */
    private void configTakePhotoOption() {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        mTakePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 配置图片压缩选项
     */
    private void configCompress() {
        //暂时不进行压缩，开始图片压缩可能某些机型会crash
        mTakePhoto.onEnableCompress(null, false);
    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt(MAX_WIDTH);
        int width = Integer.parseInt(MAX_HEIGHT);
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    public void selectPhoto(int number) {
        try {
            mTakePhoto.onPickMultipleWithCrop(number, getCropOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takePhoto() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/Toojing/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Uri mImageUri = Uri.fromFile(file);
            mTakePhoto.onPickFromCaptureWithCrop(mImageUri, getCropOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
