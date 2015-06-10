package sk.upjs.ics.s.vyletnik;

import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.ecotastic.android.camerautil.lib.CameraIntentHelperActivity;
import de.ecotastic.android.camerautil.util.BitmapHelper;
import sk.upjs.ics.s.vyletnik.provider.Provider;
import sk.upjs.ics.s.vyletnik.provider.RecordContentProvider;
import static  sk.upjs.ics.s.util.Defaults.DISMISS_ACTION;
import static  sk.upjs.ics.s.util.Defaults.NO_COOKIE;




public class AddEditActivity extends CameraIntentHelperActivity implements LocationListener{

    private static final int INSERT_NOTE_TOKEN = 0;
    private LocationManager locationManager;
    private static final long TEN_SECONDS = 10 * 1000;
    private static final float ONE_HUNDRED_METERS = 100f;
    private static final boolean ONLY_ENABLED_LOCATION_PROVIDERS = true;
    private String locationProviderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationProviderName = locationManager.getBestProvider(criteria, ONLY_ENABLED_LOCATION_PROVIDERS);
        locationManager.requestLocationUpdates(locationProviderName, TEN_SECONDS, ONE_HUNDRED_METERS, this);
    }

    @Override
    protected void onPause() {
        locationManager.removeUpdates(this);
        super.onPause();
    }

    public void onShootButtonClick(View v) {
        startCameraIntent();
    }

    @Override
    protected void onPhotoUriFound() {
        super.onPhotoUriFound();
        Bitmap photo = BitmapHelper.readBitmap(this, this.photoUri);
        if (photo != null) {
            photo = BitmapHelper.shrinkBitmap(photo, 300, this.rotateXDegrees);
            ImageView imageView = (ImageView) findViewById(R.id.thumbnailImageView);
            imageView.setImageBitmap(photo);
        }

        //Delete photo in second location (if applicable)
        if (this.preDefinedCameraUri != null && !this.preDefinedCameraUri.equals(this.photoUri)) {
            BitmapHelper.deleteImageWithUriIfExists(this.preDefinedCameraUri, this);
        }
        //Delete photo in third location (if applicable)
        if (this.photoUriIn3rdLocation != null) {
            BitmapHelper.deleteImageWithUriIfExists(this.photoUriIn3rdLocation, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveClick() {
        final EditText nameET = (EditText) findViewById(R.id.nameEditText);
        final EditText contentET = (EditText) findViewById(R.id.contentEditText);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        new AlertDialog.Builder(this).
                setTitle("Save a record?").
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameET.getText().toString();
                        String content = contentET.getText().toString();
                        Long timestamp = getTimeStamp(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                        insertIntoContentProvider(name, content, timestamp, getPhotoUri());

                        finish();
                    }
                })
                .setNegativeButton("Cancel", DISMISS_ACTION)
                .show();
    }

    private void insertIntoContentProvider(String name, String content, long timestamp, String photo) {
        Uri uri = RecordContentProvider.CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(Provider.Record.NAME, name);
        values.put(Provider.Record.CONTENT, content);
        values.put(Provider.Record.TIMESTAMP, timestamp);
        values.put(Provider.Record.PHOTO, photo);

        AsyncQueryHandler insertHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(AddEditActivity.this, "Record was saved", Toast.LENGTH_SHORT)
                        .show();
            }
        };

        insertHandler.startInsert(INSERT_NOTE_TOKEN, NO_COOKIE, uri, values);
    }

    //http://stackoverflow.com/questions/14809027/get-long-timestamp-from-date-picker-ondateset
    private Long getTimeStamp(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        return calendar.getTimeInMillis();
    }

    @Override
    public void onLocationChanged(Location location) {
        EditText contentET = (EditText) findViewById(R.id.contentEditText);
        Double altitude = location.getLatitude();
        Double longtitude = location.getLongitude();
        contentET.setText(altitude + " " + longtitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
// v xml scrolovanie
// http://stackoverflow.com/questions/17198902/how-to-add-a-scroll-view-to-an-entire-activity
