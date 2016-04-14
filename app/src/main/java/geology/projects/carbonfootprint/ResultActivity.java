package geology.projects.carbonfootprint;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    private static final double AVERAGE = 20;
    private String csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail("geol1045FootprintData@gmail.com", csv);
            }
        });


        Intent intent = getIntent();
        double footprint = intent.getDoubleExtra("FOOTPRINT", -1);
        csv = intent.getStringExtra("CSV_ANSWERS");
        csv += footprint;


        TextView t =  ((TextView)findViewById(R.id.result_box));

        appendText(t, "\n" + round(footprint) + " tons of CO2 per year\n");

        if (footprint > AVERAGE) {
            appendText(t, "Your result is " + round(footprint - AVERAGE) + " above the U.S. average!");
        }
        else if (footprint < AVERAGE) {
            appendText(t, "Your result is " + round(AVERAGE - footprint) + " below the U.S. average!");
        }
        else {
            appendText(t, "Your result is exactly the U.S. average!");
        }


    }

    private void appendText(TextView t, String s) {
        t.setText(t.getText() + s);
    }

    private String round (double d) {
        String s = String.valueOf(d);
        int dec_at = s.indexOf('.');

        if (dec_at >= 0) {
            return s.substring(0, dec_at + 2);
        }


        return s;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
  /*
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + ": Automated Message");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setData(Uri.parse("mailto:" + destination)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
  */


    public void sendEmail(String destination, String text) {

        String uriText =
                "mailto:" + destination +
                        "?subject=" + Uri.encode(getString(R.string.app_name) + ": Automated Message") +
                        "&body=" + Uri.encode(csv);

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);



        try {
            startActivity(Intent.createChooser(sendIntent, "Send results"));
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(this,"Email could not be sent. ", Toast.LENGTH_LONG).show();
        }

    }



}
