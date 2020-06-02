package com.example.test2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.R
import com.example.test2.model.Location
import com.example.test2.model.TransportInfo
import com.example.test2.ui.main.LATITUDE
import com.example.test2.ui.main.LONGITUDE
import com.example.test2.ui.main.NAME
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var location: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        location = Location(name = intent.getStringExtra(NAME) ?: "Sydney",
            transportInfo = TransportInfo(car = "", train = ""),
        latLng = LatLng(intent.getDoubleExtra(LATITUDE, -33.852),
            intent.getDoubleExtra(LONGITUDE, 151.211)))
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.addMarker(
            MarkerOptions().position(location.latLng)
                .title("Marker in ${location.name}")
        )
        googleMap?.apply {
            moveCamera(CameraUpdateFactory.newLatLng(location.latLng))
            setMinZoomPreference(6.0f);
            setMaxZoomPreference(14.0f);    }
        }
}
