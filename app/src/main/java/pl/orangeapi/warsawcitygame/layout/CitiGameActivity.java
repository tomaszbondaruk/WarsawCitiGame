package pl.orangeapi.warsawcitygame.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;

import pl.orangeapi.warsawcitigame.R;

public class CitiGameActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citi_game);
        addListenerOnButton();
    }

    public void addListenerOnButton(){

        final Context context = this;
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainMenuActivity.class);
                startActivity(intent);
            }

        });
    }
}
