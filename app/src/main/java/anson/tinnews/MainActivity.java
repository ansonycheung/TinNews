package anson.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import anson.tinnews.model.NewsResponse;
import anson.tinnews.network.NewsApi;
import anson.tinnews.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom part: nav_view
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        // center part: nav_host_fragment
        navController = navHostFragment.getNavController();

        // link center to bottom
        NavigationUI.setupWithNavController(navigationView, navController);
        // update the title on the top while choosing different button on the bottom
        NavigationUI.setupActionBarWithNavController(this, navController);



    }

    @Override
    public boolean onSupportNavigateUp() {
        // back to home page
        return navController.navigateUp();
    }
}
