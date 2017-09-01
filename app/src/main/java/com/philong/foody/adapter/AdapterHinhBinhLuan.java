package com.philong.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.philong.foody.R;

import java.util.List;

/**
 * Created by Long on 9/1/2017.
 */

public class AdapterHinhBinhLuan extends RecyclerView.Adapter<AdapterHinhBinhLuan.ViewHodlerHinhBinhLuan>{

    private List<String> mLinkHinhs;
    private Context mContext;

    public AdapterHinhBinhLuan(List<String> linkHinhs, Context context) {
        mLinkHinhs = linkHinhs;
        mContext = context;
    }

    @Override
    public ViewHodlerHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hinh_binh_luan, parent, false);
        return new ViewHodlerHinhBinhLuan(view);
    }

    @Override
    public void onBindViewHolder(ViewHodlerHinhBinhLuan holder, int position) {
        String link = mLinkHinhs.get(position);
        holder.bind(link);
    }

    @Override
    public int getItemCount() {
        return  mLinkHinhs.size();
    }

    public class ViewHodlerHinhBinhLuan extends RecyclerView.ViewHolder {

       public ImageView mHinhBinhLuanImageView;
       public String mHinhList;

       public ViewHodlerHinhBinhLuan(View itemView) {
           super(itemView);
           mHinhBinhLuanImageView = (ImageView)itemView.findViewById(R.id.item_hinh_binh_luan_image_view);
       }

       public void bind(String link){
           StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(link);
           setImageView(storageReference, mHinhBinhLuanImageView);
       }

       public void setImageView(StorageReference storageReference, final ImageView imageView){
           final long ONE_MEGABYTE = 1024 * 1024;
           storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
               @Override
               public void onSuccess(byte[] bytes) {
                   Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                   imageView.setImageBitmap(bitmap);
               }
           });
       }

   }

}
