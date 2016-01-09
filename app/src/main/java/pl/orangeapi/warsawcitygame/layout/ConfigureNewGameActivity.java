package pl.orangeapi.warsawcitygame.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.utils.GameConfiguration;
import pl.orangeapi.warsawcitygame.utils.GameItem;

public class ConfigureNewGameActivity extends AppCompatActivity {

    WarsawCitiGameDBAdapter dbAdapter;

    private Button startNewGameButton;
    private CheckBox treesCheckBox, shrubsCheckBox, apartmentsCheckBox;
    private EditText noPlayersInput, noElementsInput;
    private SeekBar gameRadius;
    private GameConfiguration config;
    private PopupWindow popUp;
    private LinearLayout mainLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = new GameConfiguration();
        setContentView(R.layout.activity_configure_new_game);

        dbAdapter = new WarsawCitiGameDBAdapter(ConfigureNewGameActivity.this);
        dbAdapter.open();

        startNewGameButton = (Button) findViewById(R.id.start_new_game_button);

        treesCheckBox = (CheckBox) findViewById(R.id.checkTrees);
        shrubsCheckBox = (CheckBox) findViewById(R.id.checkShrubs);

        noPlayersInput = (EditText) findViewById(R.id.numberOfPlayersInput);

        noElementsInput = (EditText) findViewById(R.id.noElementsInput);
        noElementsInput.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                noElementsInput.setText("");
            }
        });

        gameRadius = (SeekBar) findViewById(R.id.gameRadiusBar);

        final Context context = this;
        startNewGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if(treesCheckBox.isChecked())
                    config.addToGameItemList(GameItem.TREES);
                if(shrubsCheckBox.isChecked())
                    config.addToGameItemList(GameItem.SHRUBS);

                config.setNoParticipants(1);
                config.setNoElements(Integer.parseInt(noElementsInput.getText().toString()));
                Log.d("LAYOUT", noElementsInput.getText().toString());
                config.setGameRadius(10*((float)gameRadius.getProgress()/100));
                Log.d("LAYOUT", "" + (10*((float)gameRadius.getProgress()/100)));

                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("gameConfiguration", config);
                startActivity(intent);
            }
        });
    }

}
