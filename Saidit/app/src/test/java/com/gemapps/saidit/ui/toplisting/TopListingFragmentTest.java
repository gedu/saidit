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

import com.gemapps.saidit.ui.toplisting.presenter.FragmentContract;
import com.gemapps.saidit.ui.toplisting.presenter.FragmentPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by edu on 3/22/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TopListingFragmentTest {

    private FragmentContract.OnInteractionListener mPresenter;

    @Mock
    private FragmentContract.View mView;

    @Before
    public void setup(){
        mPresenter = new FragmentPresenter(mView);
    }

    @Test
    public void succeedListingPopulation_hideEmptyView(){
        TopListingAdapter mockAdapter = getMockAdapter();
        when(mockAdapter.getItemCount()).thenReturn(1);

        mPresenter.addAdapter(mockAdapter);
        mPresenter.updateListingView();

        verify(mView).hideEmptyView();
    }

    @Test
    public void succeedListingPopulation_showEmptyView(){
        TopListingAdapter mockAdapter = getMockAdapter();

        mPresenter.addAdapter(mockAdapter);
        mPresenter.updateListingView();

        verify(mView).showEmptyView();
    }

    private TopListingAdapter getMockAdapter(){
        return mock(TopListingAdapter.class);
    }
}