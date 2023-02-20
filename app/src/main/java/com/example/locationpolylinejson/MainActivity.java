package com.example.locationpolylinejson;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Marker marker;
    ArrayList<GoogleLocationModel> googleLocationModels = new ArrayList<>();
    ArrayList<LatLng> latLngArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(MainActivity.this);
        CallLocation();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CallLocation();

    }

    private void CallLocation() {
        GoogleLocationInterface googleLocationInterface = APIClient.getClient().create(GoogleLocationInterface.class);
        googleLocationInterface.CallRepot().enqueue(new Callback<GoogleLocationResponse>() {
            @Override
            public void onResponse(Call<GoogleLocationResponse> call, Response<GoogleLocationResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        latLngArrayList.clear();
                        googleLocationModels = response.body().getGoogleLocationModels();
                        Log.e(TAG, "onResponse: 2   " + googleLocationModels);

                        //Map Location
                        for (int i = 0; i < googleLocationModels.size(); i++) {
                            LatLng location = new LatLng(Double.parseDouble(googleLocationModels.get(i).getLatitude()), Double.parseDouble(googleLocationModels.get(i).getLongitude()));
                            marker = mMap.addMarker(new MarkerOptions().position(location));
                            LatLng latLng = new LatLng(Double.parseDouble(googleLocationModels.get(0).getLatitude()), Double.parseDouble(googleLocationModels.get(0).getLongitude()));
                            marker.setTitle(googleLocationModels.get(i).getPlace());
                            latLngArrayList.add(location);

                            PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngArrayList).
                                    color(Color.RED).width(12);
                            mMap.addPolyline(polylineOptions);

                             mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 18));
                            mMap.getUiSettings().setZoomControlsEnabled(true);

                        }

                    }

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<GoogleLocationResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}