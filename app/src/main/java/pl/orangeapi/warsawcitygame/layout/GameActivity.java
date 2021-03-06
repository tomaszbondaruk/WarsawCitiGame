package pl.orangeapi.warsawcitygame.layout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.pojo.GameObject;
import pl.orangeapi.warsawcitygame.db.pojo.GameProgress;
import pl.orangeapi.warsawcitygame.db.pojo.Score;
import pl.orangeapi.warsawcitygame.db.pojo.Shrub;
import pl.orangeapi.warsawcitygame.db.pojo.Tree;
import pl.orangeapi.warsawcitygame.utils.*;

/**
 * Created by Tomek on 2015-12-18.
 */
public class GameActivity extends AppCompatActivity implements LocationListener {
    LocationManager lm;
    Location l;
    Date startGame, startPoint;
    String provider;
    int active;
    WarsawCitiGameDBAdapter dbAdapter;
    Button btnShowLocation;
    ArrayList<GameObject> goList;
    private final double TOLLERANCE = 0.005;
    ListView lv ;
    List<GameProgress> gameProgress;

    TextView currentDistance, pointDescription;
    EditText x, y;

    private final double degToM = 111.196672;
    boolean working;


    GameProgressAdapter lvadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        working = true;
        dbAdapter = new WarsawCitiGameDBAdapter(GameActivity.this);
        dbAdapter.open();

        startGame = new Date();
        startPoint = new Date();

        currentDistance = (TextView) findViewById(R.id.activityGame_currentDistance);
        pointDescription = (TextView) findViewById(R.id.activityGame_pointDescription);
        lv = (ListView) findViewById(R.id.activityGame_checkedPoints_listView);
        //x = (EditText) findViewById(R.id.x);
        //y = (EditText) findViewById(R.id.y);

        gameProgress = new ArrayList<>();
        lvadapter = new GameProgressAdapter(GameActivity.this, gameProgress);
        lv.setAdapter(lvadapter);

        goList = (ArrayList<GameObject>) getIntent().getExtras().get("gameObjects");
        active=0;

        btnShowLocation = (Button) findViewById(R.id.activityGame_button_finishGame);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater li = LayoutInflater.from(GameActivity.this);
                View promptsView = li.inflate(R.layout.interupted_game, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        GameActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.nameEditText);

                // set textview score
                TextView score = (TextView) promptsView.findViewById(R.id.points1);
                TextView time = (TextView) promptsView.findViewById(R.id.time1);
                TextView objects = (TextView) promptsView.findViewById(R.id.objects1);

                Date temp = new Date();
                long diff = (temp.getTime() - startGame.getTime())/1000;
                long minutes = diff/60;
                long seconds = diff-minutes*60;

                score.setText(getString(R.string.dialog_scoredPoints_label, (gameProgress.size()*20)));
                time.setText(getString(R.string.dialog_elapsedTime_label,String.format("%02d", minutes),String.format("%02d", seconds)));
                objects.setText(getString(R.string.dialog_foundElements_label, gameProgress.size()));

