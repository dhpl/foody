package com.philong.foody.controller.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.philong.foody.model.HinhAnh;

import java.io.File;

/**
 * Created by Long on 9/5/2017.
 */

public class PushImage extends IntentService {

    public PushImage() {
        super("PushImage");
    }

    public static Intent newIntent(Context context, HinhAnh hinhAnh, String key){
        Intent intent = new Intent(context, PushImage.class);
        intent.putExtra("HinhAnh", hinhAnh);
        intent.putExtra("Key", key);
        return intent;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        HinhAnh hinhAnh = intent.getParcelableExtra("HinhAnh");
        String key = intent.getStringExtra("Key");
        pushImage(hinhAnh, key);
    }

    public void pushImage(HinhAnh hinhAnh, final String key){
        final Uri uri = Uri.fromFile(new File(hinhAnh.getPath()));
        StorageReference storageReferenceHinhAnh = FirebaseStorage.getInstance().getReference()
                .child(uri.getLastPathSegment());
        storageReferenceHinhAnh.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans")
                            .child(key).push().setValue(uri.getLastPathSegment());
                }
            }
        });

    }
}
