package com.tool.util.glide;

import com.bumptech.glide.Glide;

import cn.finalteam.galleryfinal.PauseOnScrollListener;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GlidePauseOnScrollListener extends PauseOnScrollListener {
    public GlidePauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
        super(pauseOnScroll, pauseOnFling);
    }

    @Override
    public void resume() {
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void pause() {
        Glide.with(getActivity()).pauseRequests();
    }
}
