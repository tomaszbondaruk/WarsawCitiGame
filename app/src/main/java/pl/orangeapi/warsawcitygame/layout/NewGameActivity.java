package pl.orangeapi.warsawcitygame.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.utils.GameConfiguration;

public class NewGameActivity extends AppCompatActivity {

    private GameConfiguration config;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        config = (GameConfiguration)getIntent().getSerializableExtra("gameConfiguration");
    }
}
