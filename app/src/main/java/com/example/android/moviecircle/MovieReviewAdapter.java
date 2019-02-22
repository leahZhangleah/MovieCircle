package com.example.android.moviecircle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviecircle.model.Reviews;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.CommentViewHolder> {
    List<Reviews.Result> results;
    Context context;

    public MovieReviewAdapter(List<Reviews.Result> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_review,viewGroup,false);
        return new CommentViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Reviews.Result result = results.get(i);
        if(result!=null){
            String author = result.getAuthor();
            String content = result.getContent();
            Character firstChar = author.toUpperCase().charAt(0);

            commentViewHolder.reviewerNameFirstChar.setText(String.valueOf(firstChar));
            commentViewHolder.reviewTitle.setText("A review by "+author);
            commentViewHolder.reviewContent.setText(content);
        }

    }

    public void setResults(List<Reviews.Result> results) {
        if(this.results==null){
            this.results = results;
            notifyDataSetChanged();
        }
    }
    //todo: add "Full Cast & Crew" Button to go to cast and crew activity

    @Override
    public int getItemCount() {
        if(results!=null){
            if(results.size() >= 10){
                return 10;
            }
            return results.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewerProfileImage;
        TextView reviewerNameFirstChar,reviewTitle;
        ExpandableTextView reviewContent;
        Button readMoreBtn;
        boolean expandable = true;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            final int MAX_LINES = 4;
            reviewerProfileImage = itemView.findViewById(R.id.reviewer_profile_image);
            reviewerNameFirstChar = itemView.findViewById(R.id.reviewer_name_char);
            reviewTitle = itemView.findViewById(R.id.review_title);
            reviewContent = itemView.findViewById(R.id.review_content);
            //readMoreBtn = itemView.findViewById(R.id.read_more_btn);
            /*readMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(expandable){
                        expandable = false;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(reviewContent,"maxLines",MAX_LINES);
                        objectAnimator.setDuration(100).start();
                        readMoreBtn.setText(context.getString(R.string.read_less));
                    }else{
                        expandable = true;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(reviewContent,"maxLines",Integer.MAX_VALUE);
                        objectAnimator.setDuration(100).start();
                        readMoreBtn.setText(context.getString(R.string.read_more));
                    }
                }
            });
            reviewContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    reviewContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if(expandable){
                        expandable = false;
                        if(reviewContent.getLineCount() > MAX_LINES){
                            readMoreBtn.setVisibility(View.VISIBLE);
                            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(reviewContent,"maxLines",MAX_LINES);
                            objectAnimator.setDuration(0).start();
                        }
                    }
                }
            });*/

        }
    }

















}
