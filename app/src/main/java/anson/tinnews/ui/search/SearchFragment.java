package anson.tinnews.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import anson.tinnews.R;
import anson.tinnews.databinding.FragmentSearchBinding;
import anson.tinnews.repository.NewsRepository;
import anson.tinnews.repository.NewsViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    // Use the viewBinding in the SearchFragment
    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    // create a new screen
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    // forms a new screen that covers old screen
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // add SoftKeyboard Enter Listener to handle enter event
        binding.searchView.setOnEditorActionListener((v, actionId, event) -> {
            String searchText = binding.searchView.getText().toString();
            if (actionId == EditorInfo.IME_ACTION_DONE && !searchText.isEmpty()) {
                viewModel.setSearchInput(searchText);
                return true;
            } else {
                return false;
            }
        });

        // Render data with Recycle
        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        // Implement the SpanSizeLookup for different grid size
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // the first grid is double size
                return position % 3 == 0 ? 2 : 1;
            }
        });

        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(newsAdapter);


        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(SearchViewModel.class);
        viewModel
                .searchNews()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("SearchFragment", newsResponse.toString());
                                // setArticles
                                newsAdapter.setArticles(newsResponse.articles);
                            }
                        });
    }

}
