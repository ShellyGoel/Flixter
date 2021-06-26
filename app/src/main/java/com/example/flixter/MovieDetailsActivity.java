package com.example.flixter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPrimaryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPrimaryImage = (ImageView) findViewById(R.id.ivPrimaryImage);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        //Glide.with(this).load(movie.getPosterPath()).into(ivPrimaryImage);
        int radius = 40; // corner radius, higher value = more rounded
        int margin = 0; // crop margin, set to 0 for corners with no crop
        String imageUrl;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //then imageURL = back drop image
            imageUrl = movie.getBackdropPath();

            //placeImage = "R.drawable.flicks_backdrop_placeholder";
            //Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_backdrop_placeholder).override(200,300).into(ivPrimaryImage);
            Glide.with(this).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_backdrop_placeholder).override(200,300).into(ivPrimaryImage);


        }
        else{
            //else imageURL = poster image
            imageUrl = movie.getPosterPath();
            //placeImage = "R.drawable.flicks_movie_placeholder";
            Glide.with(this).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_movie_placeholder).override(200,300).into(ivPrimaryImage);


        }

        //String imageUrl = movie.getPosterPath();
        //Glide.with(this).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_movie_placeholder).override(200,300).into(ivPrimaryImage);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);
    }

}