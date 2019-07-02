package dream.api.dmf.cn.dreaming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dream.api.dmf.cn.dreaming.Constants;
import dream.api.dmf.cn.dreaming.R;

public class PhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_photo);

        Intent intent = getIntent();
        String photoUrl = intent.getExtras().getString(Constants.PHOTO);
        ImageView imageView = findViewById(R.id.iv_photo);

        Glide.with(this).load(photoUrl).into(imageView);
    }
}
