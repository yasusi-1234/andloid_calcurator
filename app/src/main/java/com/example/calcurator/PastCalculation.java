package com.example.calcurator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class PastCalculation extends AppCompatActivity {

    Realm realm;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_calcuration);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(realmConfiguration);

        listView = (ListView) findViewById(R.id.past_calc_list);

        RealmResults<CalcModel> results = realm.where(CalcModel.class).findAll();
        RealmResults<CalcModel> sortResults = results.sort("id", Sort.DESCENDING);

        CalcAdapter calcAdapter = new CalcAdapter(sortResults);
        listView.setAdapter(calcAdapter);

        Button backButton = (Button) findViewById(R.id.button_back_main);

        backButton.setOnClickListener(view ->{
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}