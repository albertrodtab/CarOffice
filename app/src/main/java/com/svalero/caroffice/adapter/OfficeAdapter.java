package com.svalero.caroffice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.caroffice.R;
import com.svalero.caroffice.ViewOfficeActivity;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Office;

import java.util.List;

public class OfficeAdapter extends RecyclerView.Adapter<OfficeAdapter.OfficeHolder> {

    private List<Office> officeList;

    //esto sirve para guardar la posición para luego poder hacer cosas con ellos.
    private Context context;


    public OfficeAdapter(Context context, List<Office> dataList) {
        this.context = context;
        this.officeList = dataList;

        //esto indica que no hay ninguno seleccionado
        //selectedPosition = -1;
    }

    //Patron Holder (ESTO ESTOY OBLIGADO A HACERLO SIEMPRE)
    //metodo que crea cada estructura de layout donde iran los datos de cada tarea.
    @NonNull
    @Override
    public OfficeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.office_item, parent, false);
        return new OfficeHolder(view);
    }

    //hace corresponder cada elemento de la lista para decir como pintarlo en cada elemento del layout
    @Override
    public void onBindViewHolder(@NonNull OfficeHolder holder, int position) {
        holder.officeName.setText(officeList.get(position).getName());
        holder.officeDescription.setText(officeList.get(position).getDescription());
        holder.open_all_day.setChecked(officeList.get(position).isDone());
    }

    @Override
    public int getItemCount() {
        return officeList.size();
    }

    public class OfficeHolder extends RecyclerView.ViewHolder {
        public TextView officeName;
        public TextView officeDescription;
        public CheckBox open_all_day;
        public Button all_day_button;
        public Button seeDetailsButton;
        public Button delete_office_button;
        public View parentView;

        public OfficeHolder(View view) {
            super(view);
            parentView = view;

            officeName = view.findViewById(R.id.office_name);
            officeDescription = view.findViewById(R.id.office_description);
            open_all_day = view.findViewById(R.id.open_all_day);
            all_day_button = view.findViewById(R.id.all_day_button);
            seeDetailsButton = view.findViewById(R.id.see_details_button);
            delete_office_button = view.findViewById(R.id.delete_office_button);

            //Programar boton ver detalles de la tarea
            all_day_button.setOnClickListener(v -> openAllDay(getAdapterPosition()));

            //Programar boton marcar tarea como hecha.
            seeDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));

            //click on button (remove task from de list).
            delete_office_button.setOnClickListener(v -> deleteOffice(getAdapterPosition()));
        }

        private void openAllDay(int position){
            Office office = officeList.get(position);
            office.setDone(true);

            final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "offices")
                    .allowMainThreadQueries().build();
            db.officeDao().update(office);

            notifyItemChanged(position);

        }

        private void seeDetails(int position) {
            Office office = officeList.get(position);

            Intent intent = new Intent(context, ViewOfficeActivity.class);
            intent.putExtra("name", office.getName());
            context.startActivity(intent);
        }

        private void deleteOffice(int position){
            officeList.remove(position);
            notifyItemRemoved(position);

            //Falta hacer que borre el dato de la base de datos.
        }


        }

    }

