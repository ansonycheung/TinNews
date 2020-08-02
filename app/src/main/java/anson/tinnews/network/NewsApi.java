package anson.tinnews.network;

import anson.tinnews.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
        // https://newsapi.org/docs/endpoints/top-headlines
        // https://newsapi.org/v2/top-headlines?country=us&apiKey=#yourapiKey#
    Call<NewsResponse> getTopHeadlines(@Query("country") String country);

    @GET("everything")
        // https://newsapi.org/docs/endpoints/everything
    Call<NewsResponse> getEverything(@Query("q") String query, @Query("pageSize") int pageSize);
}
