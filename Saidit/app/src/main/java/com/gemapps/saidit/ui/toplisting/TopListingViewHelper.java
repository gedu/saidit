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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.gemapps.saidit.R;
import com.gemapps.saidit.util.ViewUtil;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 3/22/17.
 */

public class TopListingViewHelper {

    private static final int LIST_ORIENTATION = LinearLayoutManager.VERTICAL;
    @BindView(R.id.saidit_header_container)
    View mHeader;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.top_listing_recycler)
    RecyclerView mTopListingRecycler;
    @BindDimen(R.dimen.list_elevation)
    int ELEVATION;

    private GridLayoutManager mLayoutManager;

    public TopListingViewHelper(View root) {
        ButterKnife.bind(this, root);
        setupRecycler();
    }

    private void setupRecycler() {
        mTopListingRecycler.setLayoutManager(getLayoutManager());
    }

    private GridLayoutManager getLayoutManager() {
        mLayoutManager = new GridLayoutManager(mTopListingRecycler.getContext(),
                1, LIST_ORIENTATION, false);
        return mLayoutManager;
    }

    public void setAdapter(TopListingAdapter adapter) {
        updateRecyclerUI();
        mTopListingRecycler.setAdapter(adapter);
    }

    private void updateRecyclerUI() {

        addPaddingTopPadding();
        // forward clicks to the description view
        mTopListingRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int firstVisible = mLayoutManager.findFirstVisibleItemPosition();

                if (firstVisible > 0) return false;

                if (mTopListingRecycler.getAdapter().getItemCount() == 0)
                    return mHeader.dispatchTouchEvent(event);

                final RecyclerView.ViewHolder vh = mTopListingRecycler.findViewHolderForAdapterPosition(0);
                if (vh == null) return false;

                if (event.getY() < vh.itemView.getTop())
                    return mHeader.dispatchTouchEvent(event);

                return false;
            }
        });

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

    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
