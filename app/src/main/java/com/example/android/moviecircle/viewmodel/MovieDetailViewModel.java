package com.example.android.moviecircle.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.moviecircle.MovieRepository;
import com.example.android.moviecircle.Resource;
import com.example.android.moviecircle.model.SingleMovie;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MovieDetailViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Resource> resourceMutableLiveData;

    public MovieDetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Resource> getMovieLiveData(int movieId){
        if(resourceMutableLiveData == null){
            resourceMutableLiveData = new MutableLiveData<>();
            fetchMovieById(movieId);
        }
        return resourceMutableLiveData;
    }

    private void fetchMovieById(int movieId){
        Disposable disposable = movieRepository.getMovieById(movieId)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        resourceMutableLiveData.setValue(Resource.loading(null));
                    }
                })
                .subscribe(new Consumer<SingleMovie>() {
                    @Override
                    public void accept(SingleMovie singleMovie) throws Exception {
                        resourceMutableLiveData.setValue(Resource.success(singleMovie));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        resourceMutableLiveData.setValue(Resource.error(throwable.getMessage(),null));
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
