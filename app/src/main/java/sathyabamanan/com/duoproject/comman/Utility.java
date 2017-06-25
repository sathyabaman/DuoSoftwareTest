package sathyabamanan.com.duoproject.comman;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by baman on 6/25/17.
 */

public class Utility {


    public String getLoginUrl(){
        return "http://userservice.app.veery.cloud/auth/login";
    }

    public void addDataToSharedPreferences(String key, String value, Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences("zupportdesk", ctx.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }
}
