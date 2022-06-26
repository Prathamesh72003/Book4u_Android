package com.example.book4u;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.Continuation;
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
 * Use the {@link UploadPdfFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPdfFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadPdfFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPdfFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPdfFrag newInstance(String param1, String param2) {
        UploadPdfFrag fragment = new UploadPdfFrag();
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

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_ID = "local_userid";
    SharedPreferences sharedpreferences;
    String userid;
    String pdf_id;
    String pdf_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_pdf2, container, false);
        pdfUploadBtn = view.findViewById(R.id.pdfUploadBtn);
        notification = view.findViewById(R.id.Notify);


        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(SHARED_ID, null);
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
                        SubtadapterItems = new ArrayAdapter<String>(getActivity(),R.layout.list_item, subs);
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
            Log.d("second", "local url: "+pdfUrl);
            myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            notification.setText("file: "+ data.getData().getLastPathSegment());


            Log.d("upload", uriString);
//            uploadPdf();
        }else {
            Toast.makeText(getActivity(), "Please select the PDF", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String uploadPDFToFirebase(Uri pdfUrl) {
        String[] firebasePdfurl = {""};
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setTitle("Uploading file...");
//        progressDialog.setProgress(0);
//        progressDialog.show();
        StorageReference storageReference = storage.getReference(); //returns the path
        String fileName = System.currentTimeMillis()+"";
        StorageReference ref = storageReference.child(fileName);
        ref.putFile(pdfUrl)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    main_url = downloadUri.toString();
                    Log.d("firebaseURL", downloadUri.toString());
                    uploadDataToDB(main_url);
                } else {
                    // Handle failures
                    // ...
                    Log.d("firebaseURL", "fail");
                    Toast.makeText(getContext(), "Failed to store PDF. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "File not uploaded", Toast.LENGTH_SHORT).show();

            }
        });
        Log.d("firebaseUrl",""+main_url);

        //        main_url = firebasePdfurl.toString();
        return main_url;
    }


    public void uploadPdf() {
        if (nameText.getText().length() != 0) {
            Log.d("firebaseURL", "uploadPdf: Waiting for 2 sec");

            String firebasePdfUrl = uploadPDFToFirebase(pdfUrl);

            Toast.makeText(getContext(), "PDF Inserted!!", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getActivity(), "All fields required", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadDataToDB(String pdf_url){
        Log.d("firebaseURL", "uploadPdf: "+pdf_url);

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        JSONObject dataParam = new JSONObject();
        try {
            dataParam.put("name", nameText.getText().toString());
            dataParam.put("uploader_id", ""+userid);
            dataParam.put("pdf_url", ""+pdf_url);
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
    }
}