package com.example.galaxyapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Interface.Login_Call;
import com.example.galaxyapp.API_Model.LoginResponse;
import com.example.galaxyapp.API_Model.User;
import com.example.galaxyapp.Activity.HomepageActivity;
import com.example.galaxyapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private CreateAccount createAccount;
    private EditText username, password;
    private Button login, signup;
    private Retrofit retrofit;
    private Login_Call login_call;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            createAccount = (CreateAccount) context;
        } catch (ClassCastException castException) {
            // In case when the activity does not implement the interface.  this code runs
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        username = v.findViewById(R.id.signupUsername);
        password = v.findViewById(R.id.signuppassword);
        login = v.findViewById(R.id.signupnext1);
        signup = v.findViewById(R.id.loginbtnCreateAccount);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        retrofit = API_CALL.getAPI_Instance().getRetrofit();
        login_call = retrofit.create(Login_Call.class);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signupnext1){
            if(TextUtils.isEmpty(username.getText())){
                username.setError("Please provide Username");
                return;
            }if(TextUtils.isEmpty(password.getText())){
                password.setError("Please provide Username");
                return;
            }
            User user = new User();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            onLogin(user);

        }else if (v.getId() == R.id.loginbtnCreateAccount){
            createAccount.onCreateAccountClick();
        }
    }

    private void onLogin(User user) {
        Call<LoginResponse> call = login_call.onLogin(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Successfully logged In", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), HomepageActivity.class));
                    createAccount.onLogin();
                }else{
                    Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    public interface CreateAccount{
        void onCreateAccountClick();
        void onLogin();
    }

}
