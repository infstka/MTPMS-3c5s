package com.mtpms.lr6;

import static java.util.Collections.sort;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Lesson> adapter;
    private List<Lesson> lessons;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_new :
                addContact();
                return true;
            case R.id.sort:
                sort(lessons);
                adapter.notifyDataSetChanged();
            case R.id.up:
                up();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void up() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    private void addContact() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

}