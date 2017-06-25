package sathyabamanan.com.duoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sathyabamanan.com.duoproject.comman.RequestExternalResouce;
import sathyabamanan.com.duoproject.comman.Utility;

public class ProfileDetails extends AppCompatActivity {
    Context context;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        context = getApplicationContext();

        getProfileDetails();
    }


    private void getProfileDetails(){
        if(new Utility().isNetworkAvailable(context)){
            showprogressDialog("Requesting Profile Data. Please wait..");
            new RequestExternalResouce(context, new Utility().getProfileDetailsUrl(), " ", true, "GET", new RequestExternalResouce.OnTaskDoneListener() {
                @Override
                public void onTaskDone(String responseData) {
                    try{pDialog.dismiss();} catch (Exception e){e.printStackTrace();}
                    System.out.println("Success :  getProfileDetails"+ responseData);
                }

                @Override
                public void onError() {
                    try{pDialog.dismiss();} catch (Exception e){e.printStackTrace();}
                    showErrorMessage("Error occurred!", "Please try again.");
                }
            }).execute();
        } else
            showErrorMessage("No Connectivity!", "Please check your Internet connection.");
    }





    private void showprogressDialog(String Message){
        try {
            pDialog = new ProgressDialog(ProfileDetails.this);
            pDialog.setMessage(Message);
            pDialog.show();
        }catch (Exception e){e.printStackTrace();}
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
