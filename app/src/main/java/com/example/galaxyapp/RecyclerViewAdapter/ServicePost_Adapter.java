package com.example.galaxyapp.RecyclerViewAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Model.Service;
import com.example.galaxyapp.Fragments.ViewService;
import com.example.galaxyapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ServicePost_Adapter extends RecyclerView.Adapter<ServicePost_Adapter.ServiceViewHolder> {

    private Context mContext;
    private List<Service> mList;
    private ViewService viewService;

    public ServicePost_Adapter(Context mContext, List<Service> mList, ViewService viewService) {
        this.mContext = mContext;
        this.mList = mList;
        this.viewService = viewService;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return  new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder v, int i) {
        final int id = mList.get(i).getId();
        v.postTitle.setText("Title : "+mList.get(i).getTitle());
        v.postCategories.setText("Categories :"+mList.get(i).getCategory());
        v.postGender.setText("Gender :"+mList.get(i).getGender());
        v.postDescription.setText("Description :"+mList.get(i).getDescription());
        String imageURI = API_CALL.baseUrl+"/Images/upload/service/"+mList.get(i).getImageURL();
        strictMode();
        try {
            URL url = new URL(imageURI);
            v.postImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        v.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewService.onBookService(id);
            }
        });
    }

    private void strictMode() {
        StrictMode.ThreadPolicy stict =new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(stict);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{
        private TextView postDescription, postCategories, postGender;
        private TextView postTitle;
        private ImageView postImage;
        private Button book;
        public ServiceViewHolder(@NonNull View v) {
            super(v);
            postDescription = v.findViewById(R.id.tvDescription);
            postCategories = v.findViewById(R.id.tvCategories);
            postGender = v.findViewById(R.id.tvGender);
            postTitle = v.findViewById(R.id.postTitle);
            postImage = v.findViewById(R.id.postImage);
            book = v.findViewById(R.id.btnbooktheService);
        }
    }
}
