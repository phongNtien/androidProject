package sk.upjs.ics.s.vyletnik;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.Timestamp;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewNameContentFragment extends Fragment {

    TextView nameTV;
    TextView contentTV;
    TextView dateTV;


    public ViewNameContentFragment() {
        // Required empty public constructor
    }

    //http://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        String name = getActivity().getIntent().getStringExtra("name");
        String content = getActivity().getIntent().getStringExtra("content");
        Long timeStamp = getActivity().getIntent().getLongExtra("timeStamp", 0); //1L

        View myInfalterView = inflater.inflate(R.layout.fragment_view_name_content, container, false);
        nameTV =  (TextView) myInfalterView.findViewById(R.id.nameTextView);
        nameTV.setText(name);
        dateTV =  (TextView) myInfalterView.findViewById(R.id.dateTextView);
        Date date = new Date(timeStamp*1000);
        dateTV.setText(date.toString());
        contentTV =  (TextView) myInfalterView.findViewById(R.id.contentTextView);
        contentTV.setText(content);

        return myInfalterView;
    }


}
