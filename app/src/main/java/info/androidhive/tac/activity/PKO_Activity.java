package info.androidhive.tac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import info.androidhive.tac.R;

public class PKO_Activity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLinkToMain;
    private Button klient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pko);

        String txtName = getIntent().getStringExtra("name");
        //String txtId = getIntent().getStringExtra("id");
        btnLinkToMain = (Button) findViewById(R.id.btnLinkToMain);
        btnLinkToMain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        klient = (Button) findViewById(R.id.klient);
        klient.setText(txtName);
    }

}
