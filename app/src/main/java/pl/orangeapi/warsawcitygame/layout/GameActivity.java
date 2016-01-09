package pl.orangeapi.warsawcitygame.layout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
public class GameActivity extends AppCompatActivity implements LocationListener {
    LocationManager lm;
    Location l;
    String provider;
    double x,y;
    int active;

    Button btnShowLocation;
    ArrayList<GameObject> goList;
    private final double TOLLERANCE = 1.79e-5;

    TextView currentDistance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currentDistance = (TextView) findViewById(R.id.currentDistance);
        goList = (ArrayList<GameObject>) getIntent().getExtras().get("gameObjects");
        active=0;
        btnShowLocation = (Button) findViewById(R.id.button_finish_game);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        provider = lm.getBestProvider(c, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        l = lm.getLastKnownLocation(provider);
        if(l!=null)
        {
            Log.d("Errr", "Lecimy");
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
        double diff_x = Math.abs(l.getLongitude() - goList.get(active).getLongitude());
        double diff_y = Math.abs(l.getLatitude() - goList.get(active).getLatitude());
        double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));

        if (diff_x > TOLLERANCE || diff_y > TOLLERANCE){
            currentDistance.setText("Dystans to: " + dist);
        }
        else {
            active++;
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
}
