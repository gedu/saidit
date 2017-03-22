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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gemapps.saidit.R;
import com.gemapps.saidit.ui.butter.ButterFragment;
import com.gemapps.saidit.ui.model.TopListingItem;
import com.gemapps.saidit.ui.paginator.PaginationManager;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentContract;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

public class TopListingFragment extends ButterFragment
        implements FragmentContract.View {

    private FragmentContract.OnInteractionListener mInteractionListener;
    private TopListingAdapter mAdapter;
    private TopListingViewHelper mViewHelper;

    public TopListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInteractionListener = new FragmentPresenter(this);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        mInteractionListener.onEventBusSubscribe(EventBus.getDefault());
    }

    @Override
    public void onResume() {
        super.onResume();
        PaginationManager.getInstance().onStart();
    }

    @Override
    public void onStop() {
        mInteractionListener.onEventBusUnSubscribe(EventBus.getDefault());
        super.onStop();
    }

    @Override
    public void onPopulateList(List<TopListingItem> listingItems) {
        mAdapter = new TopListingAdapter(getActivity(), listingItems);
        mViewHelper.setAdapter(mAdapter);
        mInteractionListener.addAdapter(mAdapter);
        mInteractionListener.updateListingView();
    }

    @Override
    public void hideEmptyView() {
        mViewHelper.hideLoading();
    }

    @Override
    public void showEmptyView() {
        //todo; implement it
        mViewHelper.showLoading();
    }
}

