package com.example.book4u;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

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

    TextInputEditText nameText,descText;
    AutoCompleteTextView auto_complete_txt,dept_autocomplete,sub_autocomplete;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_pdf, container, false);

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
        descText = view.findViewById(R.id.descText);
        auto_complete_txt = view.findViewById(R.id.auto_complete_txt);
        dept_autocomplete = view.findViewById(R.id.dept_autocomplete);
        sub_autocomplete = view.findViewById(R.id.sub_autocomplete);

        Button pdfUploadBtn = view.findViewById(R.id.pdfUploadBtn);
        pdfUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
//                startActivity(intent);
                startActivityForResult(intent, 1212);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPdf();
            }
        });

        return view;
    }

    public void uploadPdf() {
        if (nameText.getText().length()!=0 && descText.getText().length()!=0 && auto_complete_txt.getText().length()!=0 && dept_autocomplete.getText().length()!=0 && sub_autocomplete.getText().length()!=0) {
            RequestQueue requestQueue= Volley.newRequestQueue(getActivity());

            JSONObject dataParam = new JSONObject();
            try {
                dataParam.put("name", nameText.getText().toString());
                dataParam.put("uploader_id", "613f8323c9fad3ccf92f1c0a");
                dataParam.put("pdf_url","http://www.africau.edu/images/default/sample.pdf");
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
        switch (requestCode) {
            case 1212:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();

                    // after setting up firebase this is the code to upload file to fireabse and get pdf url in return
//                    if(file.exists()) {
//                        UploadTask uploadTask = riversRef.putFile(Uri.fromFile(file),metadata);
//                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//
//                                // Continue with the task to get the download URL
//                                return riversRef.getDownloadUrl();
//                            }
//                        }).addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                Uri downloadUri = task.getResult();
//                                getUploadedMediaPath.getUploadedFileUrl(downloadUri.toString());
//                            }
//                        });
//                    }

                    Log.d("upload", uriString);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}