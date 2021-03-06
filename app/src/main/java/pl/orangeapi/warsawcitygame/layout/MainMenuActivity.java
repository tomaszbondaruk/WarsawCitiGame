package pl.orangeapi.warsawcitygame.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
import android.widget.Button;

import pl.orangeapi.warsawcitigame.R;

public class MainMenuActivity extends AppCompatActivity {

    Button b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        b1 = (Button) findViewById(R.id.activityMainMenu_button_newGame);
        b2 = (Button) findViewById(R.id.activityMainMenu_button_joinGame);
        b2.setPaintFlags(b2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        b3 = (Button) findViewById(R.id.activityMainMenu_button_statistics);
        b4 = (Button) findViewById(R.id.activityMainMenu_button_quit);

        addListenersToButtons();
    }


    private void addListenersToButtons(){

        final Context context = this;

        ViewGroup parentView = (ViewGroup) findViewById(R.id.activityMainMenu_layout);
        for(int i=0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if(childView.getId() == R.id.activityMainMenu_button_newGame){
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context, ConfigureNewGameActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                });
            }
            else if(childView.getId() == R.id.activityMainMenu_button_quit){
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        finish();
                        System.exit(0);
                    }
                });
            }
            else if (childView.getId() == R.id.activityMainMenu_button_statistics){
                childView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainMenuActivity.this, ScoreActivity.class);
                        finish();
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                });
            }
        };


    }
}
