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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gemapps.saidit.R;
import com.gemapps.saidit.networking.RedditListingManager;
import com.gemapps.saidit.ui.butter.ButterFragment;
import com.gemapps.saidit.ui.model.TopListingItem;
import com.gemapps.saidit.ui.picturedetail.PictureDetailActivity;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentContract;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class TopListingFragment extends ButterFragment
        implements FragmentContract.View, TopListingAdapter.ListingListener {

    private static final String TAG = "TopListingFragment";
    private static final String LISTING_LIST_PREF = "saidit.LISTING_LIST_PREF";
    private FragmentContract.OnInteractionListener mInteractionListener;
    private TopListingAdapter mAdapter;
    private TopListingViewHelper mViewHelper;

    public TopListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupPresenter();
    }

    private void setupPresenter(){
        //Some tablets/android don't call the onAttach method
        if(mInteractionListener == null) mInteractionListener = new FragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = createView(inflater, container, R.layout.fragment_top_listing);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewHelper = new TopListingViewHelper(view);
        setupPresenter();
        if(savedInstanceState == null) loadFirstContent();
        else rebuildState(savedInstanceState);
    }

    private void loadFirstContent(){
        mInteractionListener.loadBasicListing();
    }

    private void rebuildState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(LISTING_LIST_PREF)) {
            ArrayList<TopListingItem> listingItems = (ArrayList<TopListingItem>) savedInstanceState.get(LISTING_LIST_PREF);

            if(listingItems != null && listingItems.size() > 0) onPopulateList(listingItems);
            else mInteractionListener.loadBasicListing();
        } else {
            mInteractionListener.loadBasicListing();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mInteractionListener.onEventBusSubscribe(EventBus.getDefault());
    }

    @Override
    public void onStop() {
        mInteractionListener.onEventBusUnSubscribe(EventBus.getDefault());
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(mAdapter != null)
            outState.putParcelableArrayList(LISTING_LIST_PREF, mAdapter.getListingItems());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPopulateList(List<TopListingItem> listingItems) {
        updateAdapter(listingItems);

        mInteractionListener.updateListingView();
    }

    private void updateAdapter(List<TopListingItem> listingItems){
        if(mAdapter == null) {
            mAdapter = new TopListingAdapter(getActivity(), listingItems, this);
            mViewHelper.setAdapter(mAdapter);
            mInteractionListener.addAdapter(mAdapter);
        } else {
            mAdapter.setItems(listingItems);
            sendToTheTop();
        }
    }

    public void showLoading(){
        mViewHelper.showLoading();
    }

    public void hideLoading() {
        mViewHelper.hideLoading();
    }

    public void sendToTheTop(){
        mViewHelper.sendToTheTop();
    }

    @Override
    public void hideEmptyView() {
        mViewHelper.hideLoading();
        mViewHelper.hideEmptyView();
        mViewHelper.showRecyclerView();
    }

    @Override
    public void showEmptyView() {
        mViewHelper.hideRecyclerView();
        mViewHelper.hideLoading();
        mViewHelper.showEmptyView(new TopListingViewHelper.ErrorViewListener() {
            @Override
            public void onTryAgain() {
                mViewHelper.showLoading();
                mInteractionListener.loadBasicListing();
            }
        });
    }

    @Override
    public void showOauthError() {
        mViewHelper.hideRecyclerView();
        mViewHelper.hideLoading();
        mViewHelper.showOauthError(new TopListingViewHelper.ErrorViewListener() {
            @Override
            public void onTryAgain() {
                mViewHelper.showLoading();
                RedditListingManager.getInstance().authenticate();
            }
        });
    }

    @Override
    public void hideOauthError() {
        mViewHelper.hideLoading();
        mViewHelper.hideOauthError();
        mViewHelper.showRecyclerView();
    }

    @Override
    public void onPictureClicked(TopListingItem listingItem, ImageView mPictureImage) {

        Intent intent = PictureDetailActivity.getInstance(getActivity(), listingItem.getPictureUrl());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(),
                        mPictureImage, getString(R.string.saidit_pic_trans_name));
        startActivity(intent, options.toBundle());
    }
}

