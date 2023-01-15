package com.svalero.caroffice.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.caroffice.R;
import com.svalero.caroffice.RegCarActActivity;
import com.svalero.caroffice.RegisterCarActivity;
import com.svalero.caroffice.ViewCarActivity;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.TaskHolder>{

    private List<Car> carList;
    //esto sirve para guardar la posición para luego poder hacer cosas con ellos.
    private Context context;

    private int selectedPosition;

    public CarAdapter(Context context, List<Car> dataList) {
        this.context = context;
        this.carList = dataList;

        //esto indica que no hay ninguno seleccionado
        //selectedPosition = -1;
    }

    //Patron Holder (ESTO ESTOY OBLIGADO A HACERLO SIEMPRE)
    //metodo que crea cada estructura de layout donde iran los datos de cada tarea.
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }

    //hace corresponder cada elemento de la lista para decir como pintarlo en cada elemento del layout
    @Override
    public void onBindViewHolder(TaskHolder holder, int position){
        holder.taskName.setText(carList.get(position).getName());
        holder.taskDescription.setText(carList.get(position).getDescription());
        holder.taskDone.setChecked(carList.get(position).isDone());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder{
        public TextView taskName;
        public TextView taskDescription;
        public CheckBox taskDone;
        public Button doTaskButton;
        public Button seeDetailsButton;
        public Button deleteTaskButton;
        public View parentView;
        public Button modifyRareaButton;

        public TaskHolder(View view) {
            super(view);
            parentView = view;

            taskName = view.findViewById(R.id.task_name);
            taskDescription = view.findViewById(R.id.task_description);
            taskDone = view.findViewById(R.id.check_task_done);
            doTaskButton = view.findViewById(R.id.do_task_button);
            seeDetailsButton = view.findViewById(R.id.see_details_button);
            deleteTaskButton = view.findViewById(R.id.delete_task_button);

            modifyRareaButton = view.findViewById(R.id.buttonUpdate);



            //Programar boton ver detalles de la tarea
            doTaskButton.setOnClickListener(v -> doTask(getAdapterPosition()));

            //Programar boton marcar tarea como hecha.
            seeDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));

            //click on button (remove task from de list).
            deleteTaskButton.setOnClickListener(v -> deleteTask(getAdapterPosition()));

            modifyRareaButton.setOnClickListener(v-> modifyTarea(getAdapterPosition()));
        }

        private void doTask(int position){
            Car car = carList.get(position);
            car.setDone(true);

            final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "tasks")
                    .allowMainThreadQueries().build();
            db.taskDao().update(car);

            notifyItemChanged(position);

        }

        private void seeDetails(int position){
            Car car = carList.get(position);

            Intent intent = new Intent(context, ViewCarActivity.class);
            intent.putExtra("name", car.getName());
            context.startActivity(intent);


        }

        private void deleteTask(int position){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("are_you_sure_msg")
                    .setTitle("Delete element")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "tareas")
                                .allowMainThreadQueries().build();
                        Car car = carList.get(position);
                        db.taskDao().delete(car);

                        carList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("no", (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            //Falta hacer que borre el dato de la base de datos.
        }
        private void modifyTarea(int position) {
            Car car = carList.get(position);

            Intent intent = new Intent(context, RegCarActActivity.class);
            intent.putExtra("name", car.getName());
            context.startActivity(intent);
        }

    }
}
