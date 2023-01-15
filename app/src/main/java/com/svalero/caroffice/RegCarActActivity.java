package com.svalero.caroffice;

import static com.svalero.caroffice.db.Constants.DATABASE_NAME;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Car;

public class RegCarActActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_regis_task);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name == null)
            return;

        // Cargo los detalles de la tarea
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Car car = db.taskDao().getByName(name);
        fillData(car);

    }
    private void fillData(Car car) {
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText ownerEditText = findViewById(R.id.ownerEditText);

        nameEditText.setText(car.getName());
        descriptionEditText.setText(car.getDescription());
        ownerEditText.setText(car.getOwner());
    }
    public void updateTask(View view) {
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText ownerEditText = findViewById(R.id.ownerEditText);

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String owner = ownerEditText.getText().toString();

        Car car = new Car(name, description, owner, false);
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        try {
            db.taskDao().update(name, description, owner);

            Toast.makeText(this, "Tarea registrada", Toast.LENGTH_LONG).show();
            nameEditText.setText("");
            descriptionEditText.setText("");
            ownerEditText.setText("");
            nameEditText.requestFocus();
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(nameEditText, "An error has ocurred. Check that data is valid", BaseTransientBottomBar.LENGTH_LONG).show();
            //Toast.makeText(this, R.string.task_registered_error, Toast.LENGTH_LONG).show();
        }
    }

}
