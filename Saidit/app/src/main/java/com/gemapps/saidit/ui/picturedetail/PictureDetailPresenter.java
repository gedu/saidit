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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.gemapps.saidit.R;
import com.gemapps.saidit.util.ImageUtil;
import com.gemapps.saidit.util.PicassoUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by edu on 3/23/17.
 */

public class PictureDetailPresenter implements PictureDetailContract.OnInteractionListener {

    private static final String TAG = "PictureDetailPresenter";
    private PictureDetailContract.View mView;
    private Bitmap mCurrentPic;

    public PictureDetailPresenter(PictureDetailContract.View view) {
        mView = view;
    }

    @Override
    public void loadPicture(Context context, String url) {
        PicassoUtil.loadUrlImage(context, url, new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mCurrentPic = bitmap;
                mView.setPicture(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed: ");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        }).error(R.drawable.ic_warning_white_48px);
    }

    @Override
    public void savePictureLocally(Context context) {
        boolean success = ImageUtil.saveImage(context, mCurrentPic);
        if (success) mView.showSavedImageSuccess();
        else mView.showSavedImageFail();
    }
}
