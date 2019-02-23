package com.example.android.moviecircle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class MovieListModelFactory extends ViewModelProvider.NewInstanceFactory {
    MovieListRepository movieListRepository;

    public MovieListModelFactory(MovieListRepository movieListRepository) {
        this.movieListRepository = movieListRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieListViewModel(movieListRepository);
    }
}

