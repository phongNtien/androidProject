package sk.upjs.ics.s.vyletnik.provider;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sk.upjs.ics.s.util.Defaults;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "vyletnik";
    public static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, Defaults.DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSql());
        insertSampleEntry(db, "Tatry", "Pekny vyhlad", "cesta k fotke 1");
        insertSampleEntry(db, "Slovensky Raj", "Pekna priroda", "cesta k fotke 2");
        insertSampleEntry(db, "Spissky hrad", "Zaujimava stavba", "cesta k fotke #");


    }

    private String createTableSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT," //NAME
                + "%s TEXT," //CONTENT
                + "%s DATETIME," //TIMESTAMP
                + "%s TEXT" //PHOTO
                + ")";
        return String.format(sqlTemplate, Provider.Record.TABLE_NAME,
                                            Provider.Record._ID,
                                            Provider.Record.NAME,
                                            Provider.Record.CONTENT,
                                            Provider.Record.TIMESTAMP,
                                            Provider.Record.PHOTO);
    }

    private void insertSampleEntry(SQLiteDatabase db, String name, String content, String photo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Provider.Record.NAME, name);
        contentValues.put(Provider.Record.CONTENT, content);
        contentValues.put(Provider.Record.TIMESTAMP, System.currentTimeMillis() / 1000);
        contentValues.put(Provider.Record.PHOTO, photo);
        db.insert(Provider.Record.TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }
}
