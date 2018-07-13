package com.example.android.bakingapp;

/**
 * Created by SG on 7/9/2018.
 */

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by SG on 7/9/2018.
 */
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.content.Intent;

import com.example.android.bakingapp.Model.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;


import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;


public class stepsFragment extends Fragment implements ExoPlayer.EventListener  {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean playWhenReady=true;
    private long playbackPosition=0;
    private int currentWindow=0;
    private BandwidthMeter bandwidthMeter;


    View rootView;






    private int type;
    private Step step;
    public void setType(int t){type = t;}
    public void setStep(Step r){step = r;}
public boolean ExistsVideo = false;
public String url="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_steps_item, container, false);



        if(step==null) {
            Intent i = getActivity().getIntent();
            step = (Step) i.getSerializableExtra("Step");
        }


        ExistsVideo = VideoExists(step);
        TextView stepdescription = (TextView) rootView.findViewById(R.id.stepdescription);


        stepdescription.setText(step.getDescription());



        playWhenReady = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "bakingapp"), (TransferListener<? super DataSource>) bandwidthMeter);
        window = new Timeline.Window();


if (!step.getVideoURL().isEmpty())
    url = step.getVideoURL();
        else if (!step.getThumbnailURL().isEmpty())
            url = step.getThumbnailURL();



        if (url.isEmpty())
            rootView.findViewById(R.id.playerView).setVisibility(View.GONE);



        return rootView;

    }



    private void initializePlayer() {
        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);

        player.seekTo(currentWindow, playbackPosition);


        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                mediaDataSourceFactory, extractorsFactory, null, null);



        player.prepare(mediaSource);


    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
    player.stop();
            player.release();
            player = null;
            trackSelector = null;

        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

      //  if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
        //    hideSystemUi();


    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if(ExistsVideo)
            initializePlayer();

        }



    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || player == null)) {
            { if (ExistsVideo)
                initializePlayer();

            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if(ExistsVideo)
            releasePlayer();


        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if(ExistsVideo)
            releasePlayer();

        }



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
      if (ExistsVideo)
        releasePlayer();



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ExistsVideo)
        releasePlayer();


    }



    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }



    boolean VideoExists(Step s)
    {

        if (!s.getVideoURL().isEmpty())
            return true;

        if (!s.getThumbnailURL().isEmpty())
            return true;

        return false;
    }




}