package com.example.a712948.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 */
public class MovieActivity extends Fragment {
    private MovieAdapter mMovieAdapter;
    private Movie mMovie;

    private final String MOVIE_TITLE = "MOVIE_TITLE";
    private final String MOVIE_REL = "MOVIE_REL";
    private final String MOVIE_SUM = "MOVIE_SUM";
    private final String MOVIE_RATE = "MOVIE_RATE";
    private final String MOVIE_POSTER = "MOVIE_POSTER";
    final String TAG = "Tag";

    public MovieActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.data_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateMovies();
        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                Toast.makeText(view.getContext(), "you clicked "+movie.title,
                        Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), MovieDetail.class);
 //               intent.putExtra("MOVIE_TITLE", movie.title);
   //             intent.putExtra("MOVIE_SUM", movie.summary);
     //           intent.putExtra("MOVIE_RATE", movie.vote_avg);
       //         intent.putExtra("MOVIE_REL", movie.release_date);
         //       intent.putExtra("MOVIE_POSTER", movie.poster);
           //     startActivity(intent);
            }
        });
        return view;
    }

    private void updateMovies() {
        ServiceHandler dataTask = new ServiceHandler(getActivity(), mMovieAdapter);
        dataTask.execute("popularity.asc");
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

}