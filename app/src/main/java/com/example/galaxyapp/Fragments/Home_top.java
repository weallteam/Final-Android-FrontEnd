package com.example.galaxyapp.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.galaxyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_top extends Fragment implements View.OnClickListener {

    Top_interface top_interface;
    private ImageButton add, search, cart, home;
    private EditText searchField;
    public Home_top() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            top_interface = (Top_interface) context;
        } catch (ClassCastException castException) {
            // In case when the activity does not implement the interface.  this code runs
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.home_top1, container, false);
        add = v.findViewById(R.id.BTNaddService);
        search = v.findViewById(R.id.BTNsearch);
        home = v.findViewById(R.id.BTNhome);
        cart = v.findViewById(R.id.BTNcart);
        searchField = v.findViewById(R.id.ETserchfield);

        add.setOnClickListener(this);
        search.setOnClickListener(this);
        home.setOnClickListener(this);
        cart.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.BTNaddService){
            top_interface.onAddServiceClicked();
        }else if (v.getId() == R.id.BTNsearch){
            if(TextUtils.isEmpty(searchField.getText())){
                searchField.setError("Please provide search text");
                return;
            }
            top_interface.onSerchServiceClicked(searchField.getText().toString());
        }else if(v.getId() == R.id.BTNhome){
            top_interface.onHomeClicked();
        }else if (v.getId() == R.id.BTNcart){
            top_interface.onBookServiceClicked();
        }
    }

    public interface Top_interface{
        void onAddServiceClicked();
        void onSerchServiceClicked(String text);
        void onHomeClicked();
        void onBookServiceClicked();
    }

}
