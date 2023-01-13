package com.svalero.caroffice;

import static com.svalero.caroffice.db.Constants.DATABASE_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Office;

public class ViewOfficeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_details);
        //
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name == null)
            return;

        // Cargo los detalles de la oficina
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Office office = db.officeDao().getByName(name);
        fillData(office);
    }

    private void fillData(Office office) {
        TextView name = findViewById(R.id.task_name_details);
        TextView description = findViewById(R.id.task_description_details);
        TextView owner = findViewById(R.id.task_owner_details);

        name.setText(office.getName());
        description.setText(office.getDescription());
        owner.setText(office.getOwner());
    }
}
