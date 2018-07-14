package com.example.jdocter.parstagram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.jdocter.parstagram.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> mPosts;
    Context context;

    // pass in the Tweets array in the constructor
    public PostAdapter(List<Post>posts) {
        mPosts=posts;

    }

    // for each tow, inflate the layout and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;

    }

    // bind the values based on the position of the element

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // get the data according to position
        final Post post = (Post) mPosts.get(position);

        //populate the views according to this data
        holder.tvCaption.setText(post.getDescription());
        holder.btnUsername.setText(post.getUser().getUsername());
        holder.tvCaptionUser.setText(post.getUser().getUsername());
        holder.tvTimestamp.setText(post.getTimestamp());
        holder.tvLocation.setText(post.getLocationKey());
        holder.tvLikeCount.setText(post.getLikeKey() + " likes");
        String profileUrl = post.getUser().getParseFile("profilePicture").getUrl();
        String imageUrl = post.getImage().getUrl();

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ivLike.setImageResource(R.drawable.ufi_heart_active);
                post.setLikeKey();
                holder.tvLikeCount.setText(post.getLikeKey() + " likes");
            }

        });



        // load image using glide
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions().transform(new RoundedCorners(15)))
                .into(holder.ibPostImage);

        // load image using glide
        Glide.with(context)
                .load(profileUrl)
                .apply(new RequestOptions().transform(new CircleTransform(context)))
                .into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivProfileImage;
        public Button btnUsername;
        public TextView tvCaption;
        public TextView tvTimestamp;
        public ImageButton ibPostImage;
        public TextView tvLocation;
        public TextView tvLikeCount;
        public TextView tvCaptionUser;
        public ImageView ivLike;

        public ViewHolder (View itemView) {
            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivPostProfileImage);
            btnUsername = (Button) itemView.findViewById(R.id.btnPostUsername);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            ibPostImage = (ImageButton) itemView.findViewById(R.id.ibPostImage);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
            tvCaptionUser = (TextView) itemView.findViewById(R.id.tvCaptionUser);
            ivLike = (ImageView) itemView.findViewById(R.id.ivLike);
            itemView.setOnClickListener(this);

        }

        // when the user clicks on a row, show MovieDetailsActivity for the selected movie
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = mPosts.get(position);
                // create intent for the new activity
                // TODO doubleclick?
            }
        }
    }


    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }


}
