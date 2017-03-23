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

package com.gemapps.saidit.ui.picturedetail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.gemapps.saidit.R;
import com.gemapps.saidit.ui.butter.ButterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PictureDetailActivity extends ButterActivity
        implements PictureDetailContract.View {

    private static final String TAG = "PictureDetailActivity";
    private static final String PIC_URL = "saidit.PIC_URL";
    private static final int UI_ANIMATION_DELAY = 200;
    private static final int STORAGE_PERMISSION_CODE = 1;

    @BindView(R.id.fullscreen_content)
    View mContentView;
    @BindView(R.id.picture_image)
    ImageView mImageView;

    private PictureDetailContract.OnInteractionListener mInteractionListener;
    private final Handler mHideHandler = new Handler();

    public static Intent getInstance(Context context, String picUrl){
        Intent intent = new Intent(context, PictureDetailActivity.class);
        intent.putExtra(PIC_URL, picUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        String url = getIntent().getStringExtra(PIC_URL);
        mInteractionListener = new PictureDetailPresenter(this);
        mInteractionListener.loadPicture(this, url);
        if(!hasStoragePermission()) requestStoragePermission();
    }

    private boolean hasStoragePermission(){
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                STORAGE_PERMISSION_CODE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.download_button_text)
    public void onDownloadClick(){
        mInteractionListener.savePictureLocally(this);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @OnClick(R.id.back_button)
    public void onBackClick(){
        super.onBackPressed();
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void setPicture(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void showSavedImageSuccess() {
        Snackbar.make(mContentView, R.string.image_save_success,
                BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void showSavedImageFail() {
        Snackbar.make(mContentView, R.string.image_save_fail,
                BaseTransientBottomBar.LENGTH_LONG)
                .setAction(R.string.try_again, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInteractionListener.savePictureLocally(v.getContext());
                    }
                })
                .show();
    }

    @Override
    public void showSavedImagePermissionDenied() {
        Snackbar.make(mContentView, R.string.image_save_permission_denied,
                BaseTransientBottomBar.LENGTH_LONG)
                .setAction(R.string.grant_permission, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestStoragePermission();
                    }
                })
                .show();
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
}
