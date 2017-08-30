package com.philong.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.philong.foody.R;
import com.philong.foody.model.QuanAn;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Long on 8/30/2017.
 */

public class AdapterQuanAn extends RecyclerView.Adapter<AdapterQuanAn.ViewHolderQuanAn>{

    private List<QuanAn> mQuanAnList;
    private Context mContext;

    public AdapterQuanAn(List<QuanAn> quanAnList, Context context) {
        mQuanAnList = quanAnList;
        mContext = context;
    }

    @Override
    public ViewHolderQuanAn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_quan_an, parent, false);
        return new ViewHolderQuanAn(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderQuanAn holder, int position) {
        QuanAn quanAn = mQuanAnList.get(position);
        holder.bind(quanAn);
    }

    @Override
    public int getItemCount() {
        return mQuanAnList.size();
    }

    public class ViewHolderQuanAn extends RecyclerView.ViewHolder{

        public TextView mDiemTextView;
        public TextView mTenQuanAnTextView;
        public TextView mDiaChiQuanAnTextView;
        public TextView mSoKMTextView;
        public ImageView mHinhMonAnImageView;
        public Button mDatMonButton;
        public CircleImageView mAvatarUserFirst;
        public TextView mTenUserFirstTextView;
        public TextView mBinhLuanUserFirstTextView;
        public TextView mDiemUserFirstTextView;
        public CircleImageView mAvatarUserSecondsTextView;
        public TextView mTenUserSecondsTextView;
        public TextView mBinhLuanUserSecondsTextView;
        public TextView mDiemUserSecondsTextView;
        public TextView mSoLuongBinhLuanTextView;
        public TextView mSoLuongHinhTextView;

        public ViewHolderQuanAn(View itemView) {
            super(itemView);
            mDiemTextView = (TextView)itemView.findViewById(R.id.item_quan_an_diem_text_view);
            mTenQuanAnTextView = (TextView)itemView.findViewById(R.id.item_quan_an_ten_text_view);
            mDiaChiQuanAnTextView = (TextView)itemView.findViewById(R.id.item_quan_an_dia_chi_text_view);
            mSoKMTextView = (TextView)itemView.findViewById(R.id.item_quan_an_so_km_text_view);
            mHinhMonAnImageView = (ImageView)itemView.findViewById(R.id.item_quan_an_hinh_image_view);
            mDatMonButton = (Button)itemView.findViewById(R.id.item_quan_an_dat_mon_button);
            mAvatarUserFirst = (CircleImageView)itemView.findViewById(R.id.item_quan_an_use_1);
            mTenUserFirstTextView = (TextView)itemView.findViewById(R.id.item_quan_an_ten_user_1);
            mBinhLuanUserFirstTextView = (TextView)itemView.findViewById(R.id.item_quan_binh_luan_user_1);
            mDiemUserFirstTextView = (TextView)itemView.findViewById(R.id.item_quan_an_diem_user_1);
            mAvatarUserSecondsTextView = (CircleImageView)itemView.findViewById(R.id.item_quan_an_use_1);
            mTenUserSecondsTextView = (TextView)itemView.findViewById(R.id.item_quan_an_ten_user_1);
            mBinhLuanUserSecondsTextView = (TextView)itemView.findViewById(R.id.item_quan_binh_luan_user_1);
            mDiemUserSecondsTextView = (TextView)itemView.findViewById(R.id.item_quan_an_diem_user_1);
            mSoLuongBinhLuanTextView = (TextView)itemView.findViewById(R.id.item_quan_an_so_luong_binh_luan);
            mSoLuongHinhTextView = (TextView)itemView.findViewById(R.id.item_quan_an_so_luong_hinh);
        }

        public void bind(QuanAn quanAn){
            mTenQuanAnTextView.setText(quanAn.getTenquanan());
            if(quanAn.isGiaohang()){
                mDatMonButton.setVisibility(View.VISIBLE);
            }else{
                mDatMonButton.setVisibility(View.GONE);
            }
            if(quanAn.getHinhanhquanan().size() > 0){
                String hinhAnh = quanAn.getHinhanhquanan().get(new Random().nextInt(quanAn.getHinhanhquanan().size()));
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(hinhAnh);
                final long ONE_MEGABYTE = 1024 * 1024;
                storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        mHinhMonAnImageView.setImageBitmap(bitmap);
                    }
                });

            }
        }
    }

}
