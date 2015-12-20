package pl.orangeapi.warsawcitigame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.os.Handler;

/**
 * Created by Tomek on 2015-12-18.
 */
public class ConfigureNewGameActivity extends AppCompatActivity {

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_new_game);

        b = (Button) findViewById(R.id.start_new_game_button);
        final Context context = this;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, GameActivity.class);
                startActivity(intent);
            }
        });
    }

}
