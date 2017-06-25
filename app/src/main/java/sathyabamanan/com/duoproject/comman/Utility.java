package sathyabamanan.com.duoproject.comman;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by baman on 6/25/17.
 */

public class Utility {


    public String getLoginUrl(){
        return "http://userservice.app.veery.cloud/auth/login";
    }

    public String getProfileDetailsUrl(){
        String url = "http://userservice.app.veery.cloud/DVP/API/1.0.0.0/Myprofile";
        return  url;
    }

    public  String getTicketlistUrl(){
        return "http://liteticket.app1.veery.cloud/DVP/API/1.0.0.0/MyTickets/10/1?status=new";
    }

    public void addDataToSharedPreferences(String key, String value, Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences("Duo_Main_Shared_File", ctx.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String  getDataFromSharedPreferences(String key, Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences("Duo_Main_Shared_File", ctx.MODE_PRIVATE);
        return prefs.getString(key, "no_data");
    }


    public boolean isNetworkAvailable(Context Ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Ctx.getSystemService(Ctx.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
