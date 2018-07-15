package com.example.android.bakingapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.Model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * Created by SG on 7/14/2018.
 */

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepssFragment extends Fragment implements ExoPlayer.EventListener {

    private PlayerView playerView;


    private SimpleExoPlayer player;

    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private DebugTextViewHelper debugViewHelper;
    private TrackGroupArray lastSeenTrackGroupArray;

    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;

    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

ImageView ThumbNailForVideo;



    private int type;
    private Step step;
    public void setType(int t){type = t;}
    public void setStep(Step r){step = r;}
String url="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_steps_item, container, false);

ThumbNailForVideo = rootView.findViewById(R.id.ThumbNailForVideo);
playerView = rootView.findViewById(R.id.playerView);



        if(step==null) {
            Intent i = getActivity().getIntent();
            step = (Step) i.getSerializableExtra("Step");
        }


        if (savedInstanceState!=null)
        {
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
url = savedInstanceState.getString("URL");
                Log.v("PlayBack Pos ",playbackPosition+"");
                step =(Step) savedInstanceState.getSerializable("Step");
        }










        if (!step.getVideoURL().isEmpty())
            url = step.getVideoURL();
     //   else if (!step.getThumbnailURL().isEmpty())
    //        url = step.getThumbnailURL();






        if (!TextUtils.isEmpty(step.getThumbnailURL()))
        {
            Glide.with(getContext())
                    .load(step.getThumbnailURL())
                    .thumbnail(Glide.with(getContext()).load(step.getThumbnailURL()))
                    .into(ThumbNailForVideo);

           playerView.setVisibility(View.GONE);
        }
        else
        {
            ThumbNailForVideo.setVisibility(View.GONE);
           playerView.setVisibility(View.VISIBLE);
        }



        if (url.isEmpty())
            playerView.setVisibility(View.GONE);


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && (playerView.getVisibility()==View.VISIBLE) )
            hideSystemUi();




     TextView description =  (TextView)rootView.findViewById(R.id.stepdescription);
     description.setText(step.getDescription());
        initializeMedia();
        initializePlayer();

        return rootView;


    }

    public StepssFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("playWhenReady", playWhenReady);
        outState.putLong("playbackPosition", playbackPosition);
        outState.putSerializable("Step",step);
        outState.putString("URL", url);

    }
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    player.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    player.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

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

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            player.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            player.seekTo(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }


    @Override
    public void onPause() {
        //releasing in Pause and saving current position for resuming
        super.onPause();
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            //getting play when ready so that player can be properly store state on rotation
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            //resuming properly
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(playbackPosition);
        } else {
            //Correctly initialize and play properly fromm seekTo function
            initializeMedia();
            initializePlayer();
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(playbackPosition);
        }
    }


    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    private void initializePlayer() {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance
                    (getActivity(), trackSelector, loadControl);
            playerView.setPlayer(player);
            player.addListener(this);
            String userAgent = Util.getUserAgent(getActivity(),"bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }





    private void initializeMedia() {
        mMediaSession = new MediaSessionCompat(getActivity(), StepssFragment.class.getSimpleName());
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }






}
