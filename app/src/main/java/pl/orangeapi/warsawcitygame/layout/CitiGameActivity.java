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
import pl.orangeapi.warsawcitygame.db.pojo.GameObject;
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


        //STWORZENIE ADAPTERA DO BAZY DANYCH
        dbAdapter = new WarsawCitiGameDBAdapter(getApplicationContext());
        dbAdapter.open();

        //
        //WERSJA Z PROGRESS BAR'em. BARDZO DLUGO SIE WCZYTUJE
        //
        //DbPopulateAsyncTask<Void, Void, Void> updateTask = new DbPopulateAsyncTask<Void, Void, Void>(CitiGameActivity.this,dbAdapter);
        //updateTask.execute();

        // DLA PIERWSZEGO URUCHOMIENIA BEDZIE DOSC DLUGO TRWALO, NARAZIE ZAKOMENTOWALEM ZOSTAWIAJAC ORYGINALNA KLASE
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

        // PRZYKLAD WYWOLANIA ZAPYTAN KTORE ZWRACAJA LISTY OBIEKTOW. DEFINICJE OBIEKTOW ZNAJDUJA SIE W PAKIECIE pl.orangeapi.warsawcitygame.db.pojo
        //List<Shrub> s = dbAdapter.getAllShrubs();
        //List<Tree> t = dbAdapter.getAllTrees();
        //List<Property> p = dbAdapter.getAllProperties();
        //List<Square> sq = dbAdapter.getAllSquares();
        //List<Street> st = dbAdapter.getAllStreets();
        //List<Forest> f = dbAdapter.getAllForests();
        try {
            List<GameObject> go = dbAdapter.getStartingPoints("Drzewo",5,52.210252,21.045218,3.0);
            Toast.makeText(CitiGameActivity.this, "ilosc gameobject: "+ go.size() , Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


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
