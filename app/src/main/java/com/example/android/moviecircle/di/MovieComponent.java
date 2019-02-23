package com.example.android.moviecircle.di;

import com.example.android.moviecircle.MovieListActivity;
import com.example.android.moviecircle.moviedetail.MovieDetail;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MovieModule.class})
public interface MovieComponent {
    void inject(MovieDetail activity);
    void inject(MovieListActivity activity);
}
