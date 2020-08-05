package anson.tinnews.ui.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anson.tinnews.R;
import anson.tinnews.databinding.FragmentSaveBinding;
import anson.tinnews.model.Article;
import anson.tinnews.repository.NewsRepository;
import anson.tinnews.repository.NewsViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends Fragment {
    private SaveViewModel viewModel;
    // implement the viewBinding
    private FragmentSaveBinding binding;

    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SavedNewsAdapter savedNewsAdapter = new SavedNewsAdapter();
        binding.recyclerView.setAdapter(savedNewsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NewsRepository newsRepository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(newsRepository)).get(SaveViewModel.class);
        viewModel
                .getAllSavedArticles()
                .observe(
                        getViewLifecycleOwner(),
                        savedArticles -> {
                            if (savedArticles != null) {
                                Log.d("SaveFragment", savedArticles.toString());
                                savedNewsAdapter.setArticles(savedArticles);
                            }
                        });
        savedNewsAdapter.setOnClickListener(new SavedNewsAdapter.OnClickListener() {
            @Override
            public void onClick(Article article) {

            }

            @Override
            public void unLike(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.onCancel();
    }
}
