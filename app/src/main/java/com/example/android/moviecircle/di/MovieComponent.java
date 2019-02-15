package com.example.android.moviecircle.di;

import com.example.android.moviecircle.MovieDetail;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MovieModule.class})
public interface MovieComponent {
    void inject(MovieDetail activity);
}
