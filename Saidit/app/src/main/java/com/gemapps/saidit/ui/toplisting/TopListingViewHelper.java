/*
 *    Copyright 2017 Edu Graciano
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gemapps.saidit.ui.toplisting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.gemapps.saidit.R;
import com.gemapps.saidit.util.ViewUtil;

import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 3/22/17.
 */

public class TopListingViewHelper {

    interface ErrorViewListener {
        void onTryAgain();
    }

    private static final int LIST_ORIENTATION = LinearLayoutManager.VERTICAL;
    @BindView(R.id.oauth_error_stub)
    ViewStub mOauthErrorStub;
    @BindView(R.id.empty_list_stub)
    ViewStub mEmptyListStub;
    @BindView(R.id.saidit_header_container)
    View mHeader;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.top_listing_recycler)
    RecyclerView mTopListingRecycler;
    @BindDimen(R.dimen.list_elevation)
    int ELEVATION;
    @BindInt(R.integer.amount_of_columns)
    int AMOUNT_OF_COLUMNS;

    private View mOauthErrorView;
    private View mEmptyListView;

    private StaggeredGridLayoutManager mLayoutManager;

    public TopListingViewHelper(View root) {
        ButterKnife.bind(this, root);
        setupRecycler();
    }

    private void setupRecycler() {
        mTopListingRecycler.setLayoutManager(getLayoutManager());
    }

    private StaggeredGridLayoutManager getLayoutManager() {

        mLayoutManager = new StaggeredGridLayoutManager(AMOUNT_OF_COLUMNS, LIST_ORIENTATION);
        return mLayoutManager;
    }

    public void setAdapter(TopListingAdapter adapter) {
        updateRecyclerUI();
        mTopListingRecycler.setAdapter(adapter);
    }

    private void updateRecyclerUI() {

        addPaddingTopPadding();
        mTopListingRecycler.setVisibility(View.VISIBLE);
        mTopListingRecycler.setElevation(ELEVATION);
    }

    private void addPaddingTopPadding() {
        if (mHeader.getHeight() == 0) {
            mHeader.getViewTreeObserver().addOnGlobalLayoutListener(mContainerLayoutListener);
        } else {
            ViewUtil.setPaddingTop(mTopListingRecycler, mHeader.getHeight());
        }
    }

    private final ViewTreeObserver.OnGlobalLayoutListener mContainerLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            ViewUtil.setPaddingTop(mTopListingRecycler, mHeader.getHeight());
            mHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };

    public void showOauthError(final ErrorViewListener listener){
        if(mOauthErrorView == null) mOauthErrorView = mOauthErrorStub.inflate();
        else mOauthErrorView.setVisibility(View.VISIBLE);

        mOauthErrorView.findViewById(R.id.try_again_button)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTryAgain();
            }
        });
    }

    public void hideOauthError(){
        if(mOauthErrorView != null) mOauthErrorView.setVisibility(View.GONE);
    }


    public void showEmptyView(final ErrorViewListener listener){
        if(mEmptyListView == null) mEmptyListView = mEmptyListStub.inflate();
        else mEmptyListView.setVisibility(View.VISIBLE);

        mEmptyListView.findViewById(R.id.try_again_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onTryAgain();
                    }
                });
    }

    public void hideEmptyView(){
        if(mEmptyListView != null) mEmptyListView.setVisibility(View.GONE);
    }

    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
