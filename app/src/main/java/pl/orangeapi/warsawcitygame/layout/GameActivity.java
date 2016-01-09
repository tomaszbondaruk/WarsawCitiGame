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

    private final double degToMSzer = 111.30;
    private final double degToMDlug = 68.14;

    GameProgressAdapter lvadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dbAdapter = new WarsawCitiGameDBAdapter(GameActivity.this);
        dbAdapter.open();

        currentDistance = (TextView) findViewById(R.id.currentDistance);
        pointDescription = (TextView) findViewById(R.id.pointDescription);
        lv = (ListView) findViewById(R.id.listView);
        x = (EditText) findViewById(R.id.x);
        y = (EditText) findViewById(R.id.y);

        gameProgress = new ArrayList<>();
        lvadapter = new GameProgressAdapter(GameActivity.this, gameProgress);
        lv.setAdapter(lvadapter);

        goList = (ArrayList<GameObject>) getIntent().getExtras().get("gameObjects");
        active=0;

        btnShowLocation = (Button) findViewById(R.id.button_finish_game);

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
            double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude())*degToMDlug;
            double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude())*degToMSzer;
            double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));
            pointDescription.setText(goList.get(active).getDescription());
            currentDistance.setText("Dystans do obecnego punku : " + dist + "km");
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
        Log.d("TEST", "LocationChanged");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        l = lm.getLastKnownLocation(provider);
        double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude())*degToMDlug;
        double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude())*degToMSzer;
        double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));

        if (diff_x > TOLLERANCE || diff_y > TOLLERANCE){
            currentDistance.setText("Dystans do obecnego punku : " + dist + "km");
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
        if(active<=goList.size()){
            double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude())*degToMDlug;
            double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude())*degToMSzer;
            double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));
            pointDescription.setText(goList.get(active).getDescription());
            currentDistance.setText("Dystans do obecnego punku : " + dist + "km");
            x.setText("" + goList.get(active).getLongitude());
            y.setText("" + goList.get(active).getLatitude());
            GameProgress gameSingleProgress = new GameProgress();
            if(goList.get(active-1) instanceof Tree) {
                gameSingleProgress.setType("Drzewo");
            }
            else if(goList.get(active-1) instanceof Shrub) {
                gameSingleProgress.setType("Krzew");
            }
            gameSingleProgress.setName(goList.get(active - 1).getDescription());
            gameSingleProgress.setTime("1");
            gameProgress.add(gameSingleProgress);
            lvadapter.notifyDataSetChanged();
        }
        else{
            LayoutInflater li = LayoutInflater.from(GameActivity.this);
            View promptsView = li.inflate(R.layout.congratz_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    GameActivity.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.nameEditText);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Zapisz wynik",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    if ( ! (userInput.getText().equals(""))) {
                                        Score score= new Score();
                                        score.setUser(userInput.getText().toString());
                                        score.setNumber("" + goList.size());
                                        score.setPoints("1");
                                        score.setTime("1");
                                        dbAdapter.addScore(score);
                                        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
                                        startActivity(intent);

                                        //go on here and dismiss dialog
                                    }
                                    else
                                        Toast.makeText(GameActivity.this, "Aby zapisać wynik wprowadź kswkę",Toast.LENGTH_LONG).show();

                                }
                            })
                    .setNegativeButton("Zakończ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
                                    startActivity(intent);
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
    }
}
