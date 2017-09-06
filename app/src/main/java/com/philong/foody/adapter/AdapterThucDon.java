package com.philong.foody.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philong.foody.R;
import com.philong.foody.model.ThucDon;

import java.util.List;

/**
 * Created by Long on 9/6/2017.
 */

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.ThucDonViewHolder>{

    private Context mContext;
    private List<ThucDon> mThucDonList;

    public AdapterThucDon(Context context, List<ThucDon> thucDonList) {
        mContext = context;
        mThucDonList = thucDonList;
    }

    @Override
    public ThucDonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_thuc_don, parent, false);
        return new ThucDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThucDonViewHolder holder, int position) {
        ThucDon thucDon = mThucDonList.get(position);
        holder.bind(thucDon);
    }

    @Override
    public int getItemCount() {
        return mThucDonList.size();
    }

    public class ThucDonViewHolder extends RecyclerView.ViewHolder{

        private TextView mTieuDeTextView;
        private AdapterMonAn mAdapterMonAn;
        private RecyclerView mThucDonRecyclerView;

        public ThucDonViewHolder(View itemView) {
            super(itemView);
            mTieuDeTextView = (TextView)itemView.findViewById(R.id.item_thuc_don_ten_text_view);
            mThucDonRecyclerView = (RecyclerView)itemView.findViewById(R.id.item_thuc_don_recycler_view);
        }

        public void bind(ThucDon thucDon){
            mTieuDeTextView.setText(thucDon.getTenThucDon());
            mThucDonRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mThucDonRecyclerView.setNestedScrollingEnabled(false);
            mThucDonRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mAdapterMonAn = new AdapterMonAn(mContext, thucDon.getMonAnList());
            mThucDonRecyclerView.setAdapter(mAdapterMonAn);
        }
    }
}
