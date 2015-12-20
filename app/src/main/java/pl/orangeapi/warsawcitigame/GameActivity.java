package pl.orangeapi.warsawcitigame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tomek on 2015-12-18.
 */
public class GameActivity extends AppCompatActivity {

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        b = (Button) findViewById(R.id.button_finish_game);
        final Context context = this;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, GameSummaryActivity.class);
                startActivity(intent);
            }
        });
    }

}
