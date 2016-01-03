package pl.orangeapi.warsawcitigame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;

public class CitiGameActivity extends AppCompatActivity {

    Button button;
    //WarsawCitiGameDBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citi_game);
        Log.i("main thread", "initializing");
        /*dbAdapter = new WarsawCitiGameDBAdapter(getApplicationContext());
        Log.i("main thread", "adapter initialized");
        try {
            dbAdapter.open();
            Log.i("main thread", "adapter opened");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        addListenerOnButton();
    }

    public void addListenerOnButton(){

        final Context context = this;
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainMenuActivity.class);
                startActivity(intent);
            }

        });
    }
}
