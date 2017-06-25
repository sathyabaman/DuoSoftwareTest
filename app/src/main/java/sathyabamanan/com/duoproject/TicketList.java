package sathyabamanan.com.duoproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sathyabamanan.com.duoproject.Adapters.TicketListAdapter;
import sathyabamanan.com.duoproject.ModelObjects.TicketListModel;
import sathyabamanan.com.duoproject.comman.RequestExternalResouce;
import sathyabamanan.com.duoproject.comman.SimpleDividerItemDecoration;
import sathyabamanan.com.duoproject.comman.Utility;

public class TicketList extends AppCompatActivity {
    private Context context;
    ProgressDialog pDialog;
    RecyclerView TlistrecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static TicketListAdapter TicketAdapter;
    public static List<TicketListModel> TicketListArray = new ArrayList<TicketListModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        context = getApplicationContext();
        TlistrecyclerView = (RecyclerView) findViewById(R.id.TL_recyclerview);
        getTicketList();
        updateRecyclerView();
    }


    private void getTicketList(){
        if(new Utility().isNetworkAvailable(context)){
            showprogressDialog("Requesting Ticket Data. Please wait..");
            new RequestExternalResouce(context, new Utility().getTicketlistUrl(), " ", true, "GET", new RequestExternalResouce.OnTaskDoneListener() {
                @Override
                public void onTaskDone(String responseData) {
                    try{pDialog.dismiss();} catch (Exception e){e.printStackTrace();}
                    System.out.println("Success :  getProfileDetails"+ responseData);
                    addTicklist(responseData);
                    TicketAdapter.notifyDataSetChanged();
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


    private void updateRecyclerView(){

        TlistrecyclerView.addItemDecoration(new SimpleDividerItemDecoration(context));
        layoutManager = new LinearLayoutManager(context);
        TlistrecyclerView.setLayoutManager(layoutManager);
        TicketAdapter = new TicketListAdapter(context, TicketListArray);
        TlistrecyclerView.setAdapter(TicketAdapter);
    }


    private void addTicklist(String TicketList){
        try {
            JSONObject TList = new JSONObject(TicketList);
            JSONArray TArray = TList.getJSONArray("Result");
            for (int y=0; y< TArray.length(); y++){
                TicketListModel Tickets = new Gson().fromJson(TArray.get(y).toString(), TicketListModel.class);
                TicketListArray.add(Tickets);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                TicketListArray.clear();
                TicketAdapter.notifyDataSetChanged();
                getTicketList();
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
