package pl.orangeapi.warsawcitygame.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.Exception.NotEnoughObjectsInAreaException;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.pojo.GameObject;
import pl.orangeapi.warsawcitygame.utils.GPSService;
import pl.orangeapi.warsawcitygame.utils.GameConfiguration;
import pl.orangeapi.warsawcitygame.utils.GameItem;
import pl.orangeapi.warsawcitygame.utils.GameObjectList;

public class ConfigureNewGameActivity extends AppCompatActivity {

    WarsawCitiGameDBAdapter dbAdapter;

    private GPSService gpsReader;

    private Button startNewGameButton;
    private CheckBox treesCheckBox, shrubsCheckBox, apartmentsCheckBox;
    private EditText noPlayersInput, noElementsInput;
    private TextView currentRadius;
    private SeekBar gameRadius;
    private GameConfiguration config;
    private PopupWindow popUp;
    private LinearLayout mainLayout;

    private GameObjectList<GameObject> gameObjects;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = new GameConfiguration();
        setContentView(R.layout.activity_configure_new_game);


        dbAdapter = new WarsawCitiGameDBAdapter(ConfigureNewGameActivity.this);
        dbAdapter.open();

        gpsReader = new GPSService(ConfigureNewGameActivity.this);

        currentRadius = (TextView) findViewById(R.id.currentRadius);

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
        gameRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                currentRadius.setText("Obecny promień poszukiwań: " + (double) Math.round((10*((float)progress/100)) * 100) / 100 + "km");
            }
        });

        final Context context = this;
        startNewGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if(treesCheckBox.isChecked())
                    config.addToGameItemList(GameItem.TREES);
                if(shrubsCheckBox.isChecked())
                    config.addToGameItemList(GameItem.SHRUBS);

                config.setNoParticipants(1);
                config.setNoElements(Integer.parseInt(noElementsInput.getText().toString()));
                config.setGameRadius(10 * ((float) gameRadius.getProgress() / 100));

                try {
                    gameObjects = dbAdapter.getStartingPoints("Drzewo",config.getNoElements(),gpsReader.getLatitude(),gpsReader.getLongitude(),config.getGameRadius());
                    for(int i=0;i<5;i++)
                        Log.d("LAYOUT", gameObjects.get(i).getDescription());
                }
                catch(NotEnoughObjectsInAreaException e){
                    Toast.makeText(ConfigureNewGameActivity.this,"Nie znaleziono wmaganej liczby elementów",Toast.LENGTH_LONG).show();
                }
                catch (Exception e){

                }
                Intent intent = new Intent(ConfigureNewGameActivity.this, GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("gameObjects", gameObjects);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getGameConfiguration(){

    }

}
