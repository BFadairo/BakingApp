package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ImageView fullscreenIcon;
    private Button nextStep, previousStep;
    private ArrayList<Step> stepList;
    private Step step;
    private Recipe recipe;
    private long exoPlayerPosition;

    private final String EXOPLAYER_EXTRA = "exoplayer_extras";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);

        stepDescription = rootView.findViewById(R.id.step_detail_description);

        Bundle passedArgs = getArguments();

        stepList = passedArgs.getParcelableArrayList(LIST_EXTRAS);
        recipe = passedArgs.getParcelable(RECIPE_EXTRAS);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(STEP_EXTRAS);
            //Used to restore the exoPlayer to it's before-rotated position
            exoPlayerPosition = savedInstanceState.getLong(EXOPLAYER_EXTRA);
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
            initializePlayer(context, Uri.parse(step.getStepThumbnailUrl()));
        } else {
            initializePlayer(context, Uri.parse(step.getStepUrl()));
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
        Log.v(LOG_TAG, currentStep + "");
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
            exoPlayer.setPlayWhenReady(true);
        } else {
            releasePlayer();
            populateUI(getContext());
        }
    }

/*    private void goFullScreenRotate() {
        final Activity activity = getActivity();
        int orientation = 0;
        Log.v(LOG_TAG, "Rotation: " + getActivity().getWindowManager().getDefaultDisplay().getRotation());
        if (activity != null) {
            orientation = activity.getWindowManager().getDefaultDisplay().getRotation();
            Log.v(LOG_TAG, "Current Orientation Value: " + orientation);
        }
        OrientationEventListener orientationEventListener = new OrientationEventListener(getContext(), SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int rotation) {
                Log.v(LOG_TAG, "Orientation new: " + rotation);
                if (rotation == 0 || rotation == 180){
                    setFullScreenLayoutParams(rotation);
                } else if (rotation == 90){
                    setFullScreenLayoutParams(rotation);
                } else if (rotation == 270){
                    setFullScreenLayoutParams(rotation);
                }
            }
            };
        orientationEventListener.enable();
        switch (orientation) {
            case 0:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setFullScreenLayoutParams(orientation);
                break;
            case 1:

                setFullScreenLayoutParams(orientation);
                break;
            case 3:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                setFullScreenLayoutParams(orientation);
                break;
        }
    }*/

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
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            outState.putLong(EXOPLAYER_EXTRA, exoPlayer.getCurrentPosition());
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
