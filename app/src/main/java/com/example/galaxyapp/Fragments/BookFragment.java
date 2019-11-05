package com.example.galaxyapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Interface.Book_call;
import com.example.galaxyapp.API_Interface.Service_call;
import com.example.galaxyapp.API_Model.Book;
import com.example.galaxyapp.API_Model.Service;
import com.example.galaxyapp.R;
import com.example.galaxyapp.RecyclerViewAdapter.BookAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {

    private RecyclerView recyclerView;
    private Book_call book_call;
    private Service_call service_call;
    Service ans = null;
    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.booklayout,container,false);
        recyclerView = v.findViewById(R.id.bookrecycle);
        book_call = API_CALL.getAPI_Instance().getRetrofit().create(Book_call.class);
        service_call = API_CALL.getAPI_Instance().getRetrofit().create(Service_call.class);
        loadBook();
        return v;
    }

    public void loadBook() {
        Book book = new Book();
        book.setUserID(1);
        Call<List<Book>> call = book_call.viewBook(book);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.isSuccessful()){
                    List<Book> list = response.body();
                    BookAdapter adap = new BookAdapter(getContext(), list, BookFragment.this);
                    recyclerView.setAdapter(adap);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(mLayoutManager);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    public Service loadService(int id){
        Service service = new Service();
        service.setId(id);
        Call<Service> call = service_call.findOneService(service);
        try {
            Response<Service> response =  call.execute();
            ans = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

}
