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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data according to position
        Post post = (Post) mPosts.get(position);

        //populate the views according to this data
        holder.tvComment.setText(post.getDescription());
        holder.btnUsername.setText(post.getUser().getUsername());
        String imageUrl = post.getImage().getUrl();

        // load image using glide
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions().transform(new RoundedCorners(15)))
                .into(holder.ibPostImage);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ivProfileImage;
        public Button btnUsername;
        public TextView tvComment;
        public ImageButton ibPostImage;

        public ViewHolder (View itemView) {
            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivPostProfileImage);
            btnUsername = (Button) itemView.findViewById(R.id.btnPostUsername);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
            ibPostImage = (ImageButton) itemView.findViewById(R.id.ibPostImage);
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

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

}
