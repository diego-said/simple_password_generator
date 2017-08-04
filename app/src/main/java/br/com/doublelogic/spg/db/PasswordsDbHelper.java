package br.com.doublelogic.spg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.doublelogic.spg.bean.PasswordSettings;

public class PasswordsDbHelper extends DatabseHelper {

	private static final String TAG = PasswordsDbHelper.class.getSimpleName();

	public PasswordsDbHelper(Context context) {
		super(context);
	}
	
	public List<PasswordSettings> getListPasswordSettings() {
		final List<PasswordSettings> listPasswordSettings = new ArrayList<>();

		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();

			cursor = db.rawQuery("select * from " + TABLE_PASSWORD_SETTINGS.TABLE_NAME
					+ " a left join " + TABLE_PASSWORDS.TABLE_NAME + " b on a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_ID + " = b."
					+ TABLE_PASSWORDS.COLUMN_PASSWORD_SETTINGS_ID + " order by a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_NAME,
					null);

			int idIndex = -1;
			int nameIndex = -1;
			int regexIndex = -1;
			int lengthIndex = -1;
			int quantityIndex = -1;
			int passwordIndex = -1;
			
			PasswordSettings settings = null;
			while (cursor != null && cursor.moveToNext()) {
				if(settings == null) {
					idIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_ID);
					nameIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_NAME);
					regexIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_REGEX);
					lengthIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_LENGTH);
					quantityIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_QUANTITY);
					
					passwordIndex = cursor.getColumnIndex(TABLE_PASSWORDS.COLUMN_PASSWORD);
				}
				
				PasswordSettings actualSettings = new PasswordSettings();
				actualSettings.setId(cursor.getInt(idIndex));
				
				if(settings != null && settings.getId() == actualSettings.getId()) {
					settings.getPasswords().add(cursor.getString(passwordIndex));
				} else {
					actualSettings.setName(cursor.getString(nameIndex));
					actualSettings.setRegEx(cursor.getString(regexIndex));
					actualSettings.setLength(cursor.getInt(lengthIndex));
					actualSettings.setQuantity(cursor.getInt(quantityIndex));
	
					actualSettings.getPasswords().add(cursor.getString(passwordIndex));
					
					settings = actualSettings;
					
					listPasswordSettings.add(actualSettings);
				}
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
		return listPasswordSettings;
	}

	public PasswordSettings getPasswordSettings(long id) {
		final PasswordSettings settings = new PasswordSettings();

		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();

			cursor = db.rawQuery("select * from " + TABLE_PASSWORD_SETTINGS.TABLE_NAME
					+ " a left join " + TABLE_PASSWORDS.TABLE_NAME + " b on a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_ID + " = b."
					+ TABLE_PASSWORDS.COLUMN_PASSWORD_SETTINGS_ID + " where a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_ID + " = ? order by a."
					+ TABLE_PASSWORD_SETTINGS.COLUMN_NAME,
					new String[] { String.valueOf(id) });

			int idIndex = -1;
			int nameIndex = -1;
			int regexIndex = -1;
			int lengthIndex = -1;
			int quantityIndex = -1;
			int passwordIndex = -1;
			while (cursor != null && cursor.moveToNext()) {
				if(settings.getId() == -1) {
					idIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_ID);
					nameIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_NAME);
					regexIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_REGEX);
					lengthIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_LENGTH);
					quantityIndex = cursor.getColumnIndex(TABLE_PASSWORD_SETTINGS.COLUMN_QUANTITY);
					
					passwordIndex = cursor.getColumnIndex(TABLE_PASSWORDS.COLUMN_PASSWORD);
					
					settings.setId(cursor.getInt(idIndex));
					settings.setName(cursor.getString(nameIndex));
					settings.setRegEx(cursor.getString(regexIndex));
					settings.setLength(cursor.getInt(lengthIndex));
					settings.setQuantity(cursor.getInt(quantityIndex));
				}
				
				settings.getPasswords().add(cursor.getString(passwordIndex));
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

	public void savePasswords(PasswordSettings settings, List<String> passwords) {
		SQLiteDatabase db = null;
		try {
			db = getWritableDatabase();
			final ContentValues values = new ContentValues();
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_NAME, settings.getName());
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_REGEX, settings.getRegEx());
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_LENGTH, settings.getLength());
			values.put(TABLE_PASSWORD_SETTINGS.COLUMN_QUANTITY, settings.getQuantity());
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

	public void removePasswords(String... passwordIds) {
		SQLiteDatabase db = null;
		try {
			if(passwordIds != null && passwordIds.length > 0) {
				db = getWritableDatabase();
				StringBuilder whereClause = new StringBuilder(TABLE_PASSWORD_SETTINGS.COLUMN_ID);
				whereClause.append(" in (");
				for(int i=0; i<passwordIds.length; i++) {
					whereClause.append("?,");
				}
				whereClause.deleteCharAt(whereClause.length() -1);
				whereClause.append(")");

				db.delete(TABLE_PASSWORD_SETTINGS.TABLE_NAME, whereClause.toString(), passwordIds);
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
