package anson.tinnews.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.SwipeDecor;

import anson.tinnews.R;
import anson.tinnews.databinding.FragmentHomeBinding;
import anson.tinnews.model.Article;
import anson.tinnews.repository.NewsRepository;
import anson.tinnews.repository.NewsViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    // create a new screen
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    // forms a new screen that covers old screen
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  use the getBuilder() method to modify the default swipeView configurations.
        //  In this example we are adding 3 cards in the display.
        //  SwipeDecor class is used to adjust the visual elements of the view.
        //  Here paddingTop and relativeScale gives the perception of a card being placed in stack
        binding.swipeView
                .getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor().setPaddingTop(20)
                .setRelativeScale(0.01f));

        binding.rejectBtn.setOnClickListener(v -> binding.swipeView.doSwipe(false));
        binding.acceptBtn.setOnClickListener(v -> binding.swipeView.doSwipe(true));



        NewsRepository repository = new NewsRepository(getContext());
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository))
                .get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("HomeFragment", newsResponse.toString());
                                // Retrofit Response and add to the PlaceHolderView
                                for (Article article : newsResponse.articles) {
                                    TinNewsCard tinNewsCard = new TinNewsCard(article);
                                    binding.swipeView.addView(tinNewsCard);
                                }
                            }
                        });

    }
}
