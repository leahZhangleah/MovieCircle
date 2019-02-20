package com.example.android.moviecircle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.moviecircle.model.Credit;

import java.util.List;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CastViewHolder> {
    List<Credit.Cast> casts;
    Context context;

    public MovieCastAdapter(List<Credit.Cast> casts, Context context) {
        this.casts = casts;
        this.context = context;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_cast,viewGroup,false);
        return new CastViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder castViewHolder, int i) {
        Credit.Cast cast = casts.get(i);
        if(cast!=null){
            String profilePath = cast.getProfilePath();
            ImageSizeHelper imageSizeHelper = new ImageSizeHelper(context);
            String profileImageUrl = context.getString(R.string.image_base_url)
                    +imageSizeHelper.getCastProfileSize()+profilePath;
            castViewHolder.castImage.getLayoutParams().width = imageSizeHelper.getImageViewWidth();
            castViewHolder.castImage.getLayoutParams().height = imageSizeHelper.getImageViewWidth() * 3 / 2;
            Glide.with(context).load(profileImageUrl).into(castViewHolder.castImage);

            String castName = cast.getName();
            castViewHolder.castName.setText(castName);

            String castCharacter = cast.getCharacter();
            castViewHolder.castCharacter.setText(castCharacter);
        }

    }

    public void setCasts(List<Credit.Cast> casts) {
        if(this.casts==null){
            this.casts = casts;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(casts!=null){
            return casts.size();
        }
        return 0;
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView castImage;
        TextView castName,castCharacter;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImage = itemView.findViewById(R.id.cast_image);
            castName = itemView.findViewById(R.id.cast_name);
            castName.setSingleLine(false);
            castName.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
            castCharacter = itemView.findViewById(R.id.cast_character);
            castCharacter.setSingleLine(false);
            castCharacter.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        }
    }
}
