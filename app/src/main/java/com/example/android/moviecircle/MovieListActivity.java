package com.example.android.moviecircle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.moviecircle.di.DaggerMovieComponent;
import com.example.android.moviecircle.di.MovieComponent;
import com.example.android.moviecircle.di.MovieModule;
import com.example.android.moviecircle.moviedetail.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieListActivity extends AppCompatActivity {
    RecyclerView movieList;
    MovieListAdapter movieListAdapter;
    ProgressBar loadingIndicator;

    @Inject
    MovieListModelFactory movieListModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieList = findViewById(R.id.movie_list);
        movieList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        List<PopularMovie> popularMovieList = new ArrayList<>();
        /*
        popularMovieList.add(new PopularMovie("/xRWht48C2V8XNfzvPehyClOvDni.jpg","Alita: Battle Angel",6.8,399579));
        popularMovieList.add(new PopularMovie("/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg","Spider-Man: Into the Spider-Verse",8.5,324857));
        */
        movieListAdapter = new MovieListAdapter(null,this);
        movieList.setAdapter(movieListAdapter);

        loadingIndicator = findViewById(R.id.movie_list_loading_indicator);

        getPopularMovieList();
    }

    private void getPopularMovieList() {
        String movieType = "popular";
        daggerInject().inject(this);
        MovieListViewModel movieListViewModel = ViewModelProviders.of(this,movieListModelFactory)
                .get(MovieListViewModel.class);
        movieListViewModel.getMovieListLiveData(movieType).observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(@Nullable Resource resource) {
                consumeResource(resource);
            }
        });
    }

    private void consumeResource(Resource resource) {
        switch (resource.status){
            case LOADING:
                loadingIndicator.setVisibility(View.VISIBLE);
                Toast.makeText(this,"data is loading", Toast.LENGTH_SHORT).show();
                break;
            case SUCCESS:
                loadingIndicator.setVisibility(View.INVISIBLE);
                if(resource.data instanceof PopularMovie){
                    PopularMovie popularMovie = (PopularMovie) resource.data;
                    List<PopularMovie.Result> results = popularMovie.getResults();
                    consumePopularMovie(results);
                }
                /*
                else if(resource.data instanceof List<?> ){
                    //todo: check more for comments list
                    List<Credits.Cast> casts = (List<Credits.Cast>) resource.data;
                    consumeMovieCasts(casts);
                }*/
                break;
            case ERROR:
                loadingIndicator.setVisibility(View.INVISIBLE);
                Toast.makeText(this,resource.message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void consumePopularMovie(List<PopularMovie.Result> results) {
        if(results!=null){
            movieListAdapter.setResults(results);
        }
    }


    private MovieComponent daggerInject(){
        return DaggerMovieComponent.builder()
                .movieModule(new MovieModule(this))
                .build();
    }

}
