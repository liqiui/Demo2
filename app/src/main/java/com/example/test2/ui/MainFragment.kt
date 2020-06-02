package com.example.test2.ui.main

import android.content.Intent
import com.example.test2.model.TransportInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.test2.model.Location
import com.example.test2.R
import com.example.test2.databinding.MainFragmentBinding
import com.example.test2.ui.MapActivity
import com.example.test2.viewModel.MainViewModel
import com.google.android.gms.maps.model.LatLng

const val NAME = "com.example.test2.NAME"
const val LONGITUDE = "com.example.test2.LONGITUDE"
const val LATITUDE = "com.example.test2.LATITUDE"
class MainFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var currentLocation: Location
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    val list = listOf(
            Location(name = "Sydney", transportInfo = TransportInfo(car = "40min", train = "50min"), latLng = LatLng(-33.8688, 151.2093)),
            Location(name = "Melbourne", transportInfo = TransportInfo(car = "9hr", train = "14hr"), latLng = LatLng(-37.8136, 144.9631)),
            Location(name = "Newcastle", transportInfo = TransportInfo(car = "140min", train = "250min"), latLng = LatLng(-32.9283, 151.7817)))
    lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
         binding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false);

        binding.lifecycleOwner = this
        context?.let {
            val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(it, android.R.layout.simple_spinner_item,
                    list.map { location -> location.name })
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = arrayAdapter
            binding.spinner.onItemSelectedListener = this

            binding.button.setOnClickListener {
                val intent = Intent(context, MapActivity::class.java).apply {
                    putExtra(NAME, currentLocation.name)
                    putExtra(LONGITUDE, currentLocation.latLng.longitude)
                    putExtra(LATITUDE, currentLocation.latLng.latitude)
                }
                startActivity(intent)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.info.value = """Mode of Transport:
            Car - ${list[0].transportInfo.car}
            Train - ${list[0].transportInfo.train}""".trim()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentLocation = list[position]
        viewModel.info.value = """Mode of Transport:
            Car - ${currentLocation.transportInfo.car}
            Train - ${currentLocation.transportInfo.train}""".trim()
    }

}
