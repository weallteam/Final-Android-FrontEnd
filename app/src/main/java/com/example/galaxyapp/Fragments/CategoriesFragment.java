package com.example.galaxyapp.Fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Interface.Category_call;
import com.example.galaxyapp.API_Model.Categories;
import com.example.galaxyapp.R;
import com.example.galaxyapp.RecyclerViewAdapter.Category_Adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    private RecyclerView catview;
    private Retrofit retrofit;
    private Category_call category_call;
    private  OnCategorySearch onCategorySearch;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onCategorySearch = (OnCategorySearch) context;
        } catch (ClassCastException castException) {
            // In case when the activity does not implement the interface.  this code runs
        }
    }

    public CategoriesFragment() {
        retrofit = API_CALL.getAPI_Instance().getRetrofit();
        category_call = retrofit.create(Category_call.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categories, container, false);
        catview = v.findViewById(R.id.viewSubtopRecycler);
        viewAllCategories();
        return v;
    }

    private void viewAllCategories(){
        Call<List<Categories>> viewCat = category_call.getAllCategories();
        viewCat.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if(response.isSuccessful()){

                    List<Categories> data = response.body();
                    for(Categories cat : data){
                        System.out.println(cat.getTitle());
                    }
                    Category_Adapter category_adapter = new Category_Adapter(getContext(), data,CategoriesFragment.this);
                    catview.setAdapter(category_adapter);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    catview.setLayoutManager(mLayoutManager);
                }else{
                    Toast.makeText(getActivity(), "This is not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(getActivity(), "Please ensure Internet Connection and try again...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OnCat(String text){
        onCategorySearch.onCategorySearchClicked(text);
    }

    public interface OnCategorySearch{
        void onCategorySearchClicked(String text);
    }

}
