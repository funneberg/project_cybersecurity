package com.example.datasikkerhetapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.datasikkerhetapp.model.Course;
import com.example.datasikkerhetapp.mysql_connection.Downloader;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String URL_GETCOURSES="http://192.168.1.10/datasikkerhet/php_test/php/getcourses.php";

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Downloader d=new Downloader(MainActivity.this, URL_GETCOURSES);
        d.execute();

        System.out.println("Starting setup :^)");

        setup(savedInstanceState);
    }

    private void setup(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.courseListBtn:
                        uncheckItem();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseListFragment()).commit();
                        break;
                    case R.id.changePwBtn:
                        uncheckItem();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChangePwFragment()).commit();
                        break;
                    case R.id.logOutBtn:
                        startActivity(new Intent(MainActivity.this, StartActivity.class));
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseListFragment()).commit();
        }
    }


    public void uncheckItem() {
        if (navView.getCheckedItem() != null) {
            MenuItem mItem = navView.getCheckedItem();
            mItem.setChecked(false);
        }
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }


    /*
    private void shareBetweenFragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CourseListFragment clf = new CourseListFragment();
        CourseFragment cf = new CourseFragment();
        ft.add(R.id.fragment_container, clf);
        ft.add(R.id.fragment_container, cf);
        ft.commit();
    }

    public void send(Course aCourse) {

    }

     */

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void showCourselist() {
        System.out.println("Bruh...");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseListFragment()).commit();
    }
}
