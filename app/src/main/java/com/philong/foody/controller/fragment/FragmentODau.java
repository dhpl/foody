package com.philong.foody.controller.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.philong.foody.R;
import com.philong.foody.adapter.AdapterQuanAn;
import com.philong.foody.model.QuanAn;

import java.util.List;

/**
 * Created by Long on 8/30/2017.
 */

public class FragmentODau extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private QuanAn mQuanAn;
    private Location location;

    private AdapterQuanAn mAdapterQuanAn;

    private RadioButton mRadioMoiNhat;
    private RadioButton mRadioDanhMuc;
    private RadioButton mRadioKhuVuc;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressBar mProgressBar;

    private SharedPreferences mSharedPreferences;

    public static FragmentODau newInstance() {
        Bundle args = new Bundle();
        FragmentODau fragment = new FragmentODau();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_dau, container, false);
        //Get sharepreferences
        mSharedPreferences = getActivity().getSharedPreferences("Location", Context.MODE_PRIVATE);
        double latitude = Double.parseDouble(mSharedPreferences.getString("Latitude", ""));
        double longitude = Double.parseDouble(mSharedPreferences.getString("Longitude", ""));
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        //Get view
        mProgressBar = (ProgressBar)view.findViewById(R.id.o_dau_progrss_bar);
        mRadioMoiNhat = (RadioButton)view.findViewById(R.id.o_dau_moi_nhat_radio);
        mRadioDanhMuc = (RadioButton)view.findViewById(R.id.o_dau_danh_muc_radio);
        mRadioKhuVuc = (RadioButton)view.findViewById(R.id.o_dau_khu_vuc_radio);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.o_dau_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.o_dau_swipe);
        mRadioMoiNhat.setChecked(true);
        //Set progress bar
        //Set recyclerview
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setOnScrollListener(mOnScrollListener);
        mQuanAn = new QuanAn();

        mQuanAn.getDanhSachQuanAn(new QuanAn.DanhSachQuanAn() {
            @Override
            public void completeDanhSachQuanAn(List<QuanAn> quanAnList) {
                mAdapterQuanAn = new AdapterQuanAn(quanAnList, getActivity());
                mRecyclerView.setAdapter(mAdapterQuanAn);
                mProgressBar.setVisibility(View.GONE);
            }
        }, location);



        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int first = mLinearLayoutManager.findFirstVisibleItemPosition();
            if(first == 0){
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mQuanAn.getDanhSachQuanAn(new QuanAn.DanhSachQuanAn() {
                            @Override
                            public void completeDanhSachQuanAn(List<QuanAn> quanAnList) {
                                mAdapterQuanAn = new AdapterQuanAn(quanAnList, getActivity());
                                mRecyclerView.setAdapter(mAdapterQuanAn);
                                mProgressBar.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, location);
                    }
                });
            }
        }
    };

}
