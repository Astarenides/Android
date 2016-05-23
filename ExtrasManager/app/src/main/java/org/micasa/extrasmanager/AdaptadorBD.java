package org.micasa.extrasmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import java.io.File;

public class AdaptadorBD {
	public static final String DATABASE_NAME = "extras";
	public static final int DATABASE_VERSION = 1;
	private DatabaseHelper DBHelper;
	private final Context context;
	private SQLiteDatabase db;

	public AdaptadorBD(Context context) {
		this.context = context;
		this.DBHelper = new DatabaseHelper(this.context);
	}

	public AdaptadorBD abrir() throws SQLException {
		File localFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data/org.micasa.extrasmanager/databases");
		localFile.mkdirs();
		db = SQLiteDatabase.openDatabase(new File(localFile, "/extras").toString(), null, 0);
		return this;
	}

	public void begin() {
		db.beginTransaction();
	}

	public void cerrar() {
		DBHelper.close();
	}

	public void commit() {
		db.setTransactionSuccessful();
	}

	public Cursor consulta(String paramString, String[] paramArrayOfString) {
		return db.rawQuery(paramString, paramArrayOfString);
	}

	public void insertar(String paramString) {
		db.execSQL(paramString);
	}

	public void rollback() {
		db.endTransaction();
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context paramContext) {
			super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		}

		public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
		}
	}
}