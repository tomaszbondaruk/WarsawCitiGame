package pl.orangeapi.warsawcitygame.layout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
    ArrayList<GameObject> goList;
    private final double TOLLERANCE = 1.79e-5;

    TextView currentDistance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gps = new GPSService(GameActivity.this);

        currentDistance = (TextView) findViewById(R.id.currentDistance);

        goList = (ArrayList<GameObject>) getIntent().getExtras().get("gameObjects");

        btnShowLocation = (Button) findViewById(R.id.button_finish_game);

        //tree position

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
                currentDistance.setText("Twoja odległość to: " + dist + " m. ZAPIERDALAJ!!!!!!");
                Log.d("CHUJ","Twoja odległość to: " + dist + " m. ZAPIERDALAJ!!!!!!");
            } while (diff_x > TOLLERANCE || diff_y > TOLLERANCE);
        }
        Log.d("ERROR", "Zginąłeś");
    }
}
