package info.androidhive.tac.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import info.androidhive.tac.R;
import info.androidhive.tac.adapter.RecyclerAdapter;
import info.androidhive.tac.helper.SQLiteHandler;
import info.androidhive.tac.helper.SessionManager;
import info.androidhive.tac.model.Klient;
import info.androidhive.tac.network.SampleRetrofitSpiceRequest;

public class MainActivity extends BaseActivity {

	private SampleRetrofitSpiceRequest wsRequest;

	private SQLiteHandler db;
	private SessionManager session;

	private static final int LAYOUT = R.layout.activity_main;

	private Toolbar toolbar;
	private DrawerLayout drawerLayout;

	List<Klient> dbList;
	RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppDefault);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(LAYOUT);

		initToolbar();
		initNavigationView();

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		dbList = new ArrayList<Klient>();
		dbList = db.getDataFromDB();

		// session manager
		session = new SessionManager(getApplicationContext());

		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		wsRequest = new SampleRetrofitSpiceRequest(user.get("uid"));

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mAdapter = new RecyclerAdapter(this,dbList);
		mRecyclerView.setAdapter(mAdapter);

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

	private void initNavigationView() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
		drawerLayout.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				drawerLayout.closeDrawers();
				switch (menuItem.getItemId()) {
					case R.id.actionExit:
						logoutUser();
						break;
					case R.id.actionUpdate:
						UpdateData();
						break;
				}
				return true;
			}
		});
	}

	private void UpdateData() {

		Toast.makeText(getApplicationContext(),
				"Обновление данных...", Toast.LENGTH_LONG)
				.show();

		getSpiceManager().execute(wsRequest, "WebService", DurationInMillis.ONE_MINUTE, new ListContributorRequestListener());

	}

	private class ListContributorRequestListener implements com.octo.android.robospice.request.listener.RequestListener<info.androidhive.tac.model.Klient.klients> {
		@Override
		public void onRequestFailure(SpiceException spiceException) {
			Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onRequestSuccess(final Klient.klients result) {
			Toast.makeText(MainActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
			for (Klient klient : result) {
				db.addKlient(klient.id, klient.name);
			}

			reload();
		}
	}


	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private void reload()
	{
		Intent intent = getIntent();
		//intent.putExtra(Работаем с объектом Intent);//1
		//intent.putExtra(Работаем с объектом Intent);//2
		//intent.putExtra(Работаем с объектом Intent);//3
		overridePendingTransition(0, 0);//4
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//5
		finish();//6
		overridePendingTransition(0, 0);//7
		startActivity(intent);//8
	}
}