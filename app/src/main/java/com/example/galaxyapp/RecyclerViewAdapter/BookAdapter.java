package com.example.galaxyapp.RecyclerViewAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Model.Book;
import com.example.galaxyapp.API_Model.Service;
import com.example.galaxyapp.Fragments.BookFragment;
import com.example.galaxyapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHold> {

    private Context mContext;
    private List<Book> mList;
    private BookFragment bookFragment;


    public BookAdapter(Context mContext, List<Book> mList, BookFragment bookFragment) {
        this.mContext = mContext;
        this.mList = mList;
        this.bookFragment = bookFragment;
    }

    @NonNull
    @Override
    public BookViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book,parent,false);
        return  new BookViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHold v, int i) {
        final int id = mList.get(i).getServiceID();
        Service service = bookFragment.loadService(id);
        if(service != null){
         v.title.setText(service.getTitle());
            String imageURI = API_CALL.baseUrl+"/Images/upload/service/"+service.getImageURL();
            strictMode();
            try {
                URL url = new URL(imageURI);
                v.pic.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(mContext, "No Service Booked", Toast.LENGTH_SHORT).show();
        }
    }

    private void strictMode() {
        StrictMode.ThreadPolicy stict =new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(stict);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BookViewHold extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView pic;
        public BookViewHold(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.bookserviceTitle);
            pic = v.findViewById(R.id.bookserviceimage);
        }
    }
}