                Button fb = (Button) promptsView.findViewById(R.id.face1);
                fb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, R.string.dialog_socialMediaContent_label + " " + R.string.socialMediaHashtag);
                        startActivity(Intent.createChooser(intent, "Share with"));

                    }
                });

                userInput.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (userInput.getText().equals(R.string.playerNamePlaceholder))
                            userInput.setText("");

                    }
                });


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(R.string.dialog_saveProgress_label,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        if ( ! (userInput.getText().equals(""))) {
                                            Date temp = new Date();
                                            long diff = (temp.getTime() - startPoint.getTime())/1000;
                                            long minutes = diff/60;
                                            long seconds = diff-minutes*60;

                                            Score score= new Score();
                                            score.setUser(userInput.getText().toString());
                                            score.setNumber("" +gameProgress.size());
                                            score.setPoints(""+(gameProgress.size()*20));
                                            score.setTime(""+String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                                            dbAdapter.addScore(score);
                                            Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
                                            finish();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);
                                            //go on here and dismiss dialog
                                        }
                                        else
                                            Toast.makeText(GameActivity.this, R.string.dialog_insertPlaerName_label,Toast.LENGTH_LONG).show();

                                    }
                                })
                        .setNegativeButton(R.string.dialog_finish,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
                                        finish();
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }});

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        provider = lm.getBestProvider(c, false);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, GameActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        l = lm.getLastKnownLocation(provider);
        if(l!=null)
        {
            l = lm.getLastKnownLocation(provider);
            double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude())*degToM*Math.abs(Math.cos(l.getLatitude()));
            double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude())*degToM;
            double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));
            pointDescription.setText(goList.get(active).getDescription());
            currentDistance.setText(getString(R.string.activityGame_currentDistance_label, getDistance(dist)));
            x.setText("" + goList.get(active).getLongitude());
            y.setText("" + goList.get(active).getLatitude());
        }
        else
        {
            Log.d("Errr", "Nie ma gps");
        }
    }

    //If you want location on changing place also than use below method
    //otherwise remove all below methods and don't implement location listener
    @Override
    public void onLocationChanged(Location arg0)
    {
        if (!working)
            return;

        Log.d("TEST", "LocationChanged");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        l = lm.getLastKnownLocation(provider);
        double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude())*degToM*Math.abs(Math.cos(l.getLatitude()));
        double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude())*degToM;
        double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));

        x.setText("" + diff_x);
        y.setText("" + diff_y);
        if (diff_x > TOLLERANCE || diff_y > TOLLERANCE){
            currentDistance.setText(getString(R.string.activityGame_currentDistance_label, getDistance(dist)));
        }
        else {
            goToNextPoint();
        }
        Log.d("ERROR", "Zginąłeś");
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

    private void goToNextPoint(){
        active++;
        if(active<goList.size()){
            double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude())*degToM*Math.abs(Math.cos(l.getLatitude()));
            double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude())*degToM;
            double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));
            pointDescription.setText(goList.get(active).getDescription());
            currentDistance.setText(getString(R.string.activityGame_currentDistance_label, getDistance(dist)));

            //x.setText("" + goList.get(active).getLongitude());
            //y.setText("" + goList.get(active).getLatitude());
            Date temp = new Date();
            long diff = (temp.getTime() - startPoint.getTime())/1000;
            long minutes = diff/60;
            long seconds = diff-minutes*60;
            startPoint = temp;
            GameProgress gameSingleProgress = new GameProgress();
            if(goList.get(active-1) instanceof Tree) {
                gameSingleProgress.setType("Drzewo");
            }
            else if(goList.get(active-1) instanceof Shrub) {
                gameSingleProgress.setType("Krzew");
            }
            gameSingleProgress.setName(goList.get(active - 1).getDescription());
            gameSingleProgress.setTime("" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            gameProgress.add(gameSingleProgress);
            lvadapter.notifyDataSetChanged();
            lv.setSelection(active - 1);
        }
        else{
            working = false;
            LayoutInflater li = LayoutInflater.from(GameActivity.this);
            View promptsView = li.inflate(R.layout.congratz_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    GameActivity.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.nameEditText);

            // set textview score
            TextView score = (TextView) promptsView.findViewById(R.id.points);
            TextView time = (TextView) promptsView.findViewById(R.id.time);
            TextView objects = (TextView) promptsView.findViewById(R.id.objects);

            Date temp = new Date();
            long diff = (temp.getTime() - startPoint.getTime())/1000;
            long minutes = diff/60;
            long seconds = diff-minutes*60;


            score.setText(getString(R.string.dialog_scoredPoints_label, (gameProgress.size()*20+200)));
            time.setText(getString(R.string.dialog_elapsedTime_label,String.format("%02d", minutes),String.format("%02d", seconds)));
            objects.setText(getString(R.string.dialog_foundElements_label, gameProgress.size()));

            Button fb = (Button) promptsView.findViewById(R.id.face);
            fb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, R.string.dialog_socialMediaContent_label + " " + R.string.socialMediaHashtag);
                    startActivity(Intent.createChooser(intent, "Share with"));

                }
            });

            userInput.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (userInput.getText().equals(R.string.playerNamePlaceholder))
                        userInput.setText("");

                }
            });

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton(R.string.dialog_saveProgress_label,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (!(userInput.getText().equals(""))) {
                                                Date temp = new Date();
                                                long diff = (temp.getTime() - startPoint.getTime())/1000;
                                                long minutes = diff/60;
                                                long seconds = diff-minutes*60;

                                                Score score = new Score();
                                                score.setUser(userInput.getText().toString());
                                                score.setNumber("" + gameProgress.size());
                                                score.setPoints(""+(gameProgress.size()*20+200));
                                                score.setTime(""+String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                                                dbAdapter.addScore(score);
                                                Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
                                                finish();
                                                startActivity(intent);
                                                overridePendingTransition(0, 0);
                                                //go on here and dismiss dialog
                                            } else
                                                Toast.makeText(GameActivity.this, R.string.dialog_insertPlaerName_label, Toast.LENGTH_LONG).show();

                                        }
                                    })
                            .setNegativeButton(R.string.dialog_finish,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
                                            finish();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }
            }
    String getDistance(double dist) {
        if (dist > 1)
            return String.format("%1$,.2f", dist) + "km";
        else
            return String.format("%1$,.0f", dist* 1000) + "m";
    }
}
