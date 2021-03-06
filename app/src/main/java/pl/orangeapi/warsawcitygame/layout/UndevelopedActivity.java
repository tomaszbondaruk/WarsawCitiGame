package pl.orangeapi.warsawcitygame.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pl.orangeapi.warsawcitigame.R;

public class UndevelopedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undeveloped);

        final Context context = this;
        View view = findViewById(R.id.undevelopedImage);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }

        });
    }
}
