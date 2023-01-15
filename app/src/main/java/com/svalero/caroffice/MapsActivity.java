package com.svalero.caroffice;

import static com.svalero.caroffice.db.Constants.DATABASE_NAME;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.svalero.caroffice.db.AppDatabase;
import com.svalero.caroffice.domain.Car;

import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.map);
        initializePointManager();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        List<Car> cars = db.taskDao().getAll();
        addTasksToMap(cars);
    }

    private void addTasksToMap(List<Car> cars) {
        for (Car car : cars) {
            Point point = Point.fromLngLat(car.getLongitude(), car.getLatitude());
            addMarker(point, car.getName());
        }

        Car lastCar = cars.get(cars.size() - 1);
        setCameraPosition(Point.fromLngLat(lastCar.getLongitude(), lastCar.getLatitude()));
    }


    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }
    private void addMarker(Point point, String title) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withTextField(title)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alberto_foreground));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void setCameraPosition(Point point) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(13.5)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }
}
