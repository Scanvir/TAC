package info.androidhive.tac.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.tac.model.Klient;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// Database Version
	private static final int DATABASE_VERSION = 3;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// User Login table
	private static final String TABLE_USER = "user";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

	// Klients table
	private static final String TABLE_KLIENTS = "klients";
	private static final String KLIENT_ID = "id";
	private static final String KLIENT_NAME = "name";

	// Dolgi table
	private static final String TABLE_DOLGI = "dolgi";
	private static final String DOLG_KLIENT = "klient_id";
	private static final String DOLG_TOCHKA = "tochka_id";
	private static final String DOLG_DOCUMENT = "document";
	private static final String DOLG = "dolg";


	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String query;
		query = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(query);

		query = "CREATE TABLE " + TABLE_KLIENTS + "("
				+ KLIENT_ID + " INTEGER PRIMARY KEY,"
				+ KLIENT_NAME + " TEXT)";
		db.execSQL(query);

		query = "CREATE TABLE " + TABLE_DOLGI + "("
				+ DOLG_KLIENT + " INTEGER,"
				+ DOLG_TOCHKA + " INTEGER,"
				+ DOLG_DOCUMENT + " TEXT,"
				+ DOLG + " NUMERIC)";
		db.execSQL(query);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_KLIENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOLGI);

		// Create tables again
		onCreate(db);
	}

	// Add new User into database
	public void addUser(String name, String email, String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_EMAIL, email);
		values.put(KEY_UID, uid);
		values.put(KEY_CREATED_AT, created_at);

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close();

	}

	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.delete(TABLE_KLIENTS, null, null);
		db.delete(TABLE_DOLGI, null, null);
		db.close();

		//Log.d(TAG, "Deleted all user info from sqlite");
	}

	public void addKlient(int id, String name) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KLIENT_ID, id);
		values.put(KLIENT_NAME, name);

		// Inserting Row
		db.replace(TABLE_KLIENTS, null, values);
		db.close();

		Log.d(TAG, "New klient inserted into sqlite: " + name);
	}

	public List<Klient> getDataFromDB(){
		List<Klient> klientList= new ArrayList<Klient>();
		String query = "select * from " + TABLE_KLIENTS + " order by name";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query,null);

		if (cursor.moveToFirst()){
			do {
				Klient model = new Klient();
				model.setId(cursor.getInt(0));
				model.setName(cursor.getString(1));

				klientList.add(model);
			}while (cursor.moveToNext());
		}
		Log.d("student data", klientList.toString());
		return klientList;
	}

}
