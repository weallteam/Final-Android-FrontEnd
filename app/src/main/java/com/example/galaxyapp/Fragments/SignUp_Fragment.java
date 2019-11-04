package com.example.galaxyapp.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Interface.SignUp_Call;
import com.example.galaxyapp.API_Model.User;
import com.example.galaxyapp.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp_Fragment extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private MapView mapView;
    private MapboxMap map;
    private Button btnsubmitlocation;
    private MarkerViewManager markerViewManager;
    private MarkerView marker;
    private String Latitude = "", Longitude = "";
    private LinearLayout phase1, phase2, phase3, phase4, phase5;
    private EditText username, password, repassword, email, firtname, lastname, answer;
    private Button next1, next2, next3, next5;
    private Spinner Question;
    private String[] Question_array = {"What was your first School?","Where were you born?","What is your Grandpa name?"};

    private String user, pass, gender, emails,fname,lname,ans,ques = "";
    private Retrofit retrofit;
    private SignUp_Call signUp_call;
    private SignUpControl signUpControl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            signUpControl = (SignUpControl) context;
        } catch (ClassCastException castException) {
            // In case when the activity does not implement the interface.  this code runs
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mapbox.getInstance(getActivity(), "pk.eyJ1IjoibGVvbmd1cnVuZzAyOSIsImEiOiJjang2NHZzengwODJuNDVxdTM2NDBmdWJlIn0.CPb6da7yRq1sII4tOt7quA");
        View v = inflater.inflate(R.layout.fragment_sign_up_, container, false);
        mapView = v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnsubmitlocation = v.findViewById(R.id.btnsubmitlocation);
        phase1 = v.findViewById(R.id.signupPhase1);
        phase2 = v.findViewById(R.id.signupPhase2);
        phase3 = v.findViewById(R.id.signupPhase3);
        phase4 = v.findViewById(R.id.signUpPhase4);
        phase5 = v.findViewById(R.id.signupphase5);
        username = v.findViewById(R.id.signupUsername);
        password = v.findViewById(R.id.signupPassword);
        repassword = v.findViewById(R.id.signuprePassword);
        email = v.findViewById(R.id.signupEmail);
        firtname = v.findViewById(R.id.signupFirstname);
        lastname = v.findViewById(R.id.signupLastName);
        answer = v.findViewById(R.id.signUpAnswer);
        next1 = v.findViewById(R.id.signupbtnnext1);
        next2 = v.findViewById(R.id.signupNext2);
        next3 = v.findViewById(R.id.signupNext3);
        next5 = v.findViewById(R.id.signupbtnsignup);
        Question = v.findViewById(R.id.SignUpSecurity);
        next1.setOnClickListener(this);
        next2.setOnClickListener(this);
        next3.setOnClickListener(this);
        next5.setOnClickListener(this);
        btnsubmitlocation.setOnClickListener(this);
        ArrayAdapter adap = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,Question_array);
        Question.setAdapter(adap);
        Question.setOnItemSelectedListener(this);
        retrofit = API_CALL.getAPI_Instance().getRetrofit();
        signUp_call = retrofit.create(SignUp_Call.class);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.map = mapboxMap;

        map.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                markerViewManager = new MarkerViewManager(mapView, map);
// Toast instructing user to tap on the map
                Toast.makeText(
                        getActivity(),
                        "Navigate and Tap on Screen to Choose Your Location",
                        Toast.LENGTH_LONG
                ).show();

                map.addOnMapClickListener(SignUp_Fragment.this);
            }
        });
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Toast.makeText(getActivity(), point.toString(), Toast.LENGTH_SHORT).show();
        if(marker == null){
            View customView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.mapbox_item_search_result, null);
            customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            marker = new MarkerView(new LatLng(point.getLatitude(),point.getLongitude()), customView);
            markerViewManager.addMarker(marker);
        }else{
            View customView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.mapbox_item_search_result, null);
            customView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            markerViewManager.removeMarker(marker);
            marker = new MarkerView(new LatLng(point.getLatitude(),point.getLongitude()), customView);
            markerViewManager.addMarker(marker);

        }
        Latitude = String.valueOf(point.getLatitude());
        Longitude = String.valueOf(point.getLongitude());
        return false;
    }

    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signupbtnnext1){
            if(TextUtils.isEmpty(username.getText())){
                username.setError("Please provide username");
                return;
            }
            if(TextUtils.isEmpty(password.getText())){
                password.setError("Please provide password");
                return;
            }
            if(TextUtils.isEmpty(repassword.getText())){
                repassword.setError("Please provide repassword");
            }

            user = username.getText().toString();
            pass = password.getText().toString();

            phase1.setVisibility(View.GONE);
            phase2.setVisibility(View.VISIBLE);

        }else if (v.getId() == R.id.signupNext2){
            if(TextUtils.isEmpty(email.getText())){
                email.setError("Please provide email");
                return;
            }
            if(!isValid(email.getText().toString())){
                email.setError("Please enter valid Email");
                return;
            }
            if(TextUtils.isEmpty(firtname.getText())){
                firtname.setError("Please provide firstname");
                return;
            }
            if(TextUtils.isEmpty(lastname.getText())){
                lastname.setError("Please provide lastname");
                return;
            }
            emails = email.getText().toString();
            fname = firtname.getText().toString();
            lname = lastname.getText().toString();
            phase2.setVisibility(View.GONE);
            phase3.setVisibility(View.VISIBLE);

        }else if (v.getId() == R.id.signupNext3){
            if(TextUtils.isEmpty(answer.getText())){
                answer.setError("please provider answer");
                return;
            }
            if(ques.equals("")){
                Toast.makeText(getActivity(), "Please select a question", Toast.LENGTH_SHORT).show();
            }
            ans = answer.getText().toString();
            phase3.setVisibility(View.GONE);
            phase4.setVisibility(mapView.VISIBLE);
        }else if(v.getId() == R.id.btnsubmitlocation){
            if(Latitude.equals("") || Longitude.equals("")){
                Toast.makeText(getActivity(), "Please provider location", Toast.LENGTH_SHORT).show();
                return;
            }
            phase4.setVisibility(View.GONE);
            phase5.setVisibility(mapView.VISIBLE);
        }else if(v.getId() == R.id.signupbtnsignup) {
            User userModel = new User();
            userModel.setUsername(user);
            userModel.setPassword(pass);
            userModel.setFirstname(fname);
            userModel.setLastname(lname);
            userModel.setEmail(emails);
            userModel.setSecurity_question(ques);
            userModel.setSecurity_answer(ans);
            userModel.setLatitude(Latitude);
            userModel.setLongitude(Longitude);

            Call<Void> call = signUp_call.onSignUp(userModel);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), "Your account is created", Toast.LENGTH_SHORT).show();
                        signUpControl.OnSignUpSuccess();
                    }else{
                        Toast.makeText(getActivity(), "Could not create your account", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(), "Please Connect to internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ques = Question_array[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ques = Question_array[0];
    }

    public interface SignUpControl{
        void OnSignUpSuccess();
    }
}
