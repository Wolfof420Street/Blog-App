package com.wolf.blogapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wolf.blogapp.Model.Blog;
import com.wolf.blogapp.R;

import java.util.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public BlogRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view, context );
    }

    @Override
    public void onBindViewHolder(@NonNull BlogRecyclerAdapter.ViewHolder holder, int position) {

        Blog blog = blogList.get(position);
        String imageUrl = null;

        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDesc());


        java.text.DateFormat dateFormat= java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())));

        holder.timeStamp.setText(formattedDate);

        imageUrl = blog.getImage();
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView desc;
        public TextView title;
        public TextView timeStamp;
        public ImageView imageView;
        String userId;
        public ViewHolder(View view, Context ctx) {


            super(view);
            userId = null;
            context = ctx;
            title = view.findViewById(R.id.postTitleList);
            desc = view.findViewById(R.id.postTextList);
            timeStamp= view.findViewById(R.id.timeStampList);
            imageView = view.findViewById(R.id.postImageView);
        }
    }
}
