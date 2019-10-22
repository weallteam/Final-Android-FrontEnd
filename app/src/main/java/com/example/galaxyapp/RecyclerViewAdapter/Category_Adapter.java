package com.example.galaxyapp.RecyclerViewAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Model.Categories;
import com.example.galaxyapp.Fragments.CategoriesFragment;
import com.example.galaxyapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.Category_viewhold> {

    private Context mContext;
    private List<Categories> mList;
    private CategoriesFragment categoriesFragment;
    public Category_Adapter(Context mContext, List<Categories> list, CategoriesFragment categoriesFragment) {
        this.mContext = mContext;
        mList = list;
        this.categoriesFragment = categoriesFragment;
    }

    @NonNull
    @Override
    public Category_viewhold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false);
        return  new Category_viewhold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_viewhold holder, int i) {
        final String titles = mList.get(i).getTitle();
        Categories item = mList.get(i);
        String imageURI = API_CALL.baseUrl+"/Images/upload/category/"+mList.get(i).getUrl();
        strictMode();
        try {
            URL url = new URL(imageURI);
            holder.CategoriesImageView.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.CategoriesNameView.setText(item.getTitle());
        System.out.println(item.getTitle());
        holder.CategoriesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriesFragment.OnCat(titles);
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

    public class Category_viewhold extends RecyclerView.ViewHolder{
        private ImageButton CategoriesImageView;
        private TextView CategoriesNameView;

        public Category_viewhold(@NonNull View v) {
            super(v);
        CategoriesImageView = v.findViewById(R.id.CategoriesImageView);
        CategoriesNameView = v.findViewById(R.id.CategoriesNameView);

        }
    }

    public interface OnCategoriesClicked{
        void onCategoryClciked(String category);
    }
}
