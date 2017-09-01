package com.philong.foody.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philong.foody.R;
import com.philong.foody.model.BinhLuan;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Long on 9/1/2017.
 */

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolderBinhLuan>{

    private List<BinhLuan> mBinhLuanList;
    private Context mContext;

    public AdapterBinhLuan(List<BinhLuan> binhLuanList, Context context) {
        mBinhLuanList = binhLuanList;
        mContext = context;
    }

    @Override
    public ViewHolderBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_binh_luan, parent, false);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_no_comment, parent, false);
        if(mBinhLuanList.size() == 0){
            return new ViewHolderBinhLuan(view2);
        }
        return new ViewHolderBinhLuan(view1);
    }

    @Override
    public void onBindViewHolder(ViewHolderBinhLuan holder, int position) {
        if(mBinhLuanList.size() > 0){
            BinhLuan binhLuan = mBinhLuanList.get(position);
            holder.bind(binhLuan);
        }
    }

    @Override
    public int getItemCount() {
        if(mBinhLuanList.size() == 0){
            return 1;
        }else{
            return mBinhLuanList.size();
        }
    }

    public class ViewHolderBinhLuan extends RecyclerView.ViewHolder{

        public TextView mTieuDeBinhLuanTextView;
        public TextView mNoiDungBinhLuanTextView;
        public TextView mPoint;
        public CircleImageView mAvatar;
        public RecyclerView mHinhBinhLuanRecyclerView;
        public AdapterHinhBinhLuan mAdapterHinhBinhLuan;
        public List<String> mLinkHinhs;

        public ViewHolderBinhLuan(View itemView) {
            super(itemView);
            mTieuDeBinhLuanTextView = (TextView) itemView.findViewById(R.id.item_binh_luan_ten_user_text_view);
            mNoiDungBinhLuanTextView = (TextView)itemView.findViewById(R.id.item_binh_luan_text_view);
            mPoint = (TextView)itemView.findViewById(R.id.item_binh_luan_point);
            mAvatar = (CircleImageView)itemView.findViewById(R.id.item_binh_luan_quan_an_image_view);
            mHinhBinhLuanRecyclerView = (RecyclerView)itemView.findViewById(R.id.item_binh_luan_hinh_recycler_view);

        }

        public void bind(BinhLuan binhLuan){
            mLinkHinhs = new ArrayList<>();
            mLinkHinhs = binhLuan.getHinhanhbinhluans();
            mTieuDeBinhLuanTextView.setText(binhLuan.getTieude());
            mNoiDungBinhLuanTextView.setText(binhLuan.getNoidung());
            mPoint.setText(String.valueOf(binhLuan.getChamdiem()));
            mAdapterHinhBinhLuan = new AdapterHinhBinhLuan(mLinkHinhs, mContext);
            mHinhBinhLuanRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mHinhBinhLuanRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            mHinhBinhLuanRecyclerView.setNestedScrollingEnabled(false);
            mHinhBinhLuanRecyclerView.setAdapter(mAdapterHinhBinhLuan);
        }
    }

}
