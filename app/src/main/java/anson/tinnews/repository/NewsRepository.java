package anson.tinnews.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import anson.tinnews.TinNewsApplication;
import anson.tinnews.database.AppDatabase;
import anson.tinnews.model.Article;
import anson.tinnews.model.NewsResponse;
import anson.tinnews.network.NewsApi;
import anson.tinnews.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;
    // Integrate the Room into the MVVM Architecture Repository
    private final AppDatabase database;
    private AsyncTask asyncTask;


    public NewsRepository(Context context) {
        newsApi = RetrofitClient.newInstance(context).create(NewsApi.class);
        database = TinNewsApplication.getDatabase();

    }

    // Implement getTopHeadlines API in NewsRepository
    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    // Implement searchNews API in NewsRepository
    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> isSuccessLiveData = new MutableLiveData<>();
        asyncTask =
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try {
                            database.dao().saveArticle(article);
                        } catch (Exception e) {
                            Log.e("test", e.getMessage());
                            return false;
                        }
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean isSuccess) {
                        article.favorite = isSuccess;
                        isSuccessLiveData.setValue(isSuccess);
                    }
                }.execute();
        return isSuccessLiveData;
    }

    // get saved articles
    public LiveData<List<Article>> getAllSavedArticles() {
        return database.dao().getAllArticles();
    }

    // delete saved article
    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(
                () -> database.dao().deleteArticle(article));
    }


    public void onCancel() {
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }


}
