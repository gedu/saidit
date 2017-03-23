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

package com.gemapps.saidit.ui.toplisting.presenter;

import com.gemapps.saidit.busitem.EntryResponseBridge;
import com.gemapps.saidit.busitem.OauthEventBridge;
import com.gemapps.saidit.ui.paginator.PaginationManager;
import com.gemapps.saidit.ui.toplisting.TopListingAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by edu on 3/21/17.
 */

public class FragmentPresenter implements FragmentContract.OnInteractionListener {
    private static final String TAG = "FragmentPresenter";

    private FragmentContract.View mView;
    private TopListingAdapter mAdapter;

    public FragmentPresenter(FragmentContract.View view) {
        mView = view;
    }

    @Override
    public void onEventBusSubscribe(EventBus bus) {
        bus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkResponseEvent(EntryResponseBridge response){
        mView.onPopulateList(response.getItems());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkResponseEvent(OauthEventBridge response){
        if(response.getState() == OauthEventBridge.SUCCESS) loadBasicListing();
        else mView.showOauthError();
    }

    @Override
    public void onEventBusUnSubscribe(EventBus bus) {
        bus.unregister(this);
    }

    @Override
    public void addAdapter(TopListingAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void updateListingView() {
        mView.hideOauthError();
        if (mAdapter.getItemCount() > 0) mView.hideEmptyView();
        else mView.showEmptyView();
    }

    @Override
    public void loadBasicListing() {
        PaginationManager.getInstance().onStart();
    }
}
