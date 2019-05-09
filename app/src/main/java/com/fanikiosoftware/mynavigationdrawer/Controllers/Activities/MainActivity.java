package com.fanikiosoftware.mynavigationdrawer.Controllers.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fanikiosoftware.mynavigationdrawer.Controllers.Fragments.NewsFragment;
import com.fanikiosoftware.mynavigationdrawer.Controllers.Fragments.ParamsFragment;
import com.fanikiosoftware.mynavigationdrawer.Controllers.Fragments.ProfileFragment;
import com.fanikiosoftware.mynavigationdrawer.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //declare fragments that will be handled by the navigation drawer
    private Fragment fragmentNews;
    private Fragment fragmentProfile;
    private Fragment fragmentParams;

    //id each fragment with an int
    public static final int FRAGMENT_NEWS = 0;
    public static final int FRAGMENT_PROFILE = 1;
    public static final int FRAGMENT_PARAMS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.showFirstFragment();
    }

    //Show first fragment when activity is created
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            //Show News Fragment
            this.showFragment(FRAGMENT_NEWS);
            //Mark menu item corresponding to NewsFragment as 'selected'
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Show fragment after user clicked on a menu item
        switch (id){
            case R.id.activity_main_drawer_news :
                this.showFragment(FRAGMENT_NEWS);
                break;
            case R.id.activity_main_drawer_profile:
                this.showFragment(FRAGMENT_PROFILE);
                break;
            case R.id.activity_main_drawer_settings:
                this.showFragment(FRAGMENT_PARAMS);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // 5 - Show fragment according an Identifier
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_NEWS :
                this.showNewsFragment();
                break;
            case FRAGMENT_PROFILE:
                this.showProfileFragment();
                break;
            case FRAGMENT_PARAMS:
                this.showParamsFragment();
                break;
            default:
                break;
        }
    }

    // 4 - Create each fragment page and show it
    private void showNewsFragment(){
        if (this.fragmentNews == null) this.fragmentNews = NewsFragment.newInstance();
        this.startTransactionFragment(this.fragmentNews);
    }

    private void showParamsFragment(){
        if (this.fragmentParams == null) this.fragmentParams = ParamsFragment.newInstance();
        this.startTransactionFragment(this.fragmentParams);
    }

    private void showProfileFragment(){
        if (this.fragmentProfile == null) this.fragmentProfile = ProfileFragment.newInstance();
        this.startTransactionFragment(this.fragmentProfile);
    }

    //**** Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
}