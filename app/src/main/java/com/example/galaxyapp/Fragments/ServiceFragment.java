package com.example.galaxyapp.Fragments;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.galaxyapp.API.API_CALL;
import com.example.galaxyapp.API_Interface.Category_call;
import com.example.galaxyapp.API_Interface.Service_call;
import com.example.galaxyapp.API_Model.Categories;
import com.example.galaxyapp.API_Model.ImageResponse;
import com.example.galaxyapp.API_Model.Service;
import com.example.galaxyapp.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Retrofit retrofit;
    private Service_call service_call;
    private ImageButton addpic;
    private Category_call category_call;
    private EditText title, category, description, gender, price;
    private Button selectdate, addservice, btnnext;
    private String imagePath = "";
    private String ImageName;
    private Spinner Category_Spinner;
    private String selectedCategory="";
    String[] titles_Array ={"Manicure","Delivery","Yoga","Health Fitness"};;
    String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public final static int OpenfileUploadRequestCode = 1240;
    private  final  static  int permission_status_code = 1998;

    private UpdateView listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateView) context;
        } catch (ClassCastException castException) {
            /** In case when the activity does not implement the interface. */
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.service_add,container,false);
            addpic = v.findViewById(R.id.service_addpicture);
            title = v.findViewById(R.id.etTitle);
            Category_Spinner = v.findViewById(R.id.Category_Spinner);
            description = v.findViewById(R.id.etDes);
            gender = v.findViewById(R.id.tvGender);
            price = v.findViewById(R.id.etPrice);
            selectdate = v.findViewById(R.id.btnselectDate);
            addservice = v.findViewById(R.id.btnaddservice);
            btnnext = v.findViewById(R.id.btnservice_next);
            btnnext.setOnClickListener(this);
            selectdate.setOnClickListener(this);
            addservice.setOnClickListener(this);
            addpic.setOnClickListener(this);
            retrofit = API_CALL.getAPI_Instance().getRetrofit();
            category_call = retrofit.create(Category_call.class);
            service_call = retrofit.create(Service_call.class);
            loadCategories();
            StrictMode();
            Category_Spinner.setOnItemSelectedListener(this);
            if(checkforpermission()){
                
            }else{
                Toast.makeText(getActivity(), "Please provide permission", Toast.LENGTH_SHORT).show();
                checkforpermission();
            }
        return v;
    }

    private void loadCategories() {
        Call<List<Categories>> viewCat = category_call.getAllCategories();
        viewCat.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if(response.isSuccessful()){
                    List<Categories> data = response.body();
                    List<String> titles = new ArrayList<>();
                    for(Categories cat : data){
                        titles.add(cat.getTitle());
                    }

                    ArrayAdapter adap = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,titles_Array);
                    Category_Spinner.setAdapter(adap);

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

    private boolean checkforpermission(){
        List<String> listprem = new ArrayList<>();
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},permission_status_code);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permission_status_code);
        for(String prem : permissions){
            if(ContextCompat.checkSelfPermission(getActivity(),prem) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{prem},permission_status_code);
            }
        }
        return true;
    }

    public ServiceFragment() {
        // Required empty public constructor
        retrofit = API_CALL.getAPI_Instance().getRetrofit();
        service_call = retrofit.create(Service_call.class);
    }


    private void StrictMode() {
        StrictMode.ThreadPolicy stict =new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(stict);
    }

    private void loadDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,year,month,day);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year+"-"+month+"-"+dayOfMonth;
        selectdate.setText(date);
    }

    public void setImageVIew(Bitmap image){
        addpic.setImageBitmap(image);
    }

    public void setImageViewFromFileUpload(String imgPath){
        File imgFile = new File(imgPath);
        if(imgFile.exists()){
            imagePath = imgPath;
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            addpic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnselectDate){
            loadDatePicker();
        }else if(v.getId() == R.id.btnaddservice){
            if(TextUtils.isEmpty(price.getText())){
                price.setError("Please provide price");
                return;
            }
            if(TextUtils.isEmpty(gender.getText())){
                gender.setError("Please provide gender");
            }
            String tit = title.getText().toString();
            String des = description.getText().toString();
            String pri = price.getText().toString();
            String gend = gender.getText().toString();
            String cat = selectedCategory;
            String dat = selectdate.getText().toString();
            sendPic();
            Service service = new Service();
            service.setTitle(tit);
            service.setDescription(des);
            service.setPrice(Integer.parseInt(pri));
            service.setGender(gend);
            service.setCategory(selectedCategory);
            service.setStatus(1);
            service.setImageURL(ImageName);
            service.setProviderID(1);
            service.setStart_date(dat);
            addservice(service);
            Toast.makeText(getActivity(), "Service Added", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.service_addpicture){
            listener.onFileUploadIntent();
        }else if(v.getId() == R.id.btnservice_next){
            
            if(TextUtils.isEmpty(title.getText())){
                title.setError("Please provide title");
                return;
            }
            if(TextUtils.isEmpty(description.getText())){
                description.setError("Please provide description");
                return;
            }
            if(imagePath.equals("")){
                Toast.makeText(getActivity(), "Please provide a Image", Toast.LENGTH_SHORT).show();
                return;
            }
            title.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
            Category_Spinner.setVisibility(View.GONE);
            btnnext.setVisibility(View.GONE);
            price.setVisibility(View.VISIBLE);
            gender.setVisibility(View.VISIBLE);
            addservice.setVisibility(View.VISIBLE);
            selectdate.setVisibility(View.VISIBLE);


        }
    }

    private void addservice(Service service) {
        if(ImageName.isEmpty()){
            Toast.makeText(getActivity(), "Please provide Image for the Service", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Void> call = service_call.addService(service);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendPic() {
        if(imagePath.isEmpty()){
            Toast.makeText(getActivity(), "please select image", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);
        Call<ImageResponse> responseCall =  service_call.uploadProfileImage(body);
        StrictMode();
        try {
            Response<ImageResponse> imageResponseResponse = responseCall.execute();
            ImageName = imageResponseResponse.body().getFilename();
            Toast.makeText(getActivity(), ImageName, Toast.LENGTH_SHORT).show();
            SendData();
        }catch (IOException ex){
            Toast.makeText(getActivity(), "Error uploading image", Toast.LENGTH_SHORT).show();
            System.out.println(ex.getMessage());
        }
    }

    private void SendData() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = titles_Array[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = titles_Array[0];
    }

    public interface UpdateView{
        void onFileUploadIntent();
    }
}
