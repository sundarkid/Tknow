package in.trydevs.tknow.tknow.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.trydevs.tknow.tknow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentImages extends Fragment {


    public FragmentImages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false);
    }


}
