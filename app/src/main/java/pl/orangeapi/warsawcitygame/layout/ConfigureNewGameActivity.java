package pl.orangeapi.warsawcitygame.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.utils.GameConfiguration;

public class ConfigureNewGameActivity extends AppCompatActivity {

    private Button startNewGameButton;
    private EditText noPlayersInput;
    private GameConfiguration config;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = new GameConfiguration();
        setContentView(R.layout.activity_configure_new_game);

        startNewGameButton = (Button) findViewById(R.id.start_new_game_button);
        noPlayersInput = (EditText) findViewById(R.id.numberOfPlayersInput);

        final Context context = this;
        startNewGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                config.setNoParticipants(Integer.parseInt(noPlayersInput.getText().toString()));

                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("gameConfiguration", config);
                startActivity(intent);
            }
        });
    }

}
