package in.trydevs.tknow.tknow.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.trydevs.tknow.tknow.Adapters.MyAdapterPost;
import in.trydevs.tknow.tknow.DataClasses.Post;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.MyApplication;
import in.trydevs.tknow.tknow.extras.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClub extends Fragment {

    MyAdapterPost adapterPost;
    RecyclerView recyclerView;
    View view;
    private BroadcastReceiver mNewMessageBroadcastReciever;

    public FragmentClub() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_club, container, false);
        initialize();
        return view;
    }


    private void initialize() {
        mNewMessageBroadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Post post = intent.getParcelableExtra(getString(R.string.newMessage_broadcast));
                if (post != null)
                    adapterPost.NewDataAdded(post);
            }
        };
        // Setting up Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        List<Post> data = new ArrayList<>();
        MyApplication.getWritableDatabase().insertPostData(data);
        adapterPost = new MyAdapterPost(getActivity(), MyApplication.getWritableDatabase().getPostData());
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getAppContext()));
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        recyclerView.setAdapter(adapterPost);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MyApplication.getAppContext()).unregisterReceiver(mNewMessageBroadcastReciever);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(MyApplication.getAppContext()).registerReceiver(mNewMessageBroadcastReciever,
                new IntentFilter(getString(R.string.newMessage_broadcast)));
    }

}
