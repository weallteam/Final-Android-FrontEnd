package com.example.galaxyapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Interface.Book_call;
import com.example.galaxyapp.API_Interface.Service_call;
import com.example.galaxyapp.API_Model.Book;
import com.example.galaxyapp.API_Model.Categories;
import com.example.galaxyapp.API_Model.Service;
import com.example.galaxyapp.R;
import com.example.galaxyapp.RecyclerViewAdapter.Category_Adapter;
import com.example.galaxyapp.RecyclerViewAdapter.ServicePost_Adapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewService extends Fragment {
    private RecyclerView recycleservicelayouot;
    private Retrofit retrofit;
    private Service_call service_call;
    private Book_call book_call;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.recycleservicelayout,container,false);
       recycleservicelayouot= v.findViewById(R.id.recycleservicelayouot);
       retrofit = API_CALL.getAPI_Instance().getRetrofit();
       service_call = retrofit.create(Service_call.class);
       book_call = retrofit.create(Book_call.class);
       DefaultHome();
       return v;
    }

    public void DefaultHome(){
        Call<List<Service>> call = service_call.getService();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Got Service Data", Toast.LENGTH_SHORT).show();
                    List<Service> data = response.body();
                    ServicePost_Adapter adapter = new ServicePost_Adapter(getContext(), data, ViewService.this);
                    recycleservicelayouot.setAdapter(adapter);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycleservicelayouot.setLayoutManager(mLayoutManager);
                }else{
                    Toast.makeText(getActivity(), "This is not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });
    }

    public void onSearchServiceBytitle(String title){
        Service service = new Service();
        service.setTitle(title);
        Call<List<Service>> call = service_call.searchSearchBytitle(service);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Got Service Data", Toast.LENGTH_SHORT).show();
                    List<Service> data = response.body();
                    ServicePost_Adapter adapter = new ServicePost_Adapter(getContext(), data, ViewService.this);
                    recycleservicelayouot.setAdapter(adapter);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycleservicelayouot.setLayoutManager(mLayoutManager);
                }else{
                    Toast.makeText(getActivity(), "This is not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });
    }

    public void onSearchServiceByCategory(String category){
        Service service = new Service();
        service.setCategory(category);
        Call<List<Service>> call = service_call.searchServiceonCategory(service);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Got Service Data", Toast.LENGTH_SHORT).show();
                    List<Service> data = response.body();
                    ServicePost_Adapter adapter = new ServicePost_Adapter(getContext(), data,ViewService.this);
                    recycleservicelayouot.setAdapter(adapter);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycleservicelayouot.setLayoutManager(mLayoutManager);
                }else{
                    Toast.makeText(getActivity(), "This is not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });
    }

    public void onBookService(int serviceID){
        Book book = new Book();
        book.setUserID(1);
        book.setServiceID(serviceID);
        book.setStatus(1);
        Call<Void> call = book_call.addBook(book);
        try {
            call.execute();
            Toast.makeText(getActivity(), "Booked", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
