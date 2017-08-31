package com.philong.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.philong.foody.R;
import com.philong.foody.controller.activity.ChiTietQuanAnActivity;
import com.philong.foody.model.BinhLuan;
import com.philong.foody.model.ChiNhanh;
import com.philong.foody.model.QuanAn;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

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
        final QuanAn quanAn = mQuanAnList.get(position);
        holder.bind(quanAn);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(ChiTietQuanAnActivity.newIntent(mContext, quanAn));
            }
        });
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
        public CircleImageView mAvatarUserSeconds;
        public TextView mTenUserSecondsTextView;
        public TextView mBinhLuanUserSecondsTextView;
        public TextView mDiemUserSecondsTextView;
        public TextView mSoLuongBinhLuanTextView;
        public TextView mSoLuongHinhTextView;
        public LinearLayout mBinhLuanFirst;
        public LinearLayout mBinhLuanSeconds;
        public CardView mCardView;

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
            mAvatarUserSeconds = (CircleImageView)itemView.findViewById(R.id.item_quan_an_user_2);
            mTenUserSecondsTextView = (TextView)itemView.findViewById(R.id.item_quan_an_ten_user_2);
            mBinhLuanUserSecondsTextView = (TextView)itemView.findViewById(R.id.item_quan_an_binh_luan_user_2);
            mDiemUserSecondsTextView = (TextView)itemView.findViewById(R.id.item_quan_an_diem_user_2);
            mSoLuongBinhLuanTextView = (TextView)itemView.findViewById(R.id.item_quan_an_so_luong_binh_luan);
            mSoLuongHinhTextView = (TextView)itemView.findViewById(R.id.item_quan_an_so_luong_hinh);
            mBinhLuanFirst = (LinearLayout)itemView.findViewById(R.id.item_quan_an_binh_luan_1);
            mBinhLuanSeconds = (LinearLayout)itemView.findViewById(R.id.item_quan_an_binh_luan_2);
            mCardView = (CardView)itemView.findViewById(R.id.item_quan_an_card_view);
        }

        public void bind(QuanAn quanAn){
            mTenQuanAnTextView.setText(quanAn.getTenquanan());
            if(quanAn.isGiaohang()){
                mDatMonButton.setVisibility(View.VISIBLE);
            }else{
                mDatMonButton.setVisibility(View.GONE);
            }
            //Set quán ăn
            if(quanAn.getHinhanhquanan().size() > 0){
                String hinhAnh = quanAn.getHinhanhquanan().get(0);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(hinhAnh);
                setImageView(storageReference, mHinhMonAnImageView);
            }
            //Set bình luận
            if(quanAn.getBinhluan().size() > 0){
                //Bình Luận User 1
                BinhLuan binhLuanUser1 = quanAn.getBinhluan().get(0);
                mTenUserFirstTextView.setText(binhLuanUser1.getTieude());
                mBinhLuanUserFirstTextView.setText(binhLuanUser1.getNoidung());
                mDiemUserFirstTextView.setText(String.valueOf(binhLuanUser1.getChamdiem()));
//                StorageReference storageReferenceFirst = FirebaseStorage.getInstance().getReference().child("thanhviens").child(binhLuanUser1.getThanhvien().getHinhanh());
//                setImageView(storageReferenceFirst, mAvatarUserFirst);
                if(quanAn.getBinhluan().size() > 2){
                    //Bình luận user 2
                    BinhLuan binhLuanUser2 = quanAn.getBinhluan().get(1);
                    mTenUserSecondsTextView.setText(binhLuanUser2.getTieude());
                    mBinhLuanUserSecondsTextView.setText(binhLuanUser2.getNoidung());
                    mDiemUserSecondsTextView.setText(String.valueOf(binhLuanUser2.getChamdiem()));
//                    StorageReference storageReferenceSeconds = FirebaseStorage.getInstance().getReference().child("thanhviens").child(binhLuanUser2.getThanhvien().getHinhanh());
//                    setImageView(storageReferenceSeconds, mAvatarUserSeconds);
                }
            }else{
                mBinhLuanFirst.setVisibility(View.GONE);
                mBinhLuanSeconds.setVisibility(View.GONE);
            }
            //Tong binh luan va hinh anh cua quan an
            mSoLuongBinhLuanTextView.setText(String.valueOf(quanAn.getBinhluan().size()));
            int tongSoHinhBinhLuan = 0;
            double tongDiem = 0.0;
            double diemTrungBinhQuanAn = 0.0;
            for(BinhLuan binhLuan : quanAn.getBinhluan()){
                tongSoHinhBinhLuan += binhLuan.getHinhanhbinhluans().size();
                tongDiem += binhLuan.getChamdiem();
            }
            NumberFormat numberFormat = new DecimalFormat("#.##");
            mSoLuongHinhTextView.setText(String.valueOf(tongSoHinhBinhLuan));
            diemTrungBinhQuanAn = (tongDiem) / quanAn.getBinhluan().size();
            if(diemTrungBinhQuanAn > 0){
                mDiemTextView.setText(String.valueOf(numberFormat.format(diemTrungBinhQuanAn)));
            }else{
                mDiemTextView.setText("0");
            }
            //Set dia chi, khoang cach gần nhất
            if(quanAn.getChinhanh().size() > 0){
                ChiNhanh chiNhanhTemp = quanAn.getChinhanh().get(0);
                for(int i = 1; i < quanAn.getChinhanh().size(); i++){
                    if(chiNhanhTemp.getKhoangcach() > quanAn.getChinhanh().get(i).getKhoangcach()){
                        chiNhanhTemp = quanAn.getChinhanh().get(i);
                    }
                }
                mDiaChiQuanAnTextView.setText(chiNhanhTemp.getDiachi());
                mSoKMTextView.setText(String.valueOf(numberFormat.format(chiNhanhTemp.getKhoangcach())) + " Km");
            }
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
