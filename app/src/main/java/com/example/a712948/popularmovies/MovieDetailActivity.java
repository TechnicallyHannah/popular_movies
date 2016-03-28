package com.example.a712948.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import butterknife.ButterKnife;
import com.example.a712948.popularmovies.POJO.Trailers;
import com.example.a712948.popularmovies.rest.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;


public class MovieDetailActivity extends ActionBarActivity {
    private TrailerAdapter mTrailerAdapter;
    private List<Trailers> trailersList;
    private ListView mListView;
    private final String MOVIE_ID = "MOVIEID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_fragment, new MovieDetailFragment()).commit();
        }
    }

    private void getTrailers(String mMovieID) {
        RestClient.get().getTrailers(mMovieID, new Callback<Trailers>() {
            @Override
            public void success(Trailers trailers, Response response) {
                Log.i("Movie Trailer Results", trailers.getYoutube() + "");
//
//                trailer_list.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Tag", " Error : " + error);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
