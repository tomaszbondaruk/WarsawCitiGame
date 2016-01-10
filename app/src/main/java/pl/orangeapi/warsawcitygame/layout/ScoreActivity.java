package pl.orangeapi.warsawcitygame.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.pojo.Score;
import pl.orangeapi.warsawcitygame.utils.ScoreAdapter;

public class ScoreActivity extends AppCompatActivity {
    WarsawCitiGameDBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        dbAdapter = new WarsawCitiGameDBAdapter(ScoreActivity.this);
        dbAdapter.open();
        List<Score> score = dbAdapter.getAllScores(null,null);
        ListView lv =(ListView) findViewById(R.id.scoreListView);
        final ScoreAdapter lvadapter = new ScoreAdapter(this, score);
        Button goBack = (Button) findViewById(R.id.button_back_to_main_menu);
        goBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainMenuActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        lv.setAdapter(lvadapter);

    }

}
