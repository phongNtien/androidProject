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
import android.widget.ImageView;

import sk.upjs.ics.s.util.Defaults;
import sk.upjs.ics.s.vyletnik.provider.RecordContentProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoPreviewFragment extends Fragment{

    private ImageView previewImageView;


    public PhotoPreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View photoPreviewFragmenLayout = inflater.inflate(R.layout.fragment_photo_preview, container, false);
        this.previewImageView = (ImageView) photoPreviewFragmenLayout.findViewById(R.id.photoImageView);
        getArguments().getStringArrayList("tag");
        /*TODO pridat fotku cez uri do ImageView ako vo ViewPhotoFragment*/
        return photoPreviewFragmenLayout;
    }
}
