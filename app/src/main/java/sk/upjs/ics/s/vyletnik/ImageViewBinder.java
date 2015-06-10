package sk.upjs.ics.s.vyletnik;


import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

public class ImageViewBinder implements SimpleCursorAdapter.ViewBinder {



    //http://www.programcreek.com/java-api-examples/index.php?api=android.widget.SimpleCursorAdapter.ViewBinder code example 4
    //http://stackoverflow.com/questions/16666274/set-imageview-on-a-listview-with-viewbinder-depending-on-ratingbar-value
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view instanceof ImageView) {
            String url = cursor.getString(cursor.getColumnIndex("photo"));
            Uri uri = Uri.parse(url);
            ImageView image = (ImageView) view;
            image.setImageURI(uri);
            return true;
        } else {
            // binding neprebehol, nech si ho SimpleCursorAdapter spravi sam
            return false;
        }
    }
}