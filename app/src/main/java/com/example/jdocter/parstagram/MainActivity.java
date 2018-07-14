package com.example.jdocter.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CreateFragment.Callback {

    final FragmentManager fragmentManager = getSupportFragmentManager();

    // define your fragments here
    final Fragment homeFragment = new HomeFragment();
    final Fragment selfFragment = new SelfFragment();
    final Fragment createFragment = new CreateFragment();

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        case R.id.home:
                            fragmentTransaction.replace(R.id.frameLayout, homeFragment).commit();
                            return true;
                        case R.id.self:
                            fragmentTransaction.replace(R.id.frameLayout, selfFragment).commit();
                            return true;
                        case R.id.create:
                            fragmentTransaction.replace(R.id.frameLayout, createFragment).commit();
                            return true;
                    }
                    return true; // TODO this supposed to be here?
                }
            }
        );

        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public void onPostCreated() {
        Log.d("HomeActivity", "onPostCreated()");

        // navigates to the home fragment (feed fragment)
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
