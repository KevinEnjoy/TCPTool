package net.tcp.tcptool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.util.HandlerHelper;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        HandlerHelper.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(StartActivity.this,ActContainer.class);
                intent.putExtra("fragment_name",FragmentTCP.class.getName());
                startActivity(intent);
                finish();
            }
        },1800);
    }
}
