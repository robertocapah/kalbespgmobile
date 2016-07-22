package com.kalbenutritionals.app.kalbespgmobile;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bl.tUserLoginBL;
import library.salesforce.common.tUserLoginData;

public class MainMenu extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView tvUsername, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        tUserLoginData dt=new tUserLoginBL().getUserActive();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);

        tvUsername = (TextView) vwHeader.findViewById(R.id.username);
        tvEmail = (TextView) vwHeader.findViewById(R.id.email);
        tvUsername.setText("Welcome, " + dt.get_txtName());
        tvEmail.setText(dt.get_TxtEmail());

        Menu header = navigationView.getMenu();
//        header.add(Menu.NONE, 0, 0, "Reso");
//        header.add(Menu.NONE, 1, 1, "Activity");
//        header.add(Menu.NONE, 2, 2, "Customer Base");
//
//        MenuItem menuItemReso = header.findItem(0);
//        MenuItem menuItemActivity = header.findItem(1);
//        final MenuItem menuCustomerBase = header.findItem(2);
//
//        menuItemReso.setIcon(R.drawable.ic_send_black);
//        menuItemReso.setChecked(false);
//        menuItemActivity.setIcon(R.drawable.ic_home_black);
//        menuItemActivity.setChecked(false);
//        menuCustomerBase.setIcon(R.drawable.ic_star_black);
//        menuCustomerBase.setChecked(false);


//Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.add_reso:
                        Toast.makeText(getApplicationContext(),"Add reso",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle(menuItem.getTitle().toString() + " Reso");
                        Reso fragment = new Reso();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,fragment);
                        fragmentTransaction.commit();

                        return true;
                    case R.id.view_reso:
                        Toast.makeText(getApplicationContext(),"View reso",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle(menuItem.getTitle().toString() + " Reso");
//                        tv.setText(menuItem.getTitle().toString() + " Reso");

                        return true;
                    case R.id.add_activity:
                        Toast.makeText(getApplicationContext(),"Add activity",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle(menuItem.getTitle().toString() + " Activity");
//                        tv.setText(menuItem.getTitle().toString() + " Activity");

                        return true;
                    case R.id.view_activity:
                        Toast.makeText(getApplicationContext(),"View activity",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle(menuItem.getTitle().toString() + " Activity");
//                        tv.setText(menuItem.getTitle().toString() + " Activity");

                        return true;
                    case R.id.add_customerbase:
                        toolbar.setTitle(menuItem.getTitle().toString() + " customer base");
                        CustomerBaseFragment fragment2 = new CustomerBaseFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame,fragment2);
                        fragmentTransaction2.commit();

                        return true;
                    case R.id.view_customerbase:
                        toolbar.setTitle(menuItem.getTitle().toString() + " Customer Base");
                        ViewCustomerBaseFragment fragment3 = new ViewCustomerBaseFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.frame,fragment3);
                        fragmentTransaction3.commit();

                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
