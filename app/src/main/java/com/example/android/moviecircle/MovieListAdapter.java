package com.example.android.moviecircle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.moviecircle.moviedetail.ImageSizeHelper;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    List<PopularMovie.Result> popularMovies;
    Context context;

    public MovieListAdapter(List<PopularMovie.Result> popularMovies, Context context) {
        this.popularMovies = popularMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.content_movie_list,viewGroup,false);
        return new MovieViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        PopularMovie.Result popularMovie = popularMovies.get(i);
        if(popularMovie!=null){
            String posterPath = popularMovie.getPosterPath();
            ImageSizeHelper imageSizeHelper = new ImageSizeHelper(context);
            String movieImage = context.getString(R.string.image_base_url)
                    +imageSizeHelper.getMoviePosterSize()+posterPath;
            movieViewHolder.movieImage.getLayoutParams().width = imageSizeHelper.getImageViewWidth();
            movieViewHolder.movieImage.getLayoutParams().height = imageSizeHelper.getImageViewWidth() * 3 / 2;
            Glide.with(context).load(movieImage)
                    .into(movieViewHolder.movieImage);
            movieViewHolder.movieScore.setText(String.valueOf(popularMovie.getVoteAverage()));
            movieViewHolder.movieTitle.setText(popularMovie.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if(popularMovies!=null){
            if(popularMovies.size() > 10)return 10;
            return popularMovies.size();
        }
        return 0;
    }

    public void setResults(List<PopularMovie.Result> popularMovies) {
        if(this.popularMovies==null){
            this.popularMovies = popularMovies;
            notifyDataSetChanged();
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView movieScore,movieTitle;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_list_item_image);
            movieScore = itemView.findViewById(R.id.movie_list_item_score);
            movieTitle = itemView.findViewById(R.id.movie_list_item_title);
        }
    }
}
