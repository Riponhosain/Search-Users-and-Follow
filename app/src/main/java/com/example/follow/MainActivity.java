package com.example.follow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.follow.Fragment.HomeFragment;
import com.example.follow.Fragment.NotificationFragment;
import com.example.follow.Fragment.ProfileFragment;
import com.example.follow.Fragment.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Fragment selectedfragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedfragment = new HomeFragment();
                            break;


                        case R.id.nav_search:
                            selectedfragment = new SearchFragment();
                            break;

                        case R.id.nav_add:
//                            selectedfragment = null;
//                            startActivity(new Intent(MainActivity.this, postActivity.class));
                            break;

                        case R.id.nav_heart:

                            selectedfragment = new NotificationFragment();
                            break;

                        case R.id.nav_profile:

                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();

                            selectedfragment = new ProfileFragment();
                            break;
                    }

                    if (selectedfragment !=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfragment).commit();
                    }

                    return true;
                }
            };
}
