package com.example.zatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zatch.databinding.ActivityGalleryAfterImagePickBinding;

public class ImageSelectActivity extends AppCompatActivity {

    private ActivityGalleryAfterImagePickBinding binding;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGalleryAfterImagePickBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageUri = getIntent().getParcelableExtra("imageUri");

        binding.imageCheckView.setImageURI(imageUri);

        binding.chooseImageButton.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.putExtra("imageResult",imageUri);
            setResult(RESULT_OK,intent);
            finish();
        });

        binding.backButtonToGallery.setOnClickListener(v->{

        });
    }
}
