package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;
import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.TARGET_FRAME;
import static com.example.android.bakingapp.Fragments.StepFragment.STEP_EXTRAS;
import static com.example.android.bakingapp.MainActivity.RECIPE_EXTRAS;
import static com.example.android.bakingapp.MainActivity.mTwoPane;
import static com.example.android.bakingapp.StepActivity.LIST_EXTRAS;

public class StepDetailFragment extends Fragment {

    private static final String LOG_TAG = StepDetailFragment.class.getSimpleName();
    private SimpleExoPlayer exoPlayer;
    private static boolean exoFullscreen;
    private TextView stepDescription;
    private PlayerView simpleExoPlayerView;
    private final String EXOPLAYER_BOOLEAN = "exoplayer_boolean";
    private Step step;
    private long exoPlayerPosition;
    private ImageView fullscreenIcon, thumbnailView;

    private final String EXOPLAYER_EXTRA = "exoplayer_extras";
    private Boolean exoPlayerState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);

        stepDescription = rootView.findViewById(R.id.step_detail_description);

        thumbnailView = rootView.findViewById(R.id.thumbnail_image);

        Bundle passedArgs = getArguments();

        ArrayList<Step> stepList = passedArgs.getParcelableArrayList(LIST_EXTRAS);
        Recipe recipe = passedArgs.getParcelable(RECIPE_EXTRAS);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(STEP_EXTRAS);
            //Used to restore the exoPlayer to it's before-rotated position
            exoPlayerPosition = savedInstanceState.getLong(EXOPLAYER_EXTRA);
            exoPlayerState = savedInstanceState.getBoolean(EXOPLAYER_BOOLEAN);
        }

        if (!mTwoPane) {
            step = passedArgs.getParcelable(STEP_EXTRAS);
            getActivity().setTitle(recipe.getRecipeName());
            populateUI(getContext());
            initializeFullScreenButton();
        }

        return rootView;
    }

    private void populateUI(Context context) {
        //Retrieve the Video url from the Step object and initialize player with it
        if (step.getStepUrl().isEmpty()) {
            //initializePlayer(context, Uri.parse(step.getStepThumbnailUrl()));
            long requestedFrame = 0;
            RequestOptions requestOptions = new RequestOptions().set(TARGET_FRAME, requestedFrame).set(FRAME_OPTION, 0);
            Glide.with(context).load(step.getStepThumbnailUrl()).apply(requestOptions).into(thumbnailView);
            thumbnailView.setVisibility(View.VISIBLE);
            simpleExoPlayerView.setVisibility(View.GONE);
            if (mTwoPane && exoPlayer != null) {
                releasePlayer();
            }
        } else {
            initializePlayer(context, Uri.parse(step.getStepUrl()));
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            thumbnailView.setVisibility(View.GONE);
        }

        if (step.getStepUrl().isEmpty() && step.getStepThumbnailUrl().isEmpty()) {
            simpleExoPlayerView.setVisibility(View.GONE);
        } else {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
        }

        //Set the Text on the description view to the step description
        stepDescription.setText(step.getStepDescription());
    }

    public void receiveStepInterface(Context context, Step currentStep) {
        step = currentStep;
        populateUI(context);
        initializeFullScreenButton();
    }

    private void initializePlayer(Context context, Uri mediaLink) {
        if (exoPlayer == null && mediaLink != null) {
            //Create an Instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

            //Prepare the MediaSource
            String userAgent = Util.getUserAgent(context, context.getResources().getString(R.string.app_name));
            //Create the DataSource
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    userAgent, null);
            //Create the Media Source
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaLink);


            exoPlayer.prepare(mediaSource);
            simpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.seekTo(exoPlayerPosition);
            if (exoPlayerState != null) {
                exoPlayer.setPlayWhenReady(exoPlayerState);
            } else {
                exoPlayer.setPlayWhenReady(true);
            }
        } else {
            releasePlayer();
            populateUI(context);
        }
    }

    private void goFullscreenTwoPane() {
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        fullscreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        if (mTwoPane) {
            FrameLayout.LayoutParams params =
                    (FrameLayout.LayoutParams) simpleExoPlayerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            fullscreenIcon.setImageResource(R.drawable.compress_white);
            simpleExoPlayerView.setLayoutParams(params);
        }
    }

    private void closeFullscreenTwoPane() {
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        fullscreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        if (mTwoPane) {
            FrameLayout.LayoutParams params =
                    (FrameLayout.LayoutParams) simpleExoPlayerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = (int) getResources().getDimension(R.dimen.exo_player_height);
            simpleExoPlayerView.setLayoutParams(params);
            fullscreenIcon.setImageResource(R.drawable.enlarge_white);
        }
    }


    private void initializeFullScreenButton() {
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        FrameLayout fullscreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullscreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        if (mTwoPane) {
            fullscreenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!exoFullscreen) {
                        goFullscreenTwoPane();
                        exoFullscreen = true;
                    } else {
                        closeFullscreenTwoPane();
                        exoFullscreen = false;
                    }
                }
            });
        } else {
            fullscreenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!exoFullscreen) {
                        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            fullscreenIcon.setImageResource(R.drawable.compress_white);
                        } else {
                            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            fullscreenIcon.setImageResource(R.drawable.enlarge_white);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            outState.putLong(EXOPLAYER_EXTRA, exoPlayer.getCurrentPosition());
            outState.putBoolean(EXOPLAYER_BOOLEAN, exoPlayer.getPlayWhenReady());
        }
        outState.putParcelable(STEP_EXTRAS, step);
    }

    //
    private void releasePlayer() {
        //Stop and release the player to avoid Memory leaks
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null && Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null && Util.SDK_INT <= 23) {
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
