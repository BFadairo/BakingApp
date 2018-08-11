package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private final List<Step> mSteps;
    private final AdapterOnClick mClickHandler;

    public StepAdapter(Context context, List<Step> steps, AdapterOnClick onClick) {
        mContext = context;
        mSteps = steps;
        mClickHandler = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View textView =
                inflater.inflate(R.layout.step_list, parent, false);


        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Step currentStep = mSteps.get(position);

        String stepFormatted = mContext.getResources().getString(R.string.step_number_format, String.valueOf(position));

        TextView stepNumberView = holder.stepNumberView;

        TextView stepDescriptionView = holder.stepDescriptionView;

        //Yellow cake seems to be missing a step below used to handle
        stepNumberView.setText(stepFormatted);

        stepDescriptionView.setText(currentStep.getStepShortDescription());

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public interface AdapterOnClick {
        void onClick(Step step);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView stepNumberView;
        final TextView stepDescriptionView;
        final View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            stepDescriptionView = itemView.findViewById(R.id.step_description);
            stepNumberView = itemView.findViewById(R.id.step_number);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = mSteps.get(adapterPosition);
            mClickHandler.onClick(step);
        }
    }
}
