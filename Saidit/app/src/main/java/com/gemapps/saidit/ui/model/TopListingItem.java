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

import com.google.gson.annotations.SerializedName;

/**
 * Created by edu on 3/21/17.
 */
public class TopListingItem {

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

    public TopListingItem() {
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

    public boolean isThumbnailValid(){
        return mThumbnail != null && mThumbnail.length() > 0;
    }
}
