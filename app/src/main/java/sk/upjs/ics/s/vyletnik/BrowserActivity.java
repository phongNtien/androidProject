package sk.upjs.ics.s.vyletnik;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import sk.upjs.ics.s.util.Defaults;
import sk.upjs.ics.s.vyletnik.provider.Provider;
import sk.upjs.ics.s.vyletnik.provider.RecordContentProvider;

import static sk.upjs.ics.s.util.Defaults.*;


public class BrowserActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemLongClickListener{

    private SimpleCursorAdapter adapter;
    private static final int RECORDS_LOADER_ID = 0;
    private static final int DELETE_NOTE_TOKEN = 0;
    ListView recordsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        getLoaderManager().initLoader(RECORDS_LOADER_ID,Bundle.EMPTY,this);

        recordsListView = (ListView) findViewById(R.id.recordsListView);
        recordsListView.setAdapter(initializeAdapter());
        recordsListView.setOnItemLongClickListener(this);
    }

    private ListAdapter initializeAdapter() {
        String[] from = {Provider.Record.NAME };
        int[] to = {R.id.recordsListViewItem};
        this.adapter = new SimpleCursorAdapter(this, R.layout.record, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        return this.adapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            addClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addButtonClick(View view){
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);
    }

    public void addClick(){
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(RecordContentProvider.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        this.adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(Defaults.NO_CURSOR);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
        Cursor selectedRecordCursor = (Cursor) parent.getItemAtPosition(position);

        final int nameColumnIndex = selectedRecordCursor.getColumnIndex(Provider.Record.NAME);
        final String recordName = selectedRecordCursor.getString(nameColumnIndex);

        int contentColumnIndex = selectedRecordCursor.getColumnIndex(Provider.Record.CONTENT);
        final String recordContent = selectedRecordCursor.getString(contentColumnIndex);

        int timeStampColumnIndex = selectedRecordCursor.getColumnIndex(Provider.Record.TIMESTAMP);
        final long recordTimeStamp = selectedRecordCursor.getLong(timeStampColumnIndex);

        int photoColumnIndex = selectedRecordCursor.getColumnIndex(Provider.Record.PHOTO);
        final String recordPhoto = selectedRecordCursor.getString(photoColumnIndex);

        new AlertDialog.Builder(this)
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),ViewActivity.class);
                        intent.putExtra("name", recordName);
                        intent.putExtra("content", recordContent);
                        intent.putExtra("timeStamp", recordTimeStamp);
                        intent.putExtra("photo", recordPhoto);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord(id);
                    }
                }).show();
        return false;
    }

    private void deleteRecord(final long id) {
        new AlertDialog.Builder(this).setMessage("Delete this record?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncQueryHandler deleteHandler = new AsyncQueryHandler(getContentResolver()) {
                            @Override
                            protected void onDeleteComplete(int token, Object cookie, int result) {
                                Toast.makeText(BrowserActivity.this, "Record was deleted", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        };
                        Uri selectedNoteUri = ContentUris.withAppendedId(RecordContentProvider.CONTENT_URI, id);
                        deleteHandler.startDelete(DELETE_NOTE_TOKEN, NO_COOKIE, selectedNoteUri,
                               NO_SELECTION, NO_SELECTION_ARGS);
            }
        }).setNegativeButton("Cancel", DISMISS_ACTION).show();

    }
}
