package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    private final String EXOPLAYER_EXTRA = "exoplayer_extras";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);

        stepDescription = rootView.findViewById(R.id.step_detail_description);

        nextStep = rootView.findViewById(R.id.next_step);

        previousStep = rootView.findViewById(R.id.previous_step);

        Bundle passedArgs = getArguments();

        stepList = passedArgs.getParcelableArrayList(LIST_EXTRAS);
        recipe = passedArgs.getParcelable(RECIPE_EXTRAS);

        if (savedInstanceState != null) {
            long exoplayerPosition = savedInstanceState.getLong(EXOPLAYER_EXTRA);
            exoPlayer.seekTo(exoplayerPosition);
        }

        if (!mTwoPane) {
            step = passedArgs.getParcelable(STEP_EXTRAS);
            getActivity().setTitle(step.getStepShortDescription());
            populateUI(getContext());
            initializeFullScreenButton();
            initializeButtons();
            hideButtons();
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

        //Set the Text on the description view to the step description
        stepDescription.setText(step.getStepDescription());
    }

    public void receiveStepInterface(Context context, Step currentStep) {
        step = currentStep;
        populateUI(context);
        initializeButtons();
        initializeFullScreenButton();
        hideButtons();
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
            exoPlayer.setPlayWhenReady(true);
        } else {
            releasePlayer();
            populateUI(getContext());
        }
    }

    private void goFullscreen() {
        FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) simpleExoPlayerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        simpleExoPlayerView.setLayoutParams(params);
        fullscreenIcon.setImageResource(R.drawable.compress_white);
        previousStep.setVisibility(View.GONE);
        nextStep.setVisibility(View.GONE);
    }

    private void closeFullscreen() {
        FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) simpleExoPlayerView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = (int) getResources().getDimension(R.dimen.exo_player_height);
        simpleExoPlayerView.setLayoutParams(params);
        fullscreenIcon.setImageResource(R.drawable.enlarge_white);
        nextStep.setVisibility(View.VISIBLE);
        previousStep.setVisibility(View.VISIBLE);
    }


    private void initializeFullScreenButton() {
        PlayerControlView controlView = simpleExoPlayerView.findViewById(R.id.exo_controller);
        FrameLayout fullscreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullscreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!exoFullscreen) {
                    goFullscreen();
                    exoFullscreen = true;
                } else {
                    closeFullscreen();
                    exoFullscreen = false;
                }
            }
        });
    }

    /**
     * This method is used to retrieve the previous Step from the step list
     */
    private void getPreviousStep() {
        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step.getStepId() > 0) {
                    int currentStep;
                    currentStep = step.getStepId();
                    step = stepList.get(currentStep - 1);
                    refreshFragment();
                    Log.v(LOG_TAG, step.getStepId() + "previous step");
                    Log.v(LOG_TAG, stepList.size() + "Size");
                }

                if (step.getStepId() < stepList.size() - 1 || (step.getStepId() == stepList.size() && recipe.getRecipeName().equals("Yellow Cake"))) {
                    nextStep.setVisibility(View.VISIBLE);
                }

                if (step.getStepId() == 0) {
                    previousStep.setVisibility(View.GONE);
                }
            }
        });
    }

    private void refreshFragment() {
        populateUI(getContext());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.step_detail_container, this);
    }

    /**
     * This method is used to retrieve the next Step from the list
     * Special case for Yellow Cake as the data was not in order
     */
    private void getNextStep() {
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step.getStepId() < stepList.size() - 1 || ((recipe.getRecipeName().equals("Yellow Cake")) && step.getStepId() < stepList.size())) {
                    int currentStep;
                    currentStep = step.getStepId();
                    step = stepList.get(currentStep + 1);
                    refreshFragment();
                    Log.v(LOG_TAG, step.getStepId() + "next step");
                    Log.v(LOG_TAG, stepList.size() + "Size");
                }

                if (step.getStepId() >= 1) {
                    previousStep.setVisibility(View.VISIBLE);
                }

                if (step.getStepId() == stepList.size() - 1 || (step.getStepId() == stepList.size() && recipe.getRecipeName().equals("Yellow Cake"))) {
                    nextStep.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXOPLAYER_EXTRA, exoPlayer.getCurrentPosition());
        //TODO Implement both methods to handle phone rotations
    }


    /**
     * Method used to initially hide the buttons
     */
    private void hideButtons() {
        if (step.getStepId() == 0) {
            previousStep.setVisibility(View.GONE);
        }

        if (step.getStepId() == stepList.size() - 1 && !(recipe.getRecipeName().equals("Yellow Cake"))) {
            nextStep.setVisibility(View.GONE);
        }

        if (step.getStepId() == stepList.size() && recipe.getRecipeName().equals("Yellow Cake")) {
            nextStep.setVisibility(View.GONE);
        }
    }

    /**
     * Used to initialize both buttons
     */
    private void initializeButtons() {
        //Initialize both onClickListeners for both buttons
        getNextStep();
        getPreviousStep();
    }

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
