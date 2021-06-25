package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.MovieDetailsActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;
import com.example.flixter.models.Movie;
import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
// new since Glide v4
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    //usually involves inflating a layout from XML and returning the holder
    @NonNull
    @NotNull
    @Override
    //responsible for creating individual rows needed to display movie info in recyclerView
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateHolder");
        //inflates rows, layoutInflater is used to instantiate the contents of the layout XML files into their corresponding View objects (takes XML file as input
        //and builds the View objects from it
        //R.layout.item_movie is the layout of the row
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        //creates and returns an instance of ViewHolder class, movieView is the view we just created containing the info of the row
        //
        return new ViewHolder(movieView);

    }
    //involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //get the movie at the passed in position
        Movie movie = movies.get(position);
        //bind the movie data into the view holder
        holder.bind(movie);
    }

    //returns total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }
    //optional: could have it implement the OnClickListener class to use OnClick(view)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //implements View.OnClickListener

        TextView tvTitle;
        TextView tvOverview;
        //ImageView ivPoster;

        ImageView ivPrimaryImage;

        //responsible for managing the rows and keeps track of all Views present in that row

        public ViewHolder(View itemView) {
            super(itemView);
            //all the Views in a row, itemView is the View representing the row!
            //ivPrimaryImage = (ImageView) itemView.findViewById(R.id.ivPrimaryImage);
            ivPrimaryImage = (ImageView) itemView.findViewById(R.id.ivPrimaryImage);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            //ivPoster = itemView.findViewById(R.id.ivPoster);
            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);

        }
        // when the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View v) {
            Log.d("MovieDetailsActivity", String.format("hello"));
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
        //public void onClick(View v)
        //takes the view and puts in our data into the view
        public void bind(Movie movie){
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            String placeImage;
            //glide helps with rendering the images
            int radius = 40; // corner radius, higher value = more rounded
            int margin = 0; // crop margin, set to 0 for corners with no crop
            //if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //then imageURL = back drop image
                imageUrl = movie.getBackdropPath();
                //placeImage = "R.drawable.flicks_backdrop_placeholder";
                //Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_backdrop_placeholder).override(200,300).into(ivPrimaryImage);
                Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_backdrop_placeholder).override(200,300).into(ivPrimaryImage);



            }
            else{
                //else imageURL = poster image
                imageUrl = movie.getPosterPath();
                //placeImage = "R.drawable.flicks_movie_placeholder";
                Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(R.drawable.flicks_movie_placeholder).override(200,300).into(ivPrimaryImage);


            }



        }
    }


}
