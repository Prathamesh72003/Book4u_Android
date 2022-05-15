package com.example.book4u;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadPdfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPdfFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadPdfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPdfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPdfFragment newInstance(String param1, String param2) {
        UploadPdfFragment fragment = new UploadPdfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String[] years =  {"1st Year","2nd Year","3rd Year"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    String[] dept =  {"Computer","IT","Civil","Mechanical", "Metallurgy","ENTC","Electrical", "DDGM"};
    int dept_id_selected = 1;
    ArrayList<String> subject_id = new ArrayList<String>();
    String subject_id_selected = "";
    AutoCompleteTextView DeptautoCompleteTxt;
    ArrayAdapter<String> DeptadapterItems;

    //    String[] subs;
    AutoCompleteTextView SubtautoCompleteTxt;
    ArrayAdapter<String> SubtadapterItems;
    //firebase;
    FirebaseStorage storage;
    FirebaseDatabase database;
    File myFile;
    String url;
    Uri pdfUrl; //Uri are actually urls
    Button pdfUploadBtn;
    ProgressDialog progressDialog;
    TextView notification;

    TextInputEditText nameText,descText;
    AutoCompleteTextView auto_complete_txt,dept_autocomplete,sub_autocomplete;
    Button submit;

    String main_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_pdf, container, false);
        pdfUploadBtn = view.findViewById(R.id.pdfUploadBtn);
        notification = view.findViewById(R.id.Notify);

        pdfUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    storeInFirebase();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1212);
                }

            }
        });

        autoCompleteTxt = view.findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item,years);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

//                Toast.makeText(getContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        DeptautoCompleteTxt = view.findViewById(R.id.dept_autocomplete);

        DeptadapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item,dept);
        DeptautoCompleteTxt.setAdapter(DeptadapterItems);

        DeptautoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                dept_id_selected = position+1;
                Log.d("upload", "Item: "+dept_id_selected);
                getDepartmentSubject(dept_id_selected);
            }
        });

        SubtautoCompleteTxt = view.findViewById(R.id.sub_autocomplete);

        getDepartmentSubject(1);

        SubtautoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                subject_id_selected = subject_id.get(position);
                Log.d("upload", "Selected subject id "+subject_id_selected);
//                Toast.makeText(getContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        submit = view.findViewById(R.id.SubmitButton);
        nameText = view.findViewById(R.id.nameText);
        descText =  (TextInputEditText) view.findViewById(R.id.descText);
        auto_complete_txt = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_txt);
        dept_autocomplete = (AutoCompleteTextView) view.findViewById(R.id.dept_autocomplete);
        sub_autocomplete = (AutoCompleteTextView) view.findViewById(R.id.sub_autocomplete);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (pdfUrl==null) {
//                    sendPdf(pdfUrl);
//                }else {
//                    Toast.makeText(getActivity(), "Select a file", Toast.LENGTH_SHORT).show();
//                }
                uploadPdf();
            }
        });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1212 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            storeInFirebase();
        }else {
            Toast.makeText(getActivity(), "Please give the permission to select PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeInFirebase(){
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        //offer user to selct the pdf from file manager
        //for this we will use Intent
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);// to fetch files
        startActivityForResult(intent, 1212);
    }



    public void getDepartmentSubject(int id){
        Log.d("upload", id+"");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest fetch = new JsonArrayRequest(Request.Method.GET, getString(R.string.baseUrl)+"get_dropdown_subject?id="+id, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        if(!subject_id.isEmpty())
                            subject_id.clear();
                        String[] subs = new String[response.length()];
                        for(int i=0; i<response.length(); i++){
                            try{
                                JSONObject obj = response.getJSONObject(i);
                                subject_id.add(obj.getString("id"));
                                subs[i] = obj.getString("name");
                            }catch (Exception e){
                                Toast.makeText(getContext(), "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.d("upload", Arrays.toString(subs));
                        Log.d("upload", subject_id.toString());
                        SubtadapterItems = new ArrayAdapter<String>(getContext(),R.layout.list_item, subs);
                        SubtautoCompleteTxt.setAdapter(SubtadapterItems);
//                        Toast.makeText(getContext(), "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("upload", error.toString());
            }
        });
        requestQueue.add(fetch);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check wehter user has selected a file or not

        if (resultCode == Activity.RESULT_OK && requestCode == 1212 && data != null) {
            // Get the Uri of the selected file
            pdfUrl = data.getData();
            String uriString = pdfUrl.toString();
            Log.d("second", "Firebaseurl: "+pdfUrl);
            myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            notification.setText("file: "+ data.getData().getLastPathSegment());


            Log.d("upload", uriString);
        }else {
            Toast.makeText(getActivity(), "Please select the PDF", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String sendPdf(Uri pdfUrl) {
        String[] firebasePdfurl = {""};
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();
        StorageReference storageReference = storage.getReference(); //returns the path
        String fileName = System.currentTimeMillis()+"";
        StorageReference ref = storageReference.child(fileName);
        storageReference.child(fileName).putFile(pdfUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        url = taskSnapshot.toString();
//                        main_url = storageReference.toString();
                        DatabaseReference databaseReference = database.getReference();
                        databaseReference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "File Successfully uploaded!"+url, Toast.LENGTH_SHORT).show();
                                    main_url = ref.getDownloadUrl().toString();
                                    Log.d("insideact", "onComplete: "+ref.getDownloadUrl());
                                }else {
                                    Toast.makeText(getContext(), "File not Successfully uploaded!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                UploadTask.TaskSnapshot uri = task.getResult();
//                main_url = storageReference.getDownloadUrl().toString();
                Uri uri1 = uri.getUploadSessionUri();
                firebasePdfurl[0] = uri1.getLastPathSegment();
//                main_url = Arrays.toString(firebasePdfurl);
                Log.d("insideact", "onComplete: "+Arrays.toString(firebasePdfurl));
                Log.d("insideact", "onComplete: "+uri1.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "File Successfully not upladed", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int current = (int) ((int) 100*snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setProgress(current);
            }
        });
        Log.d("firebaseUrl",firebasePdfurl[0]);
//        main_url = firebasePdfurl.toString();
        return firebasePdfurl[0];
    }


    public void uploadPdf() {
        if (nameText.getText().length() != 0) {
            RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
            String firebasePdfUrl = sendPdf(pdfUrl);
            Log.d("fbpdf", "uploadPdf: "+firebasePdfUrl);
            Toast.makeText(getContext(), ""+main_url, Toast.LENGTH_SHORT).show();
            JSONObject dataParam = new JSONObject();
            try {
                dataParam.put("name", nameText.getText().toString());
                dataParam.put("uploader_id", "613f8323c9fad3ccf92f1c0a");
                dataParam.put("pdf_url", "test"+main_url);
                dataParam.put("img_url", "https://sample-videos.com/img/Sample-jpg-image-500kb.jpg");
                dataParam.put("department_id", dept_id_selected);
                dataParam.put("subject_id", subject_id_selected);
                dataParam.put("Description", descText.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest putData = new JsonObjectRequest(Request.Method.POST, getString(R.string.baseUrl) + "upload_pdf", dataParam, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getContext(), "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(putData);
        }else {
            Toast.makeText(getActivity(), "All fields required", Toast.LENGTH_SHORT).show();
        }
    }

}