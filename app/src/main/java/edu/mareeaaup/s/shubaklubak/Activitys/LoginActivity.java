package edu.mareeaaup.s.shubaklubak.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import edu.mareeaaup.s.shubaklubak.Models.MyBounceInterpolator;
import edu.mareeaaup.s.shubaklubak.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                FirebaseUser users = firebaseAuth.getCurrentUser();
                if (users != null) {
                    startActivity(intent);
                }
            }
        };// end listener

        Button logIn = findViewById(R.id.btn_login);
        logIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //animate button
                Button button = (Button) findViewById(R.id.btn_login);
                final Animation myAnim = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bounce);
                // Use bounce interpolator with amplitude 0.2 and frequency 20
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                button.startAnimation(myAnim);

                //create login activity
                mFirebaseAuth = FirebaseAuth.getInstance();
                mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        FirebaseUser users = firebaseAuth.getCurrentUser();
                        if (users != null) {
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "User signed in", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setAvailableProviders(providers)
                                            .setIsSmartLockEnabled(false)
                                            .setLogo(R.drawable.logo)
                                            .build(),
                                    RC_SIGN_IN
                            );
                        }
                    }
                };
                mFirebaseAuth.addAuthStateListener(mAuthStateListener);
            }
        });


    }//end onCreat


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
}

