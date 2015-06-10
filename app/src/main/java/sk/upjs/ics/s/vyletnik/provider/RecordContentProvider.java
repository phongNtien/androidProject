package sk.upjs.ics.s.vyletnik.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.ContentResolver.SCHEME_CONTENT;
import static sk.upjs.ics.s.util.Defaults.AUTOGENERATED_ID;
import static sk.upjs.ics.s.util.Defaults.NO_CONTENT_OBSERVER;
import static sk.upjs.ics.s.util.Defaults.NO_NULL_COLUMN_HACK;
import static sk.upjs.ics.s.vyletnik.provider.Provider.Record;
import  static sk.upjs.ics.s.util.Defaults.ALL_COLUMNS;
import  static sk.upjs.ics.s.util.Defaults.NO_GROUP_BY;
import  static sk.upjs.ics.s.util.Defaults.NO_HAVING;
import  static sk.upjs.ics.s.util.Defaults.NO_SELECTION;
import  static sk.upjs.ics.s.util.Defaults.NO_SELECTION_ARGS;
import  static sk.upjs.ics.s.util.Defaults.NO_SORT_ORDER;
import  static sk.upjs.ics.s.util.Defaults.NO_TYPE;
import static  sk.upjs.ics.s.vyletnik.provider.Provider.Record.*;

import sk.upjs.ics.s.util.Defaults;

public class RecordContentProvider extends ContentProvider{
    public static final String AUTHORITY = "sk.upjs.ics.s.vyletnik.provider.RecordContentProvider";

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme(SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();


    private static final int URI_MATCH_RECORDS = 0;
    private static final int URI_MATCH_RECORD_BY_ID = 1;

    private static final String MIME_TYPE_RECORDS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + TABLE_NAME;
    private static final String MIME_TYPE_SINGLE_RECORD = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + TABLE_NAME;

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DatabaseOpenHelper databaseHelper;

    @Override
    public boolean onCreate(){
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, URI_MATCH_RECORDS);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", URI_MATCH_RECORD_BY_ID);

        this.databaseHelper = new DatabaseOpenHelper(getContext());

        return  true;
    }

    @Override
    public Cursor query(Uri uri,String[]projection,String selection,
                        String[]selectionArgs,String sortOrder){
        Cursor cursor = null;
        switch(uriMatcher.match(uri)) {
            case URI_MATCH_RECORDS:
                cursor = listNotes();
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case URI_MATCH_RECORD_BY_ID:
                long id = ContentUris.parseId(uri);
                cursor = findById(id);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                return Defaults.NO_CURSOR;
        }
    }

    private Cursor findById(long id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection = Record._ID + "=" + id;
        return db.query(Record.TABLE_NAME, ALL_COLUMNS, selection, NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
    }

    private Cursor listNotes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return db.query(Record.TABLE_NAME, ALL_COLUMNS, NO_SELECTION, NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case URI_MATCH_RECORDS:
                Uri newItemUri = saveRecord(values);
                getContext().getContentResolver().notifyChange(CONTENT_URI, NO_CONTENT_OBSERVER);
                return newItemUri;
            default:
                return Defaults.NO_URI;
        }
    }

        private Uri saveRecord(ContentValues values) {
            ContentValues record = new ContentValues();
            record.put(Record._ID, AUTOGENERATED_ID);
            record.put(Record.NAME, values.getAsString(Record.NAME));
            record.put(Record.CONTENT, values.getAsString(Record.CONTENT));
            record.put(Record.TIMESTAMP, values.getAsLong(Record.TIMESTAMP));
            record.put(Record.PHOTO, values.getAsString(Record.PHOTO));

            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            long newId = db.insert(Record.TABLE_NAME, NO_NULL_COLUMN_HACK, record);
            return ContentUris.withAppendedId(CONTENT_URI, newId);
        }


    @Override
    public int delete(Uri uri,String selection,String[]selectionArgs){
        switch(uriMatcher.match(uri)) {
            case URI_MATCH_RECORD_BY_ID:
                long id = ContentUris.parseId(uri);
                int affectedRows = databaseHelper.getWritableDatabase()
                        .delete(Record.TABLE_NAME, Record._ID + " = " + id, Defaults.NO_SELECTION_ARGS);
                getContext().getContentResolver().notifyChange(CONTENT_URI, NO_CONTENT_OBSERVER);
                return affectedRows;
            default:
                return 0;
        }
        }

    @Override
    public String getType(Uri uri){
    switch(uriMatcher.match(uri)) {
        case URI_MATCH_RECORD_BY_ID:
            return MIME_TYPE_SINGLE_RECORD;
        case URI_MATCH_RECORDS:
            return MIME_TYPE_RECORDS;
    }
    return NO_TYPE;
        }

    @Override
    public int update(Uri uri,ContentValues values,String selection,
        String[]selectionArgs){
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
        }
        }