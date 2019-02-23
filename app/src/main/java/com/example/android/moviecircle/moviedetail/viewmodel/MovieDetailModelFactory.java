package com.example.android.moviecircle.moviedetail.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.moviecircle.moviedetail.MovieRepository;

public class MovieDetailModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MovieRepository movieRepository;

    public MovieDetailModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(movieRepository);
    }
}
