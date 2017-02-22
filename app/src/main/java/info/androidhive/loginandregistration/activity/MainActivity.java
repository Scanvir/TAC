package info.androidhive.loginandregistration.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.HashMap;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

	private TextView txtName;
	private TextView txtEmail;
	private Button btnLogout;

	private SQLiteHandler db;
	private SessionManager session;

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppDefault);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initToolbar();
		/*txtName = (TextView) findViewById(R.id.name);
		txtEmail = (TextView) findViewById(R.id.email);
		btnLogout = (Button) findViewById(R.id.btnLogout);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});*/
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.app_name);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				return false;
			}

		});

		toolbar.inflateMenu(R.menu.menu);
	}


	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
