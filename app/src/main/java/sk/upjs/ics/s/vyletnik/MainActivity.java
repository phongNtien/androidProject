package sk.upjs.ics.s.vyletnik;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void startClick(View view){
        Intent intent = new Intent(this, BrowserActivity.class);
        startActivity(intent);
    }

    public void photosClick(View view){
        Intent intent = new Intent(this, PhotosActivity.class);
        startActivity(intent);
    }

   /* public void compassClick(View view){
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }
    <Button
    style="?android:attr/buttonStyleSmall"
    android:layout_width="90dp"
    android:layout_height="wrap_content"
    android:text="Compass"
    android:id="@+id/RateButton"
    android:layout_alignParentBottom="true"
    android:layout_toRightOf="@+id/StartButton"
    android:layout_toEndOf="@+id/StartButton"
    android:onClick="compassClick"/>*/
}
