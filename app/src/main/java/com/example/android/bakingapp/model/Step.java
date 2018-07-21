package com.example.android.bakingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("videoURL")
    @Expose
    public String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    public String thumbnailURL;

    /**
     * No args constructor for use in serialization
     */
    public Step() {
    }

    /**
     * @param id
     * @param shortDescription
     * @param description
     * @param videoURL
     * @param thumbnailURL
     */
    public Step(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }


    /**
     * Getter used to get the Step ID
     */
    public int getStepId() {
        return id;
    }

    /**
     * Getter used to get the short description
     */
    public String getStepShortDescription() {
        return shortDescription;
    }

    /**
     * Getter used to get the step description
     */
    public String getStepDescription() {
        return description;
    }

    /**
     * Getter used to get the URL of the step video
     */
    public String getStepUrl() {
        return videoURL;
    }

    /**
     * Getter used to get the ThumbnailUrl for the step
     */
    public String getStepThumbnailUrl() {
        return thumbnailURL;
    }
}
