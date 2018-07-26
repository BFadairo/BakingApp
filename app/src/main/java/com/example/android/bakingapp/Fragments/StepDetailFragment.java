package com.example.android.bakingapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.example.android.bakingapp.Fragments.StepFragment.STEP_EXTRAS;

public class StepDetailFragment extends Fragment {

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private TextView stepDescription;
    private Step step;
    private Bundle passedArgs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);

        stepDescription = rootView.findViewById(R.id.step_detail_description);

        passedArgs = getArguments();

        step = passedArgs.getParcelable(STEP_EXTRAS);

        populateUI();

        return rootView;
    }

    public void populateUI() {
        //Retrieve the Video url from the Step object and initialize player with it
        initializePlayer(Uri.parse(step.getStepUrl()));

        stepDescription.setText(step.getStepDescription());
    }

    public void initializePlayer(Uri mediaLink) {
        if (exoPlayer == null && mediaLink != null) {
            Handler mainHandler = new Handler();
            //Create an Instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector(mainHandler);
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

            //Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), getResources().getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaLink, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), mainHandler, null);


            exoPlayer.prepare(mediaSource);
            simpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            releasePlayer();
        }
    }

}
