package sk.upjs.ics.s.vyletnik;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPhotoFragment extends Fragment {

    ImageView photoIV;

    public ViewPhotoFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String photo = getActivity().getIntent().getStringExtra("photo");
        View myInfalterView = inflater.inflate(R.layout.fragment_view_photo, container, false);
        photoIV = (ImageView) myInfalterView.findViewById(R.id.photoImageView);
        Uri myPhotoUri = Uri.parse(photo);
        photoIV.setImageURI(myPhotoUri);

        return myInfalterView;
    }


}
