package br.com.doublelogic.spg.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.doublelogic.spg.bean.PasswordSettings;

public class PasswordsDbHelper extends DatabseHelper {

	private static final String TAG = PasswordsDbHelper.class.getSimpleName();

	public PasswordsDbHelper(Context context) {
		super(context);
	}

	public Map<String, List<Long>> getIgnoredNumbers() {
		final Map<String, List<Long>> numbers = new HashMap<String, List<Long>>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			cursor = db.query(TABLE_IGNORED_NUMBERS.TABLE_NAME, new String[] {
					TABLE_IGNORED_NUMBERS.COLUMN_NUMBER,
					TABLE_IGNORED_NUMBERS.COLUMN_IGNORE_DATE }, null, null,
					null, null, null);
			while (cursor != null && cursor.moveToNext()) {
				final String number = cursor.getString(0);
				final long date = cursor.getLong(1);
				List<Long> dates = numbers.get(number);
				if (dates == null) {
					dates = new ArrayList<Long>();
					numbers.put(number, dates);
				}
				dates.add(date);
			}
		} catch (final Exception e) {
			Log.w(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
			close();
		}
		return numbers;
	}

	public PasswordSettings getPasswordSettings(long id) {
		final PasswordSettings settings = new PasswordSettings();

		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();

			cursor = db.rawQuery("select * from " + TABLE_PASSWORD_SETTINGS.TABLE_NAME
					+ "a left join " + TABLE_PASSWORDS.TABLE_NAME + "b on a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_ID + " = b."
					+ TABLE_PASSWORDS.COLUMN_PASSWORD_SETTINGS_ID + " where a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_ID + " = ? order by a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_NAME,
					new String[] { String.valueOf(id) });

			while (cursor != null && cursor.moveToNext()) {
				final String number = cursor.getString(0);
				final long date = cursor.getLong(1);
				List<Long> dates = numbers.get(number);
				if (dates == null) {
					dates = new ArrayList<Long>();
					numbers.put(number, dates);
				}
				dates.add(date);
			}
		} catch (final Exception e) {
			Log.w(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
			close();
		}
		return settings;
	}

	public void savePasswords(String name, PasswordSettings settings,
			List<String> passwords) {
		SQLiteDatabase db = null;
		try {
			db = getWritableDatabase();
			final ContentValues values = new ContentValues();
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_NAME, name);
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_REGEX,
					settings.getRegEx());
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_LENGTH,
					settings.getLength());
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_QUANTITY,
					settings.getQuantity());
			final long id = db.insertOrThrow(
					TABLE_PASSWORD_SETTINGS.TABLE_NAME, null, values);
			if (id != -1) {
				for (final String p : passwords) {
					values.clear();
					values.put(TABLE_PASSWORDS.COLUMN_PASSWORD, p);
					values.put(TABLE_PASSWORDS.COLUMN_PASSWORD_SETTINGS_ID, id);
					db.insertOrThrow(TABLE_PASSWORDS.TABLE_NAME, null, values);
				}
			}

		} catch (final Exception e) {
			Log.w(TAG, e.getMessage(), e);
		} finally {
			if (db != null) {
				db.close();
			}
			close();
		}
	}

}
