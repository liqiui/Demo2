package com.example.test2.model

import com.google.android.gms.maps.model.LatLng

data class Location(
    var name : String,
    val transportInfo: TransportInfo,
    var latLng: LatLng
) {
}