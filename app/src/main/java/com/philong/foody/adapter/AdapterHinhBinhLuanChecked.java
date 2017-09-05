package com.philong.foody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.philong.foody.R;
import com.philong.foody.model.HinhAnh;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Long on 9/5/2017.
 */

public class AdapterHinhBinhLuanChecked extends RecyclerView.Adapter<AdapterHinhBinhLuanChecked.HinhBinhLuanCheckedViewHolder>{

    private Context mContext;
    private ArrayList<HinhAnh> mHinhAnhs;

    public AdapterHinhBinhLuanChecked(Context context, ArrayList<HinhAnh> hinhAnhs) {
        mContext = context;
        mHinhAnhs = hinhAnhs;
    }

    @Override
    public HinhBinhLuanCheckedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hinh_anh_binh_luan_checked, parent, false);
        return new HinhBinhLuanCheckedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HinhBinhLuanCheckedViewHolder holder, int position) {
        final HinhAnh hinhAnh = mHinhAnhs.get(position);
        holder.bind(hinhAnh);
        holder.mBinhLuanDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHinhAnhs.remove(hinhAnh);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHinhAnhs.size();
    }

    public class HinhBinhLuanCheckedViewHolder extends RecyclerView.ViewHolder{

        private ImageView mBinhLuanCheckedImageView;
        private ImageView mBinhLuanDeleteImageView;

        public HinhBinhLuanCheckedViewHolder(View itemView) {
            super(itemView);
            mBinhLuanCheckedImageView = (ImageView) itemView.findViewById(R.id.item_hinh_anh_checked_image_view);
            mBinhLuanDeleteImageView = (ImageView)itemView.findViewById(R.id.item_hinh_anh_checked_check_box);
        }

        public void bind(HinhAnh hinhAnh){
            File imgFile = new File(hinhAnh.getPath());
            Picasso.with(mContext)
                    .load(imgFile)
                    .fit()
                    .centerCrop()
                    .into(mBinhLuanCheckedImageView);

        }
    }
}
