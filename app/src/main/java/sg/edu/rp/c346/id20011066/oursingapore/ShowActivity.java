package sg.edu.rp.c346.id20011066.oursingapore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    Button btnFilter;
    Spinner spinnerYear;
    ListView lvSongs;

    Island island;

    ArrayList<Island> alIsland;
    //ArrayAdapter<Song> aaSong;

    ArrayList<String> SqKM;
    ArrayAdapter<String> spinnerAdapter;

    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        DBHelper dbHelper = new DBHelper(ShowActivity.this);

        btnFilter = findViewById(R.id.btnFilter);
        spinnerYear = findViewById(R.id.spinnerYear);
        lvSongs = findViewById(R.id.lvSongs);

        Intent i = getIntent();


        alIsland = new ArrayList<Island>();
        alIsland.addAll(dbHelper.getAllIsland());

        adapter = new CustomAdapter(ShowActivity.this, R.layout.row, alIsland);
        lvSongs.setAdapter(adapter);

        DBHelper dbh = new DBHelper(ShowActivity.this);
        SqKM = dbh.getSqKM();
        SqKM.add("All");

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, SqKM);
        spinnerYear.setAdapter(spinnerAdapter);
        spinnerYear.setSelection(SqKM.size() - 1);

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Island song1 = alIsland.get(position);
                Intent intent = new Intent(ShowActivity.this, EditActivity.class);
                intent.putExtra("song", song1);
                startActivity(intent);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper1 = new DBHelper(ShowActivity.this);
                alIsland.clear();
                alIsland.addAll(dbHelper1.getAllIslandByStars("5"));
                adapter.notifyDataSetChanged();
            }
        });


        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dbHelper.close();
                alIsland.clear();
                if(spinnerYear.getSelectedItem().toString().equalsIgnoreCase("all")){
                    alIsland.addAll(dbh.getAllIsland());
                } else {
                    alIsland.addAll(dbh.getAllIslandByYear(Integer.parseInt(spinnerYear.getSelectedItem().toString())));
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ShowActivity.this);
        alIsland.clear();
        alIsland.addAll(dbh.getAllIsland());
        adapter.notifyDataSetChanged();
    }
}