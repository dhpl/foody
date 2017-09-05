package com.philong.foody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.philong.foody.R;
import com.philong.foody.model.HinhAnh;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 9/4/2017.
 */

public class AdapterHinhAnh extends RecyclerView.Adapter<AdapterHinhAnh.HinhAnhViewHolder>{


    private List<HinhAnh> mHinhAnhList;
    private Context mContext;
    private ArrayList<HinhAnh> mHinhAnhChecked;

    public AdapterHinhAnh(List<HinhAnh> hinhAnhList, Context context) {
        mHinhAnhList = hinhAnhList;
        mContext = context;
        mHinhAnhChecked = new ArrayList<>();
    }

    @Override
    public HinhAnhViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hinh_anh, parent, false);
        return new HinhAnhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HinhAnhViewHolder holder, int position) {
        final HinhAnh hinhAnh = mHinhAnhList.get(position);
        holder.mHinhAnhImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hinhAnh.setChecked(!hinhAnh.isChecked());
                holder.mCheckBox.setChecked(hinhAnh.isChecked());
                if(hinhAnh.isChecked()){
                    mHinhAnhChecked.add(hinhAnh);
                }else{
                    mHinhAnhChecked.remove(hinhAnh);
                }
            }
        });
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hinhAnh.setChecked(b);
            }
        });

        holder.bind(hinhAnh);


    }

    @Override
    public int getItemCount() {
        return mHinhAnhList.size();
    }

    public class HinhAnhViewHolder extends RecyclerView.ViewHolder{

        private ImageView mHinhAnhImageView;
        private CheckBox mCheckBox;

        public HinhAnhViewHolder(View itemView) {
            super(itemView);
            mHinhAnhImageView = (ImageView)itemView.findViewById(R.id.item_hinh_anh_image_view);
            mCheckBox = (CheckBox)itemView.findViewById(R.id.item_hinh_anh_check_box);

        }

        public void bind(HinhAnh hinhAnh){
            File imgFile = new File(hinhAnh.getPath());
            Picasso.with(mContext)
                    .load(imgFile)
                    .fit()
                    .centerCrop()
                    .into(mHinhAnhImageView);
           mCheckBox.setChecked(hinhAnh.isChecked());
        }
    }

    public ArrayList<HinhAnh> getHinhAnhChecked() {
        return mHinhAnhChecked;
    }

    public void setHinhAnhChecked(ArrayList<HinhAnh> hinhAnhChecked) {
        mHinhAnhChecked = hinhAnhChecked;
    }
}
