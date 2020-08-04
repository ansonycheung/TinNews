package anson.tinnews.ui.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import anson.tinnews.R;
import anson.tinnews.model.Article;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    private List<Article> articles = new LinkedList<>();

    // Add setArticles for setting new datas
    public void setArticles(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    // Implement the onCreateViewHolder: The RecyclerView creates only as many view holders as
    // are needed to display the on-screen portion of the dynamic content by onCreateViewHolder
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        SearchNewsViewHolder viewHolder = new SearchNewsViewHolder(view);

        // for test
        Log.d("Test", "onCreateViewHolder" + viewHolder.toString());

        return new SearchNewsViewHolder(view);
    }

    @Override
    // Finish the data render in the onBindViewHolder
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        // for test
        Log.d("Test", "onBindViewHolder" + holder.toString());

        Article article = articles.get(position);
        holder.title.setText(article.title);
        Picasso.get().load(article.urlToImage).into(holder.newsImage);
        holder.favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        ImageView favorite;
        TextView title;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.image);
            favorite = itemView.findViewById(R.id.favorite);
            title = itemView.findViewById(R.id.title);
        }
    }
}
