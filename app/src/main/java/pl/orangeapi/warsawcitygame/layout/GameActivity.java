package pl.orangeapi.warsawcitygame.layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.pojo.GameObject;
import pl.orangeapi.warsawcitygame.utils.*;

/**
 * Created by Tomek on 2015-12-18.
 */
public class  GameActivity extends AppCompatActivity {
    Button btnShowLocation;
    GPSService gps;
    GameObjectList<GameObject> goList;
    private final double TOLLERANCE = 1.79e-5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        goList = (GameObjectList<GameObject>) getIntent().getExtras().get("gameObjects");

        btnShowLocation = (Button) findViewById(R.id.button_finish_game);

        //tree position

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSService(GameActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });


        double diff_x;
        double diff_y;
        double dist;
        for (GameObject go : goList) {
            do {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                diff_x = Math.abs(go.getLongitude() - longitude);
                diff_y = Math.abs(go.getLatitude() - latitude);

                dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));
            } while (diff_x > TOLLERANCE || diff_y > TOLLERANCE);
        }
        Log.d("ERROR", "Zginąłeś");
    }
}
