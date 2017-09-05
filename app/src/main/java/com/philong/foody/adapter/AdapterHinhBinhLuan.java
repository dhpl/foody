package com.philong.foody.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.philong.foody.R;
import com.squareup.picasso.Picasso;

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
           FirebaseStorage.getInstance().getReference().child(link).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   Picasso.with(mContext)
                           .load(uri)
                           .fit()
                           .centerCrop()
                           .into(mHinhBinhLuanImageView);
               }
           });
       }


   }

}
