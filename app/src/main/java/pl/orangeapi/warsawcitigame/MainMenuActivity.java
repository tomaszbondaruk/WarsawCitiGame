package pl.orangeapi.warsawcitigame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.os.Handler;

public class MainMenuActivity extends AppCompatActivity {

    Button b1, b2, b3, b4;

    Animation animMove, animFade, animFadeAndMove;
    AnimationSet set;

    Runnable b1anim, b2anim, b3anim, b4anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        b1 = (Button) findViewById(R.id.button_new_game);
        b2 = (Button) findViewById(R.id.button_join_current_game);
        b3 = (Button) findViewById(R.id.button_statistics);
        b4 = (Button) findViewById(R.id.button_quit);

        addListenersToButtons();

        animMove = AnimationUtils.loadAnimation(this, R.anim.button_move);
        animFade = AnimationUtils.loadAnimation(this, R.anim.anim_aplha);
        animFadeAndMove = AnimationUtils.loadAnimation(this, R.anim.move_and_alpha);

        set = new AnimationSet(true);
        set.addAnimation(animMove);
        set.addAnimation(animFade);
        set.setFillAfter(true);

        set.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                b1.setVisibility(View.INVISIBLE);

            }
        });

        b1.startAnimation(animMove);

        b1anim = new Runnable() {
            @Override
            public void run(){
                b1.startAnimation(set);
            }
        };

        b2anim = new Runnable() {
            @Override
            public void run(){
                b2.startAnimation(animMove);
            }
        };

        b3anim = new Runnable() {
            @Override
            public void run(){
                b3.startAnimation(animMove);
            }
        };

        b4anim = new Runnable() {
            @Override
            public void run(){
                b4.startAnimation(animMove);
            }
        };
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            /*Handler h = new Handler();
            h.postDelayed(b1anim, 1000);
            h = new Handler();
            h.postDelayed(b2anim, 2000);
            h = new Handler();
            h.postDelayed(b3anim, 3000);
            h = new Handler();
            h.postDelayed(b4anim, 4000);
            */
        }
    }

    private void addListenersToButtons(){

        final Context context = this;

        ViewGroup parentView = (ViewGroup) findViewById(R.id.layout);
        for(int i=0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if(childView.getId() == R.id.button_new_game){
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context, ConfigureNewGameActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if(childView.getId() != R.id.button_quit){
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(context, UndevelopedActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else{
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        finish();
                        System.exit(0);
                    }
                });
            }

        };


    }
}
