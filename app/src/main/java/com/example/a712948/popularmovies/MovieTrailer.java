package com.example.a712948.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Hannah Paulson
 * @since 8/27/15.
 */
public class MovieTrailer implements Parcelable {
    String key;
    String trailerName;
    String type;

    public MovieTrailer(String key, String trailerName, String type) {
        this.key = key;
        this.trailerName = trailerName;
        this.type = type;
    }

    public MovieTrailer(Parcel in) {
        key = in.readString();
        trailerName = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(trailerName);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
}

