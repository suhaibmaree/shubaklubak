package edu.mareeaaup.s.shubaklubak;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.mareeaaup.s.shubaklubak.Model.Device;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();


            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new HomeFragment()).commit();
                            break;

                        case R.id.modes:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new ModesFragment()).commit();
                            break;

                        case R.id.users:
                            Toast.makeText(MainActivity.this, "Users", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.home);

            }

    }//end on create

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    public void onClick(View view) {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(MainActivity.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }


    public void add(View v) {

        EditText name = findViewById(R.id.dev_name);
        EditText state = findViewById(R.id.dev_state);
        String deviceName = name.getText().toString();
        boolean deviceState;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("devices");

        if(state.getText().toString().equals("1"))
            deviceState = Boolean.TRUE;
        else
            deviceState = Boolean.FALSE;

        Device device = new Device();
        device.setName(deviceName);
        device.setState(deviceState);
        mDatabase.child(mFirebaseAuth.getUid()).push().setValue(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }



}//end class
