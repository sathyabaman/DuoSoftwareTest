package sathyabamanan.com.duoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import sathyabamanan.com.duoproject.comman.RequestExternalResouce;
import sathyabamanan.com.duoproject.comman.Utility;

public class TicketDetails extends AppCompatActivity {
    String TOriginalId;
    TextView subject;
    TextView ticketdtl;
    TextView ticketid;
    private Context context;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        context = getApplicationContext();

        subject = (TextView) findViewById(R.id.TD_Subject);
        ticketdtl = (TextView) findViewById(R.id.TD_details);
        ticketid = (TextView) findViewById(R.id.TD_ticketid);
        Intent intent = getIntent();
        TOriginalId  = intent.getStringExtra("TicketId");
        getcurrentTicketDetails();
    }

    public void getcurrentTicketDetails(){
        if(new Utility().isNetworkAvailable(context)){
            showprogressDialog("Requesting Ticket Details. Please wait..");
            new RequestExternalResouce(context, new Utility().getTicketDetailsUrl()+TOriginalId+ "/Details", " ", true, "GET", new RequestExternalResouce.OnTaskDoneListener() {
                @Override
                public void onTaskDone(String responseData) {
                    try{pDialog.dismiss();} catch (Exception e){e.printStackTrace();}
                    System.out.println("Success :  getcurrentTicketDetails : "+ responseData);

                    DisplayTicketDetails(responseData);
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

    private void DisplayTicketDetails(String responseData){
        try {
            JSONObject response  = new JSONObject(responseData);
            Boolean TicketStatus = (Boolean) response.get("IsSuccess");
            if (TicketStatus){
                subject.setText("Subject : " +response.getJSONObject("Result").getString("subject").toString());
                ticketdtl.setText("Description : " +response.getJSONObject("Result").getString("description").toString());
                ticketid.setText("Ticket Id : " +response.getJSONObject("Result").getString("tid").toString());

            } else {
                showErrorMessage("No Tickets data!", "Ticket Details not available for this ticket.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showprogressDialog(String Message){
        try {
            pDialog = new ProgressDialog(TicketDetails.this);
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
