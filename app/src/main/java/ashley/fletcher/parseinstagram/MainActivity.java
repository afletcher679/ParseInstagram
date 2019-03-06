package ashley.fletcher.parseinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ashley.fletcher.parseinstagram.fragments.ComposeFragment;
import ashley.fletcher.parseinstagram.fragments.PostsFragment;
import ashley.fletcher.parseinstagram.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new ComposeFragment();
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment();
                      //Toast.makeText(MainActivity.this, "Home!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                       // Toast.makeText(MainActivity.this, "Compose!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                       // Toast.makeText(MainActivity.this, "Profile!",Toast.LENGTH_SHORT).show();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}
