package geology.projects.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar_layout.setContentScrimColor(0x81a354);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double footprint = calculateFootprint();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private double calculateFootprint() {
        String ans;
        double miles;
        double res = 13.0;

        ans = ((Spinner)findViewById(R.id.thermostat_box)).getSelectedItem().toString();

        if (ans.equals("Always")) {
            res -= 1.3;
        }
        else if(ans.equals("Never")) {
            res+=1.3;
        }

        ans = ((Spinner)findViewById(R.id.water_box)).getSelectedItem().toString();

        if (ans.equals("Always")) {
            res -= 1.3;
        }
        else if(ans.equals("Sometimes")) {
            res-=0.6;
        }

        ans = ((Spinner)findViewById(R.id.car_box)).getSelectedItem().toString();

        int car = 0;
        if (ans.equals("Small (30-40 MPG)")) {
            car = 30;
        }
        else if(ans.equals("Mid-Size (20-30 MPG)")) {
            car = 25;
        }
        else if(ans.equals("Large (&lt;20 MPG)")) {
            car = 22;
        }
        else if(ans.equals("Hybrid (&gt;40 MPG)")) {
            car = 45;
        }

        if (car != 0) {
            ans = ((EditText)findViewById(R.id.mile_box)).getText().toString();
            miles = Double.parseDouble(ans);

            res+=miles/car;
        }

        ans = ((EditText)findViewById(R.id.longflight_box)).getText().toString();
        miles = Double.parseDouble(ans);

        res+=2.15*miles;

        ans = ((EditText)findViewById(R.id.shortflight_box)).getText().toString();
        miles = Double.parseDouble(ans);

        res+=0.4*miles;

        ans = ((Spinner)findViewById(R.id.meat_box)).getSelectedItem().toString();

        if (ans.equals("At all meals")) {
            res+=1.8;
        }
        else if(ans.equals("Rarely")) {
            res-=2.6;
        }
        else if(ans.equals("Never")) {
            res-= 3.2;
        }

        ans = ((Spinner)findViewById(R.id.organic_box)).getSelectedItem().toString();
        if (ans.equals("Most of the time")) {
            res-=0.3;
        }
        else if(ans.equals("Sometimes")) {
            res-=0.1;
        }


        ans = ((Spinner)findViewById(R.id.organic_box)).getSelectedItem().toString();
        if (ans.equals("Always")) {
            res -= 0.5;
        }
        else if(ans.equals("Sometimes")) {
            res-=0.2;
        }


        //Toast.makeText(this, String.valueOf(res), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("FOOTPRINT", res);
        startActivity(intent);

        return res;
    }
}
