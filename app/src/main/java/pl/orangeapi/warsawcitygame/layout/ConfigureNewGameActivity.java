package pl.orangeapi.warsawcitygame.layout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.Exception.NotEnoughObjectsInAreaException;
import pl.orangeapi.warsawcitygame.Exception.NothingWasCheckedException;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.pojo.GameObject;
import pl.orangeapi.warsawcitygame.utils.GameConfiguration;
import pl.orangeapi.warsawcitygame.utils.GameObjectList;
import pl.orangeapi.warsawcitygame.utils.enums.ApplicationObjects;
import pl.orangeapi.warsawcitygame.utils.enums.GameItem;

public class ConfigureNewGameActivity extends AppCompatActivity implements LocationListener {

    LocationManager lm;
    Location l;
    String provider;

    WarsawCitiGameDBAdapter dbAdapter;

    private Button startNewGameButton;
    private CheckBox treesCheckBox, shrubsCheckBox, apartmentsCheckBox;
    private EditText noPlayersInput, noElementsInput;
    private TextView currentRadius;
    private SeekBar gameRadius;
    private GameConfiguration config;

    private GameObjectList<GameObject> gameObjects;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = new GameConfiguration();
        setContentView(R.layout.activity_configure_new_game);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        provider = lm.getBestProvider(c, false);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, ConfigureNewGameActivity.this);

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showGPSDisabledAlertToUser();
        }

        dbAdapter = new WarsawCitiGameDBAdapter(ConfigureNewGameActivity.this);
        dbAdapter.open();

        currentRadius = (TextView) findViewById(R.id.activityConfigureNewGame_currentRadius);

        startNewGameButton = (Button) findViewById(R.id.activityConfigureNewGame_button_startNewGame);

        apartmentsCheckBox = (CheckBox) findViewById(R.id.activityConfigureNewGame_checkbox_apartments);
        apartmentsCheckBox.setPaintFlags(apartmentsCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        treesCheckBox = (CheckBox) findViewById(R.id.activityConfigureNewGame_checkbox_trees);
        treesCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    treesCheckBox.setTextColor(Color.BLACK);
                    shrubsCheckBox.setTextColor(Color.BLACK);
                }
            }
        });

        shrubsCheckBox = (CheckBox) findViewById(R.id.activityConfigureNewGame_checkbox_shrubs);
        shrubsCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    treesCheckBox.setTextColor(Color.BLACK);
                    shrubsCheckBox.setTextColor(Color.BLACK);
                }
            }
        });
        noPlayersInput = (EditText) findViewById(R.id.activityConfigureNewGame_numberOfPlayers_input);
        noPlayersInput.setPaintFlags(noPlayersInput.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        noElementsInput = (EditText) findViewById(R.id.activityConfigureNewGame_numberOfElements_input);
        noElementsInput.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                noElementsInput.setText(R.string.blank);
            }
        });

        gameRadius = (SeekBar) findViewById(R.id.activityConfigureNewGame_gameRadiusBar);
        gameRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                currentRadius.setText(getString(R.string.activityConfigureNewGame_currentRadius_label, ((double) Math.round((10*((float)progress/100)) * 100) / 100)));
            }
        });

        final Context context = this;
        startNewGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                try {
                    if (ActivityCompat.checkSelfPermission( ConfigureNewGameActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(ConfigureNewGameActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    l = lm.getLastKnownLocation(provider);
                    if(l!=null) {
                        if (treesCheckBox.isChecked() && !shrubsCheckBox.isChecked())
                            config.setGameObjects(GameItem.Trees.getText());
                        if (!treesCheckBox.isChecked() && shrubsCheckBox.isChecked())
                            config.setGameObjects(GameItem.Shrubs.getText());
                        if (treesCheckBox.isChecked() && shrubsCheckBox.isChecked())
                            config.setGameObjects(GameItem.TreesShrubs.getText());
                        if (!treesCheckBox.isChecked() && !shrubsCheckBox.isChecked())
                            throw new NothingWasCheckedException();

                        config.setNoParticipants(1);
                        config.setNoElements(Integer.parseInt(noElementsInput.getText().toString()));
                        config.setGameRadius(10 * ((float) gameRadius.getProgress() / 100));


                        gameObjects = dbAdapter.getStartingPoints(config.getGameObjects(), config.getNoElements(), l.getLatitude(), l.getLongitude(), config.getGameRadius());


                        Intent intent = new Intent(ConfigureNewGameActivity.this, GameActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ApplicationObjects.GameObject.getText(), gameObjects);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    else{
                        showGPSDisabledAlertToUser();
                    }
                }
                catch(NotEnoughObjectsInAreaException e){
                    Toast.makeText(ConfigureNewGameActivity.this,R.string.error_notEnoughElements_label,Toast.LENGTH_LONG).show();
                }
                catch (NothingWasCheckedException e){
                    Toast.makeText(ConfigureNewGameActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    treesCheckBox.setTextColor(Color.RED);
                    shrubsCheckBox.setTextColor(Color.RED);
                }
                catch (ClassNotFoundException e){
                    Log.d("ERROR", e.getLocalizedMessage());
                }

            }
        });
    }

    private void getGameConfiguration(){

    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.error_GPSturnedOff_label)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_goToLocalizationSettings_label,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(R.string.dialog_no,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onLocationChanged(Location arg0)
    {
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub
    }

}
