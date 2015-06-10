package sk.upjs.ics.s.vyletnik;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import sk.upjs.ics.s.util.Defaults;
import sk.upjs.ics.s.vyletnik.provider.Provider;
import sk.upjs.ics.s.vyletnik.provider.RecordContentProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoGalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int RECORDS_LOADER_ID = 0;
    private SimpleCursorAdapter adapter;
    GridView photosGalleryGV;

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myInflaterView = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        getLoaderManager().initLoader(RECORDS_LOADER_ID,Bundle.EMPTY,this);
        photosGalleryGV = (GridView) myInflaterView.findViewById(R.id.photosGridView);

        photosGalleryGV.setAdapter(initializeAdapter());

        return myInflaterView;
    }

    private ListAdapter initializeAdapter() {
        String[] from = {Provider.Record.PHOTO };
        int[] to = {R.id.imageViewBinder};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.imageviewbinder_layout, Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        adapter.setViewBinder(new ImageViewBinder());         //android.R.layout.simple_list_item_2
        return adapter;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getActivity()); //miesto getActivity bolo this
        loader.setUri(RecordContentProvider.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader loader,  Cursor cursor) {
        this.adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        this.adapter.swapCursor(Defaults.NO_CURSOR);
    }

}
