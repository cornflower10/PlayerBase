package com.kk.taurus.playerbase.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kk.taurus.playerbase.callback.BaseEventReceiver;
import com.kk.taurus.playerbase.callback.GestureObserver;
import com.kk.taurus.playerbase.callback.OnCoverEventListener;
import com.kk.taurus.playerbase.callback.OnPlayerEventListener;
import com.kk.taurus.playerbase.cover.base.BaseCover;
import com.kk.taurus.playerbase.cover.base.BaseReceiverCollections;
import com.kk.taurus.playerbase.callback.PlayerObserver;
import com.kk.taurus.playerbase.inter.IDpadFocusCover;
import com.kk.taurus.playerbase.setting.BaseAdVideo;
import com.kk.taurus.playerbase.setting.VideoData;

import java.util.List;

/**
 * Created by Taurus on 2017/3/24.
 *
 * 绑定cover集合，负责分发和中转player或者cover消息。
 *
 */

public abstract class BaseBindCover extends BaseContainer implements PlayerObserver,GestureObserver,OnCoverEventListener{

    private BaseReceiverCollections coverCollections;
    private OnCoverEventListener mOnCoverEventListener;

    public BaseBindCover(@NonNull Context context){
        super(context);
    }

    public BaseBindCover(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseBindCover(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindCoverCollections(BaseReceiverCollections coverCollections){
        if(this.coverCollections!=null)
            return;
        this.coverCollections = coverCollections;
        initCovers(mAppContext);
    }

    public void unbindCoverCollections(){
        if(coverCollections!=null){
            coverCollections.clear();
            coverCollections = null;
        }
        removeAllCovers();
        removeAllContainers();
    }

    private void initCovers(Context context) {
        if(coverCollections==null)
            return;
        List<BaseEventReceiver> covers = coverCollections.getCovers();
        for(BaseEventReceiver cover : covers){
            if(cover instanceof BaseCover){
                addCover((BaseCover) cover,null);
            }
        }
        onCoversHasInit(context);
    }

    public BaseReceiverCollections getCoverCollections(){
        return coverCollections;
    }

    protected void onCoversHasInit(Context context) {

    }

    public void setOnCoverEventListener(OnCoverEventListener onCoverEventListener){
        this.mOnCoverEventListener = onCoverEventListener;
    }

    @Override
    public void onCoverEvent(int eventCode, Bundle bundle) {
        if(mOnCoverEventListener!=null){
            mOnCoverEventListener.onCoverEvent(eventCode, bundle);
        }
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onCoverEvent(eventCode, bundle);
            }
        }
    }

    /**
     * 当cover集合中存在Dpad控制层时，将焦点控制权交给它。
     */
    public void dPadRequestFocus(){
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null && receiver instanceof IDpadFocusCover){
                receiver.onNotifyPlayEvent(OnPlayerEventListener.EVENT_CODE_PLAYER_DPAD_REQUEST_FOCUS, null);
            }
        }
    }

    public void onBindPlayer(BasePlayer player, OnCoverEventListener onCoverEventListener) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onBindPlayer(player,onCoverEventListener);
            }
        }
    }

    @Override
    public void onNotifyConfigurationChanged(Configuration newConfig) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyConfigurationChanged(newConfig);
            }
        }
    }

    @Override
    public void onNotifyPlayEvent(int eventCode, Bundle bundle) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyPlayEvent(eventCode, bundle);
            }
        }
    }

    @Override
    public void onNotifyErrorEvent(int eventCode, Bundle bundle) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyErrorEvent(eventCode, bundle);
            }
        }
    }

    @Override
    public void onNotifyPlayTimerCounter(int curr, int duration, int bufferPercentage) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyPlayTimerCounter(curr, duration, bufferPercentage);
            }
        }
    }

    @Override
    public void onNotifyNetWorkConnected(int networkType) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyNetWorkConnected(networkType);
            }
        }
    }

    @Override
    public void onNotifyNetWorkError() {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyNetWorkError();
            }
        }
    }

    @Override
    public void onNotifyAdPrepared(List<BaseAdVideo> adVideos) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyAdPrepared(adVideos);
            }
        }
    }

    @Override
    public void onNotifyAdStart(BaseAdVideo adVideo) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyAdStart(adVideo);
            }
        }
    }

    @Override
    public void onNotifyAdFinish(VideoData data, boolean isAllFinish) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver!=null){
                receiver.onNotifyAdFinish(data, isAllFinish);
            }
        }
    }

    @Override
    public void onGestureSingleTab(MotionEvent event) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureSingleTab(event);
            }
        }
    }

    @Override
    public void onGestureDoubleTab(MotionEvent event) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureDoubleTab(event);
            }
        }
    }

    @Override
    public void onGestureScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureScroll(e1, e2, distanceX, distanceY);
            }
        }
    }

    @Override
    public void onGestureHorizontalSlide(float percent) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureHorizontalSlide(percent);
            }
        }
    }

    @Override
    public void onGestureRightVerticalSlide(float percent) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureRightVerticalSlide(percent);
            }
        }
    }

    @Override
    public void onGestureLeftVerticalSlide(float percent) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureLeftVerticalSlide(percent);
            }
        }
    }

    @Override
    public void onGestureEnableChange(boolean enable) {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureEnableChange(enable);
            }
        }
    }

    @Override
    public void onGestureEnd() {
        for(BaseEventReceiver receiver:mCovers){
            if(receiver instanceof GestureObserver){
                ((GestureObserver)receiver).onGestureEnd();
            }
        }
    }


    //gesture handle----------------

    @Override
    public void onSingleTapUp(MotionEvent event) {
        super.onSingleTapUp(event);
        onGestureSingleTab(event);
    }

    @Override
    public void onDoubleTap(MotionEvent event) {
        super.onDoubleTap(event);
        onGestureDoubleTab(event);
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        super.onScroll(e1, e2, distanceX, distanceY);
        onGestureScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public void onHorizontalSlide(float percent) {
        super.onHorizontalSlide(percent);
        onGestureHorizontalSlide(percent);
    }

    @Override
    public void onRightVerticalSlide(float percent) {
        super.onRightVerticalSlide(percent);
        onGestureRightVerticalSlide(percent);
    }

    @Override
    public void onLeftVerticalSlide(float percent) {
        super.onLeftVerticalSlide(percent);
        onGestureLeftVerticalSlide(percent);
    }

    @Override
    protected void onPlayerGestureEnableChange(boolean enable) {
        super.onPlayerGestureEnableChange(enable);
        onGestureEnableChange(enable);
    }

    @Override
    protected void onEndGesture() {
        super.onEndGesture();
        onGestureEnd();
    }
}
