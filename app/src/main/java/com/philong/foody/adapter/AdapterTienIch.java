package com.philong.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.philong.foody.R;
import com.philong.foody.model.TienIch;

import java.util.List;

/**
 * Created by Long on 9/1/2017.
 */

public class AdapterTienIch extends RecyclerView.Adapter<AdapterTienIch.ViewHolderTienIch>{

    private List<TienIch> mTienIchList;
    private Context mContext;

    public AdapterTienIch(List<TienIch> tienIchList, Context context) {
        mTienIchList = tienIchList;
        mContext = context;
    }

    @Override
    public ViewHolderTienIch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tien_ich, parent, false);
        return new ViewHolderTienIch(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderTienIch holder, int position) {
        TienIch tienIch = mTienIchList.get(position);
        holder.bind(tienIch);
    }

    @Override
    public int getItemCount() {
        return mTienIchList.size();
    }

    public class ViewHolderTienIch extends RecyclerView.ViewHolder{

        public ImageView mTienIchImageView;
        public TextView mTenTienIchTextView;

        public ViewHolderTienIch(View itemView) {
            super(itemView);
            mTienIchImageView = (ImageView)itemView.findViewById(R.id.item_tien_ich_image_view);
            mTenTienIchTextView = (TextView)itemView.findViewById(R.id.item_tien_ich_text_view);
        }

        public void bind(TienIch tienIch){
            mTenTienIchTextView.setText(tienIch.getTentienich());
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(tienIch.getHinhtienich());
            setImageView(storageReference, mTienIchImageView);
            System.out.println("TienIch: " + tienIch.getTentienich());
            System.out.println("TienIch: " + tienIch.getHinhtienich());
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
