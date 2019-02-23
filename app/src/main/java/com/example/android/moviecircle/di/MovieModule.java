package com.example.android.moviecircle.di;

import android.content.Context;

import com.example.android.moviecircle.MovieListModelFactory;
import com.example.android.moviecircle.MovieListRepository;
import com.example.android.moviecircle.moviedetail.MovieRepository;
import com.example.android.moviecircle.R;
import com.example.android.moviecircle.internet.NetworkInterface;
import com.example.android.moviecircle.internet.NetworkRequest;
import com.example.android.moviecircle.moviedetail.viewmodel.MovieDetailModelFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MovieModule {
    private Context context;
    private static String baseUrl;

    public MovieModule(Context context) {
        this.context = context;
        baseUrl = context.getResources().getString(R.string.base_url);
    }

    //movie list
    @Provides
    @Singleton
    public MovieListModelFactory getMovieListModelFactory(MovieListRepository movieListRepository){
        return new MovieListModelFactory(movieListRepository);
    }

    @Provides
    @Singleton
    public MovieListRepository getMovieListRepository(NetworkRequest networkRequest){
        return new MovieListRepository(networkRequest);
    }

    //movie detail
    @Provides
    @Singleton
    public MovieDetailModelFactory getMovieDetailModelFactory(MovieRepository movieRepository){
        return new MovieDetailModelFactory(movieRepository);
    }

    @Provides
    @Singleton
    public MovieRepository getMovieRepository(NetworkRequest networkRequest){
        return new MovieRepository(networkRequest);
    }

    @Provides
    @Singleton
    public NetworkRequest getNetworkRequest(NetworkInterface networkInterface){
        return new NetworkRequest(networkInterface,context);
    }

    @Provides
    @Singleton
    public NetworkInterface getNetworkInterface(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(interceptor);

        //todo: how to configure header to reduce internet request
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .addHeader("Accept","application/json")
                        .addHeader("Request-Type","Android")
                        .addHeader("Content-Type","application/json");
                Request request = builder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient okHttpClient = httpClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        NetworkInterface networkInterface= retrofit.create(NetworkInterface.class);
        return networkInterface;
    }
}
