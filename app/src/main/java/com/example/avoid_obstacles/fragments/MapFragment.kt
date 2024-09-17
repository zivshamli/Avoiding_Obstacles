package com.example.avoid_obstacles.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.avoid_obstacles.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment()  {


    private var map: GoogleMap? = null
    private val callback = OnMapReadyCallback { googleMap ->
        // Add a marker near Sydney, Australia
        val sydney = LatLng(-33.86947276307224, 151.2101295790259)
        map = googleMap
        map?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun changeLocation(latitude: Double, longitude: Double) {
        val latLng = LatLng(latitude, longitude)
        if (map != null) {
            map?.addMarker(MarkerOptions().position(latLng).title("Player position"))
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f))
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_ID) as? SupportMapFragment
        mapFragment?.getMapAsync(callback)
    }


}