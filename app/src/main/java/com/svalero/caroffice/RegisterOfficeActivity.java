package com.svalero.caroffice;

import static com.svalero.caroffice.db.Constants.DATABASE_NAME;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Office;

public class RegisterOfficeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_office);
    }

    public void registerTask(View view) {
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText ownerEditText = findViewById(R.id.ownerEditText);

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String owner = ownerEditText.getText().toString();

        Office office = new Office(name, description, owner, false);
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        try {
            db.officeDao().insert(office);

            Toast.makeText(this, "Oficina registrada", Toast.LENGTH_LONG).show();
            nameEditText.setText("");
            descriptionEditText.setText("");
            ownerEditText.setText("");
            nameEditText.requestFocus();
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(nameEditText, "An error has ocurred. Check that data is valid", BaseTransientBottomBar.LENGTH_LONG).show();
            //Toast.makeText(this, R.string.office_registered_error, Toast.LENGTH_LONG).show();
        }
    }

    public void cancel(View view) {
        onBackPressed();
    }
}

