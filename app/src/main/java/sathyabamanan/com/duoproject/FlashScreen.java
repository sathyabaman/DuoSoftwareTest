package sathyabamanan.com.duoproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FlashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                FlashScreen.this.startActivity(new Intent(FlashScreen.this, Login.class));
                FlashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
