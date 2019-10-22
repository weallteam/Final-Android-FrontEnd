package com.example.galaxyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.galaxyapp.Fragments.LoginFragment;
import com.example.galaxyapp.Fragments.SignUp_Fragment;
import com.example.galaxyapp.R;

public class Authentication_Activity extends AppCompatActivity implements LoginFragment.CreateAccount, SignUp_Fragment.SignUpControl {

    private LoginFragment loginFragment;
    private SignUp_Fragment signUp_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_layout);
        init();
        loadFragment(1);
    }

    private void init() {
        loginFragment = new LoginFragment();
        signUp_fragment = new SignUp_Fragment();
    }

    private void loadFragment(int verified) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (verified){
            case 1 :
                fragmentTransaction.replace(R.id.viewContainer, loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 2 :
                fragmentTransaction.replace(R.id.viewContainer, signUp_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

        }
    }

    @Override
    public void onCreateAccountClick() {
        loadFragment(2);
    }

    @Override
    public void onLogin() {
        startActivity(new Intent(Authentication_Activity.this,HomepageActivity.class));
    }

    @Override
    public void OnSignUpSuccess() {
        loadFragment(1);
    }
}
