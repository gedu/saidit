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

import com.gemapps.saidit.busitem.BusBridge;
import com.gemapps.saidit.busitem.PaginationStateBridge;
import com.gemapps.saidit.networking.RedditListingManager;
import com.gemapps.saidit.networking.RedditPresenter;
import com.gemapps.saidit.ui.paginator.PaginationManager;
import com.gemapps.saidit.ui.toplisting.presenter.ActivityContract;
import com.gemapps.saidit.ui.toplisting.presenter.ActivityPresenter;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by edu on 3/22/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TopListingActivityTest {

    ArgumentCaptor<BusBridge> mBusCaptor = ArgumentCaptor.forClass(BusBridge.class);

    private ActivityContract.OnInteractionListener mPresenter;

    @Mock
    private ActivityContract.View mView;

    @Mock
    private EventBus mMockBus;

    @Before
    public void setup(){
        mPresenter = new ActivityPresenter(mView);
    }

    @Test
    public void updatePrevNextStatus_disableAndEnable(){
        RedditPresenter mockedListing = mock(RedditPresenter.class);
        when(mockedListing.isAuthenticated()).thenReturn(true);
        RedditListingManager.getInstance().setInstance(mockedListing);
        mPresenter.onEventBusSubscribe(mMockBus);

        PaginationManager.getInstance().onStart();
        verify(mMockBus, times(3)).post(mBusCaptor.capture());
        List<BusBridge> stateBridges = mBusCaptor.getAllValues();

        for (BusBridge bridge : stateBridges) {
            if(bridge.getBridgeType() == BusBridge.PAGINATION) {
                mPresenter.onPaginationStateChanged(((PaginationStateBridge)bridge).getState());
            }
        }

        verify(mView).onDisablePrevButton();
        verify(mView).onEnableNextButton();
    }
}