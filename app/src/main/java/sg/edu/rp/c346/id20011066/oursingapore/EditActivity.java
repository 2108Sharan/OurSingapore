package sg.edu.rp.c346.id20011066.oursingapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {


    Button btnUpdate, btnDelete, btnCancel;
    EditText etName, etDesc, etSqKM, etID;
    RatingBar rb;
    Island island;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDescribe);
        etSqKM = findViewById(R.id.etSquare);
        etID = findViewById(R.id.etID);
        rb = findViewById(R.id.ratingBar2);

        Intent i = getIntent();
        island = (Island) i.getSerializableExtra("song");
        etID.setText(island.getId() + "");
        etName.setText(island.getName());
        etDesc.setText(island.getDescription());
        etSqKM.setText(island.getSqKM() + "");
        rb.setRating((float)island.getStars());


        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                island.setName(etName.getText().toString().trim());
                island.setDescription(etDesc.getText().toString().trim());
                int num = 0;
                try {
                    num = Integer.valueOf(etSqKM.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(EditActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                island.setSqKM(num);

                island.setStars((int) rb.getRating());
                int result = dbh.updateIsland(island);
                if (result > 0) {
                    Toast.makeText(EditActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to island\n" + island.getName());
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(EditActivity.this);
                        dbHelper.deleteIsland(island.getId());
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes" );
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                myBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }
}