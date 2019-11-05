package com.example.galaxyapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import com.example.galaxyapp.Fragments.BookFragment;
import com.example.galaxyapp.Fragments.CategoriesFragment;
import com.example.galaxyapp.Fragments.Home_top;
import com.example.galaxyapp.Fragments.ServiceFragment;
import com.example.galaxyapp.Fragments.ViewService;
import com.example.galaxyapp.R;
import com.example.galaxyapp.RecyclerViewAdapter.Category_Adapter;

public class HomepageActivity extends AppCompatActivity implements ServiceFragment.UpdateView, Home_top.Top_interface, CategoriesFragment.OnCategorySearch {
    Home_top home_top;
    CategoriesFragment categoriesFragment;
    ServiceFragment serviceFragment;
    ViewService viewService;
    private RelativeLayout top, body,post, detail, viewDetail;
    private BookFragment bookFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
        init();
        loadFragments();
    }

    private void init() {
        home_top = new Home_top();
        categoriesFragment = new CategoriesFragment();
        serviceFragment = new ServiceFragment();
        viewService = new ViewService();
        top = findViewById(R.id.view_top);
        body =findViewById(R.id.viewbody);
        post = findViewById(R.id.postService);
        detail = findViewById(R.id.OneInfo);
        viewDetail  = findViewById(R.id.viewDetail);
        bookFragment = new BookFragment();
    }

    private void loadFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_top, home_top);
        fragmentTransaction.replace(R.id.viewbody,categoriesFragment);
        fragmentTransaction.replace(R.id.OneInfo,serviceFragment);
        fragmentTransaction.replace(R.id.postService,viewService);
        fragmentTransaction.replace(R.id.viewDetail,bookFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

//
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }


    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int collIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(collIndex);
        cursor.close();
        return result;
    }

    @Override
    public void onFileUploadIntent() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, ServiceFragment.OpenfileUploadRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ServiceFragment.OpenfileUploadRequestCode && resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri uri = data.getData();
            String imagePath = getRealPathFromUri(uri);
            serviceFragment.setImageViewFromFileUpload(imagePath);
        }

    }

    @Override
    public void onAddServiceClicked() {
        body.setVisibility(View.GONE);
        post.setVisibility(View.GONE);
        detail.setVisibility(View.VISIBLE);
        viewDetail.setVisibility(View.GONE);
    }

    @Override
    public void onSerchServiceClicked(String text) {
        viewService.onSearchServiceBytitle(text);
    }

    @Override
    public void onHomeClicked() {
        body.setVisibility(View.VISIBLE);
        post.setVisibility(View.VISIBLE);
        detail.setVisibility(View.GONE);
        viewDetail.setVisibility(View.GONE);
        viewService.DefaultHome();
    }

    @Override
    public void onBookServiceClicked() {
        viewDetail.setVisibility(View.VISIBLE);
        body.setVisibility(View.GONE);
        post.setVisibility(View.GONE);
        detail.setVisibility(View.GONE);
        bookFragment.loadBook();
    }
    @Override
    public void onCategorySearchClicked(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        viewService.onSearchServiceByCategory(text);
    }
}
