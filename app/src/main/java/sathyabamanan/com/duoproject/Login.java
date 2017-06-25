package sathyabamanan.com.duoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sathyabamanan.com.duoproject.comman.RequestExternalResouce;
import sathyabamanan.com.duoproject.comman.Utility;

public class Login extends AppCompatActivity {
    TextView email;
    TextView password;

    String login_email;
    String login_password;

    Context context;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();

        email = (TextView) findViewById(R.id.L_email);
        password = (TextView) findViewById(R.id.L_password);

    }


    public void sendLloginRequest(View v){
        if(isNetworkAvailable()){
            showprogressDialog("Requesting Login token. Please wait.");
            login_email = email.getText().toString();
            login_password =  password.getText().toString();
            String getLoginUrl = getRequestBody();
            new RequestExternalResouce(context, new Utility().getLoginUrl(), getLoginUrl, new RequestExternalResouce.OnTaskDoneListener() {
                @Override
                public void onTaskDone(String responseData) {
                    try{pDialog.dismiss();} catch (Exception e){e.printStackTrace();}
                    System.out.println("Success :  in activity"+ responseData);
                }

                @Override
                public void onError() {
                    try{pDialog.dismiss();} catch (Exception e){e.printStackTrace();}
                    showErrorMessage("Login failed!", "Please check your email and password.");
                }
            }).execute();
        } else
            showErrorMessage("No Connectivity!", "Please check your Internet connection.");

    }


    public String getRequestBody(){
        JSONObject requestBody = new JSONObject();
        JSONArray scopeArray = new JSONArray();
        try {
            scopeArray.put("all_all");
            requestBody.put("userName", login_email);
            requestBody.put("password", login_password);
            requestBody.put("scope", scopeArray);
            requestBody.put("console", "AGENT_CONSOLE");
            requestBody.put("clientID", "e8ea7bb0-5026-11e7-a69b-b153a7c332b9");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return requestBody.toString();
    }


    private void showprogressDialog(String Message){
        try {
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage(Message);
            pDialog.show();
        }catch (Exception e){e.printStackTrace();}
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showErrorMessage(String title, String data){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(data)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(R.drawable.error_message)
                .show();
    }



}
