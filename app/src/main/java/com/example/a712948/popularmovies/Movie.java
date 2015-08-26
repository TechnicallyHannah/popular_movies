package com.example.a712948.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Hannah Paulson
 * @since 8/20/15.
 */
public class Movie implements Parcelable {

    String title;
    String summary;
    String release_date;
    String vote_avg;
    String poster;
    String movieID;
    String TAG = "TAG";

    public Movie(String movieID, String title, String summary, String release_date, String vote_avg, String poster) {
        this.movieID = movieID;
        this.title = title;
        this.summary = summary;
        this.release_date = release_date;
        this.vote_avg = vote_avg;
        this.poster = poster;
    }

    public Movie(Parcel in) {
        movieID = in.readString();
        title = in.readString();
        summary = in.readString();
        release_date = in.readString();
        vote_avg = in.readString();
        poster = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieID);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(release_date);
        dest.writeString(vote_avg);
        dest.writeString(poster);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
