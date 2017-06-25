package sathyabamanan.com.duoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import sathyabamanan.com.duoproject.comman.RequestExternalResouce;
import sathyabamanan.com.duoproject.comman.Utility;

public class TicketList extends AppCompatActivity {
    private Context context;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        context = getApplicationContext();
        getTicketList();
    }


    private void getTicketList(){
        if(new Utility().isNetworkAvailable(context)){
            showprogressDialog("Requesting Profile Data. Please wait..");
            new RequestExternalResouce(context, new Utility().getTicketlistUrl(), " ", true, "GET", new RequestExternalResouce.OnTaskDoneListener() {
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
            pDialog = new ProgressDialog(TicketList.this);
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

















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu1, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        switch (menu_id){
            case R.id.menu_refresh:

                break;
            case R.id.menu_profile:
                TicketList.this.startActivity(new Intent(TicketList.this, ProfileDetails.class));
                break;

            default:
                break;
        }
        return  true;
    }


}
