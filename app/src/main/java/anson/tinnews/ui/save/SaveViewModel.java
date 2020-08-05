package anson.tinnews.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import anson.tinnews.model.Article;
import anson.tinnews.repository.NewsRepository;

public class SaveViewModel extends ViewModel {
    private final NewsRepository repository;

    public SaveViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    // It gets all saved articles in the Room,
    // any updates in the Article table will immediately trigger new returns.
    public LiveData<List<Article>> getAllSavedArticles() {
        return repository.getAllSavedArticles();
    }

    // It will be used to remove the Article from the database.
    public void deleteSavedArticle(Article article) {
        repository.deleteSavedArticle(article);
    }

    // Prevent memory leak
    public void onCancel() {
        repository.onCancel();
    }

}
