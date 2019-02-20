package com.example.android.moviecircle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.moviecircle.di.DaggerMovieComponent;
import com.example.android.moviecircle.di.MovieComponent;
import com.example.android.moviecircle.di.MovieModule;
import com.example.android.moviecircle.model.Credit;
import com.example.android.moviecircle.model.SingleMovie;
import com.example.android.moviecircle.viewmodel.MovieDetailModelFactory;
import com.example.android.moviecircle.viewmodel.MovieDetailViewModel;

import java.util.List;

import javax.inject.Inject;

public class MovieDetail extends AppCompatActivity {
    public static final String MOVIE_ID = "movie_id";
    public static final int DEFAULT_MOVIE_ID = 278;
    private int movieId = DEFAULT_MOVIE_ID;
    private Menu menu;
    private ProgressBar loadingIndicator;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageView movieImage;
    private TextView movieOverview;
    private RecyclerView movieCasts;
    private MovieCastAdapter movieCastAdapter;
    @Inject
    public MovieDetailModelFactory movieDetailModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0 ){
                    isShow = true;
                    showOption(R.id.action_favorite);
                }else if(isShow){
                    isShow = false;
                    hideOption(R.id.action_favorite);
                }

            }
        });
        toolbarLayout = findViewById(R.id.toolbar_layout);
        movieImage = findViewById(R.id.movie_image);
        loadingIndicator = findViewById(R.id.loading_indicator);
        movieOverview = findViewById(R.id.movie_overview);
        movieCasts = findViewById(R.id.movie_casts);
        movieCastAdapter = new MovieCastAdapter(null,this);
        movieCasts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        movieCasts.setAdapter(movieCastAdapter);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            if(intent != null && intent.getIntExtra(MOVIE_ID,DEFAULT_MOVIE_ID)!= DEFAULT_MOVIE_ID){
                movieId = intent.getIntExtra(MOVIE_ID,DEFAULT_MOVIE_ID);
            }
        }else{
            movieId = savedInstanceState.getInt(MOVIE_ID,DEFAULT_MOVIE_ID);
        }
        getMovieDetail(movieId);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MOVIE_ID,movieId);
        super.onSaveInstanceState(outState);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        hideOption(R.id.action_favorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMovieDetail(int movieId){
        daggerInject().inject(this);
        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this,movieDetailModelFactory)
                .get(MovieDetailViewModel.class);
        //get movie basic info
        movieDetailViewModel.getMovieLiveData(movieId).observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(@Nullable Resource resource) {
                consumeResource(resource);
            }
        });


        //get movie casts
        movieDetailViewModel.getCastsLiveData(movieId).observe(this, new Observer<Resource>() {
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
                if(resource.data instanceof SingleMovie){
                    SingleMovie singleMovie = (SingleMovie) resource.data;
                    consumeMovieBasicInfo(singleMovie);
                }else if(resource.data instanceof List<?> ){
                    //todo: check more for comments list
                    List<Credit.Cast> casts = (List<Credit.Cast>) resource.data;
                    consumeMovieCasts(casts);
                }
                break;
            case ERROR:
                loadingIndicator.setVisibility(View.INVISIBLE);
                Toast.makeText(this,resource.message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void consumeMovieBasicInfo(SingleMovie singleMovie){
        if(singleMovie!=null){
            String title = singleMovie.getTitle();
            toolbarLayout.setTitle(title);
            String backdropPath = singleMovie.getBackdropPath();
            ImageSizeHelper imageSizeHelper = new ImageSizeHelper(this);
            String sizeOnTMDB = imageSizeHelper.getMovieImageSize();
            String imagePath = getString(R.string.image_base_url)+sizeOnTMDB+backdropPath;
            Glide.with(this).load(imagePath)
                    .into(movieImage);
            String overview = singleMovie.getOverview();
            movieOverview.setText(overview);
        }
    }

    private void consumeMovieCasts(List<Credit.Cast> casts){
        if(casts!=null){
           movieCastAdapter.setCasts(casts);
        }
    }

    private MovieComponent daggerInject(){
        return DaggerMovieComponent.builder()
                .movieModule(new MovieModule(this))
                .build();
    }


}


























