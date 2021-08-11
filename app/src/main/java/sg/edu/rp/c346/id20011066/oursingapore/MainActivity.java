package sg.edu.rp.c346.id20011066.oursingapore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvSinger, tvYear, tvStars;
    EditText etTitle, etSingers, etYear;
    RatingBar ratingBar;
    Button btnInsert, btnList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tvName);
        tvSinger = findViewById(R.id.tvDescribe);
        tvYear = findViewById(R.id.tvSquare);
        tvStars = findViewById(R.id.tvStars);
        ratingBar = findViewById(R.id.ratingBar);
        etTitle = findViewById(R.id.etName);
        etSingers = findViewById(R.id.etDescribe);
        etYear = findViewById(R.id.etYear);



        btnInsert = findViewById(R.id.btnInsert);
        btnList = findViewById(R.id.btnList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                if (title.length() == 0 || singers.length() == 0) {
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String year_str = etYear.getText().toString().trim();
                int year = 0;
                try {
                    year = Integer.valueOf(year_str);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbh = new DBHelper(MainActivity.this);
                int stars = getStars();
                dbh.insertIsland(title, singers, year, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();
                etTitle.setText("");
                etSingers.setText("");
                etYear.setText("");

            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });

    }
    private int getStars() {
        int stars = 1;
        stars = (int) ratingBar.getRating();
        return stars;
    }
}