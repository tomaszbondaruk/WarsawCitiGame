package pl.orangeapi.warsawcitigame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addListenersToButtons();
    }

    private void addListenersToButtons(){

        final Context context = this;

        ViewGroup parentView = (ViewGroup) findViewById(R.id.layout);
        for(int i=0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if(childView.getId() != R.id.button_quit){
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
