package anson.tinnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import anson.tinnews.model.Article;
import anson.tinnews.model.NewsResponse;
import anson.tinnews.repository.NewsRepository;

public class HomeViewModel extends ViewModel {

    private final NewsRepository newsRepository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();
    // receive favorite Article
    private final MutableLiveData<Article> favoriteArticleInput = new MutableLiveData<>();


    public HomeViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    public void setFavoriteArticleInput(Article article) {
        favoriteArticleInput.setValue(article);
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(countryInput, newsRepository::getTopHeadlines);
    }

    public LiveData<Boolean> onFavorite() {
        return Transformations.switchMap(favoriteArticleInput, newsRepository::favoriteArticle);
    }

    public void onCancel() {
        newsRepository.onCancel();
    }

}
