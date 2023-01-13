package com.svalero.caroffice;

import static com.svalero.caroffice.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.svalero.caroffice.adapter.OfficeAdapter;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Office;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Office> officeList = new ArrayList<>();
    private OfficeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        officeList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.office_list);
        //esto le dice que tenga un tamaño fijo y que ocupe el máximo espacio asiganado
        recyclerView.setHasFixedSize(true);
        //Esto le dice que lo va a gestionar un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //así se ciñe al Layout manager
        recyclerView.setLayoutManager(layoutManager);
        //hago mi adapter propio no utilizo el arrayadapter de android
        adapter = new OfficeAdapter(this, officeList);
        recyclerView.setAdapter(adapter);


       /* Button continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        officeList.clear();
        officeList.addAll(db.officeDao().getAll());
        // con esto la lista siempre estára actualizada cuando vuelva de un segundo plano.
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.registerOffice) {
            //Con Intent de digo donde estoy y a donde quiero ir
            Intent intent = new Intent(this, RegisterOfficeActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
