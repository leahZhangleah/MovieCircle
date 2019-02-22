package com.example.android.moviecircle.internet;

import com.example.android.moviecircle.model.Credits;
import com.example.android.moviecircle.model.SingleMovie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("movie/{id}")
    Observable<SingleMovie> getMovieById(@Path("id") int movieId, @Query("api_key")
            String api_key, @Query("language") String language,@Query("append_to_response") String append_to_response);

    @GET("movie/{id}/credits")
    Observable<Credits> getCastsById(@Path("id") int movieId, @Query("api_key") String api_key);
}
