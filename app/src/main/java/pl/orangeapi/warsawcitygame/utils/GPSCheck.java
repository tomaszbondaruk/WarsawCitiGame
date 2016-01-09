package pl.orangeapi.warsawcitygame.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import pl.orangeapi.warsawcitygame.layout.GameActivity;

/**
 * Created by Kula on 09.01.2016.
 */
public class GPSCheck extends AsyncTask<Void, Void, Void> {
    private final Context mContext;
    private GPSService gps;

    private double x, y;
    private final double TOLLERANCE = 3;

    public GPSCheck(Context context, double[] pos) {
        this.mContext = context;
        x = pos[0];
        y = pos[1];

        gps = new GPSService(mContext);
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            double diff_x = Math.abs(x - longitude);
            double diff_y = Math.abs(y - latitude);

            if (diff_x > TOLLERANCE || diff_y > TOLLERANCE) {
                double dist = Math.sqrt(Math.pow(diff_x, 2) + Math.pow(diff_y, 2));
            }
            else {
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }else{
            gps.showSettingsAlert();
        }



        return null;
    }
}
