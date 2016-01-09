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
import java.util.List;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.pojo.Forest;
import pl.orangeapi.warsawcitygame.db.pojo.Property;
import pl.orangeapi.warsawcitygame.db.pojo.Shrub;
import pl.orangeapi.warsawcitygame.db.pojo.Square;
import pl.orangeapi.warsawcitygame.db.pojo.Street;
import pl.orangeapi.warsawcitygame.db.pojo.Tree;
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
        //getApplicationContext().deleteDatabase("warsawcitigame.db");

        dbAdapter = new WarsawCitiGameDBAdapter(getApplicationContext());
        dbAdapter.open();

        //
        //WERSJA Z PROGRESS BAR'em. BARDZO DLUGO SIE WCZYTUJE
        //
        //DbPopulateAsyncTask<Void, Void, Void> updateTask = new DbPopulateAsyncTask<Void, Void, Void>(CitiGameActivity.this,dbAdapter);
        //updateTask.execute();



        // PRZYKLAD WYWOLANIA ZAPYTAN KTORE ZWRACAJA LISTY OBIEKTOW. DEFINICJE OBIEKTOW ZNAJDUJA SIE W PAKIECIE pl.orangeapi.warsawcitygame.db.pojo
        //List<Shrub> s = dbAdapter.getAllShrubs();
        //List<Tree> t = dbAdapter.getAllTrees();
        //List<Property> p = dbAdapter.getAllProperties();
        //List<Square> sq = dbAdapter.getAllSquares();
        //List<Street> st = dbAdapter.getAllStreets();
        //List<Forest> f = dbAdapter.getAllForests();


        setContentView(R.layout.activity_citi_game);

        if (!dbAdapter.isAlreadyPopulated()) {
            WarsawCitiGameDBProcessor dbProcessor = new WarsawCitiGameDBProcessor(CitiGameActivity.this, dbAdapter);
            try {
                Toast.makeText(CitiGameActivity.this, "To jest prawdopodobnie pierwsze uruchomienie aplikacji, pobieram dane. Może to trochę potrwać...",Toast.LENGTH_LONG).show();
                dbProcessor.populateDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
