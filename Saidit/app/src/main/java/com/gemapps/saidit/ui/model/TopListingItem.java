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

package com.gemapps.saidit.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edu on 3/21/17.
 */
public class TopListingItem implements Parcelable {

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("name")
    private String mName;

    @SerializedName("thumbnail")
    private String mThumbnail;

    @SerializedName("created")
    private long mCreated;

    @SerializedName("num_comments")
    private String mNumComments;

    @SerializedName("title")
    private String mTitle;

    private transient String mPictureUrl;

    public TopListingItem() {
    }

    public TopListingItem(Parcel in){

        mAuthor = in.readString();
        mName = in.readString();
        mThumbnail = in.readString();
        mCreated = in.readLong();
        mNumComments = in.readString();
        mTitle = in.readString();
        mPictureUrl = in.readString();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public long getCreated() {
        return mCreated;
    }

    public void setCreated(long created) {
        mCreated = created;
    }

    public String getNumComments() {
        return mNumComments;
    }

    public void setNumComments(String numComments) {
        mNumComments = numComments;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public boolean isPictureValid(){
        return mPictureUrl != null && mPictureUrl.length() > 0;
    }

    public boolean isThumbnailValid(){
        return mThumbnail != null && mThumbnail.length() > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mAuthor);
        dest.writeString(mName);
        dest.writeString(mThumbnail);
        dest.writeLong(mCreated);
        dest.writeString(mNumComments);
        dest.writeString(mTitle);
        dest.writeString(mPictureUrl);
    }

    public static final Creator<TopListingItem> CREATOR = new Creator<TopListingItem>() {
        public TopListingItem createFromParcel(Parcel in) {
            return new TopListingItem(in);
        }

        public TopListingItem[] newArray(int size) {
            return new TopListingItem[size];
        }
    };
}
