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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gemapps.saidit.R;
import com.gemapps.saidit.ui.butter.ButterViewHolder;
import com.gemapps.saidit.ui.model.TopListingItem;
import com.gemapps.saidit.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by edu on 3/22/17.
 */
public class TopListingAdapter extends RecyclerView.Adapter<TopListingAdapter.TopViewHolder> {

    private static final String TAG = "TopListingAdapter";
    public interface ListingListener {
        void onPictureClicked(TopListingItem listingItem, ImageView mPictureImage);
    }

    private final Context context;
    private List<TopListingItem> items;
    private ListingListener mListener;

    public TopListingAdapter(Context context, List<TopListingItem> items,
                             ListingListener listener) {
        this.context = context;
        this.items = items;
        this.mListener = listener;
    }

    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_item_list, parent, false);
        return new TopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {
        TopListingItem item = items.get(position);

        holder.mAuthorNameText.setText(item.getAuthor());
        holder.mCreatedDateText.setText(DateUtil.getTimeAgo(item.getCreated()));
        holder.mTitleText.setText(item.getTitle());
        holder.mCommentsCountText.setText(item.getNumComments());
        
        if(item.isThumbnailValid()){
            holder.mThumbnailImage.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(item.getThumbnail())
                    .placeholder(R.color.grey_light_54)
                    .into(holder.mThumbnailImage);
        }

        if(item.isPictureValid()){
            holder.mPictureImage.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(item.getPictureUrl())
                    .placeholder(R.color.grey_light_54)
                    .into(holder.mPictureImage);
        }
    }

    @Override
    public int getItemCount() {
        return items == null? 0 : items.size();
    }

    public class TopViewHolder extends ButterViewHolder {

        @BindView(R.id.author_name_text)
        TextView mAuthorNameText;
        @BindView(R.id.created_date_text)
        TextView mCreatedDateText;
        @BindView(R.id.title_text)
        TextView mTitleText;
        @BindView(R.id.thumbnail_image)
        ImageView mThumbnailImage;
        @BindView(R.id.picture_image)
        ImageView mPictureImage;
        @BindView(R.id.comments_count_text)
        TextView mCommentsCountText;

        public TopViewHolder(View view) {
            super(view);
        }

        @OnClick(R.id.picture_image)
        public void onPictureClick(){
            if(mListener != null)
                mListener.onPictureClicked(items.get(getAdapterPosition()), mPictureImage);
        }
    }
}