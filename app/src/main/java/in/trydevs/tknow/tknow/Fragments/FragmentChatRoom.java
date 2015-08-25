package in.trydevs.tknow.tknow.Fragments;


import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.trydevs.tknow.tknow.Adapters.MyAdapterPost;
import in.trydevs.tknow.tknow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChatRoom extends Fragment {

    MyAdapterPost adapterPost;
    RecyclerView recyclerView;
    View view;
    private BroadcastReceiver mNewMessageBroadcastReciever;


    public FragmentChatRoom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }


}
