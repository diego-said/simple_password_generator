package br.com.doublelogic.spg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabseHelper.class.getSimpleName();

	private static final String DATABASE_NAME = "spgDb";
	private static final int DATABASE_VERSION = 5;

	public DatabseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public class TABLE_PASSWORD_SETTINGS implements BaseColumns {
		public static final String TABLE_NAME = "tb_password_settings";
		public static final String INDEX_NAME = "in_password_name";

		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_REGEX = "regex";
		public static final String COLUMN_LENGTH = "length";
		public static final String COLUMN_QUANTITY = "quantity";

		public static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + _ID + "integer primary key, " +
				COLUMN_NAME + " text not null,  " + COLUMN_REGEX + " text not null,  " + COLUMN_LENGTH +" integer not null," + COLUMN_QUANTITY +" integer not null);";
		public static final String CREATE_INDEX = "create index "+INDEX_NAME+" on "+TABLE_NAME+" ("+COLUMN_NAME+")";
	}

	public class TABLE_PASSWORDS implements BaseColumns {
		public static final String TABLE_NAME = "tb_passwords";
		public static final String INDEX_NAME = "in_password";

		public static final String COLUMN_PASSWORD = "password";
		public static final String COLUMN_PASSWORD_SETTINGS_ID = "password_settings_id";

		public static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + _ID + "integer primary key, " +
				COLUMN_PASSWORD + " text not null,  " + COLUMN_PASSWORD_SETTINGS_ID +" integer not null," +
				"foreign key(" + COLUMN_PASSWORD_SETTINGS_ID + ") references " + TABLE_PASSWORD_SETTINGS.TABLE_NAME + "(" + TABLE_PASSWORD_SETTINGS._ID + ") on delete cascade);";
		public static final String CREATE_INDEX = "create index "+INDEX_NAME+" on "+TABLE_NAME+" ("+COLUMN_PASSWORD+")";
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(TABLE_PASSWORD_SETTINGS.CREATE_TABLE);
			db.execSQL(TABLE_PASSWORD_SETTINGS.CREATE_INDEX);

			db.execSQL(TABLE_PASSWORDS.CREATE_TABLE);
			db.execSQL(TABLE_PASSWORDS.CREATE_INDEX);
		} catch (final Exception e) {
			Log.w(TAG, "Error creating tables", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD_SETTINGS.TABLE_NAME);
		db.execSQL("DROP INDEX IF EXISTS " + TABLE_PASSWORD_SETTINGS.INDEX_NAME);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS.TABLE_NAME);
		db.execSQL("DROP INDEX IF EXISTS " + TABLE_PASSWORDS.INDEX_NAME);

		onCreate(db);
	}

}
