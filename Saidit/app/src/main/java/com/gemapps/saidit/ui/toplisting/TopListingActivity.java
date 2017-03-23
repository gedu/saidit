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

import android.os.Bundle;
import android.view.View;

import com.gemapps.saidit.R;
import com.gemapps.saidit.ui.butter.ButterActivity;
import com.gemapps.saidit.ui.toplisting.presenter.ActivityContract;
import com.gemapps.saidit.ui.toplisting.presenter.ActivityPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class TopListingActivity extends ButterActivity
        implements ActivityContract.View {

    private static final String TAG = "TopListingActivity";
    private static final String PREV_STATUS_KEY = "saidit.PREV_STATUS_KEY";
    private static final String NEXT_STATUS_KEY = "saidit.NEXT_STATUS_KEY";
    @BindView(R.id.prev_button_text)
    View mPrevButton;
    @BindView(R.id.next_button_text)
    View mNextButton;

    private ActivityContract.OnInteractionListener mInteractionListener;
    private TopListingFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_listing);
        mInteractionListener = new ActivityPresenter(this);
        mFragment = (TopListingFragment) getFragmentManager()
                .findFragmentById(R.id.top_listing_content_fragment);
        if(savedInstanceState != null) rebuildState(savedInstanceState);
    }

    private void rebuildState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(PREV_STATUS_KEY)){
            mPrevButton.setEnabled(savedInstanceState.getBoolean(PREV_STATUS_KEY));
            mNextButton.setEnabled(savedInstanceState.getBoolean(NEXT_STATUS_KEY));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mInteractionListener.onEventBusSubscribe(EventBus.getDefault());
    }

    @Override
    protected void onStop() {
        mInteractionListener.onEventBusUnSubscribe(EventBus.getDefault());
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(PREV_STATUS_KEY, mPrevButton.isEnabled());
        outState.putBoolean(NEXT_STATUS_KEY, mNextButton.isEnabled());
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.prev_button_text)
    public void onPrevClick(){
        mFragment.showLoading();
        mInteractionListener.onPrevClicked();
    }

    @OnClick(R.id.next_button_text)
    public void onNextClick(){
        mFragment.showLoading();
        mInteractionListener.onNextClicked();
    }

    @Override
    public void onDisablePrevButton() {
        mPrevButton.setEnabled(false);
        mFragment.hideLoading();
    }

    @Override
    public void onDisableNextButton() {
        mNextButton.setEnabled(false);
    }

    @Override
    public void onEnablePrevButton() {
        mPrevButton.setEnabled(true);
    }

    @Override
    public void onEnableNextButton() {
        mNextButton.setEnabled(true);
    }
}
