package pl.orangeapi.warsawcitygame.layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.processor.DbPopulateAsyncTask;
import pl.orangeapi.warsawcitygame.db.processor.WarsawCitiGameDBProcessor;

public class CitiGameActivity extends AppCompatActivity {

    Button button;
    WarsawCitiGameDBAdapter dbAdapter;

    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        //NA POTRZEBY TESTOW USUWANIE BAZY ZA KAZDYM RAZEM
        //
        getApplicationContext().deleteDatabase("warsawcitigame.db");
        dbAdapter = new WarsawCitiGameDBAdapter(getApplicationContext());
        dbAdapter.open();

        //
        //WERSJA Z PROGRESS BAR'em. BARDZO DLUGO SIE WCZYTUJE
        //
        //DbPopulateAsyncTask<Void, Void, Void> updateTask = new DbPopulateAsyncTask<Void, Void, Void>(CitiGameActivity.this,dbAdapter);
        //updateTask.execute();


        if (!dbAdapter.isAlreadyPopulated()) {
            WarsawCitiGameDBProcessor dbProcessor = new WarsawCitiGameDBProcessor(CitiGameActivity.this, dbAdapter);
            try {
                dbProcessor.populateDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Toast.makeText(getApplicationContext(), "posiadam: " + dbAdapter.treesCount() + " drzew", Toast.LENGTH_LONG).show();


        setContentView(R.layout.activity_citi_game);
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
