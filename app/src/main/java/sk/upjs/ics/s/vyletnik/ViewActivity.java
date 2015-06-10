package sk.upjs.ics.s.vyletnik;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.Vector;


public class ViewActivity extends FragmentActivity{

    //http://www.javaexperience.com/android-viewpager-example-tutorial/
    private PagerAdapter mPagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this,ViewNameContentFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,ViewPhotoFragment.class.getName()));
        pager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragments);

        pager.setAdapter(mPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
