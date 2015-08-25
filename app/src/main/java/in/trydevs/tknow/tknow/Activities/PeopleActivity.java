package in.trydevs.tknow.tknow.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import in.trydevs.tknow.tknow.Adapters.MyAdapterPeople;
import in.trydevs.tknow.tknow.R;
import in.trydevs.tknow.tknow.extras.MyApplication;
import in.trydevs.tknow.tknow.extras.SpacesItemDecoration;

public class PeopleActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPeople);
        MyAdapterPeople adapterPeople = new MyAdapterPeople(PeopleActivity.this, MyApplication.getWritableDatabase().getPeopleData());
        recyclerView.setLayoutManager(new LinearLayoutManager(PeopleActivity.this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        recyclerView.setAdapter(adapterPeople);
        // Setting Top bar 'Tool bar'
        toolbar = (Toolbar) findViewById(R.id.top_bar_people_activity);
        setSupportActionBar(toolbar);
    }

}
