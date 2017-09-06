package com.philong.foody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.philong.foody.R;
import com.philong.foody.model.MonAn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 9/6/2017.
 */

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.MonAnViewHolder>{

    private Context mContext;
    private List<MonAn> mMonAnList;
    private static List<MonAn> mDatMonList = new ArrayList<>();


    public AdapterMonAn(Context context, List<MonAn> monAnList) {
        mContext = context;
        mMonAnList = monAnList;
        if(mDatMonList.size() > 0){
            mDatMonList.clear();
        }
    }

    @Override
    public MonAnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mon_an, parent, false);
        return new MonAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MonAnViewHolder holder, int position) {
        final MonAn monAn = mMonAnList.get(position);
        holder.bind(monAn);
        holder.mSoLuongTextView.setTag(0);
        holder.mTangSoLuongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mSoLuongTextView.setTag(Integer.parseInt(holder.mSoLuongTextView.getTag().toString()) + 1);
                holder.mSoLuongTextView.setText(holder.mSoLuongTextView.getTag().toString());
                mDatMonList.add(monAn);
                System.out.println("Size + " + mDatMonList.size());
            }
        });
        holder.mGiamSoLuongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(holder.mSoLuongTextView.getTag().toString()) > 0){
                    holder.mSoLuongTextView.setTag(Integer.parseInt(holder.mSoLuongTextView.getTag().toString()) - 1);
                    holder.mSoLuongTextView.setText(holder.mSoLuongTextView.getTag().toString());
                    mDatMonList.remove(monAn);
                    System.out.println("Size - " + mDatMonList.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMonAnList.size();
    }

    public class MonAnViewHolder extends RecyclerView.ViewHolder{

        private TextView mTenMonAnTextView;
        private ImageView mTangSoLuongImageView;
        private ImageView mGiamSoLuongImageView;
        private TextView mSoLuongTextView;

        public MonAnViewHolder(View itemView) {
            super(itemView);
            mTenMonAnTextView = (TextView) itemView.findViewById(R.id.item_mon_an_text_view);
            mSoLuongTextView = (TextView)itemView.findViewById(R.id.item_mon_an_so_luong_text_view);
            mTangSoLuongImageView = (ImageView)itemView.findViewById(R.id.item_mon_an_tan_image_view);
            mGiamSoLuongImageView = (ImageView)itemView.findViewById(R.id.item_mon_an_giam_image_view);
        }

        public void bind(MonAn monAn){
            mTenMonAnTextView.setText(monAn.getTenmon());
        }
    }
}
