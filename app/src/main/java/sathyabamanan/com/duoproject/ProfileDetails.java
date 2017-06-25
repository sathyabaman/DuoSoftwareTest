package sathyabamanan.com.duoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import sathyabamanan.com.duoproject.comman.RequestExternalResouce;
import sathyabamanan.com.duoproject.comman.Utility;

public class ProfileDetails extends AppCompatActivity {
    Context context;
    ProgressDialog pDialog;

    ImageView avator;
    TextView fullname;
    TextView dateofbirth;
    TextView gender;
    TextView useremail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        context = getApplicationContext();

        avator = (ImageView) findViewById(R.id.PD_Image);
        fullname = (TextView) findViewById(R.id.PD_fullnam);
        dateofbirth = (TextView) findViewById(R.id.PD_dateofbirth);
        gender = (TextView) findViewById(R.id.PD_gender);
        useremail = (TextView) findViewById(R.id.PD_usr);

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
                    DisplayProfileDetails(responseData);
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

    public void DisplayProfileDetails(String responseData){
        try {
            JSONObject response  = new JSONObject(responseData);
            Boolean TicketStatus = (Boolean) response.get("IsSuccess");
            if (TicketStatus){
                Picasso.with(context).load(response.getJSONObject("Result").getString("avatar").toString()).resize(142, 145).into(avator);
                fullname.setText(response.getJSONObject("Result").getString("firstname").toString() + " " +response.getJSONObject("Result").getString("lastname").toString());
                gender.setText("Gender : "+ response.getJSONObject("Result").getString("gender").toString());
                useremail.setText("User Email : "+ response.getJSONObject("Result").getString("username").toString());
                String[] parts = response.getJSONObject("Result").getString("birthday").toString().split("T");
                dateofbirth.setText("Date Of Birth : "+ parts[0]);
            } else {
                showErrorMessage("No profile data!", "Profile data not available.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
