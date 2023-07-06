package com.src.appmobilecomputing.ui.home

import android.app.AlertDialog
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.appmobilecomputing.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),LocationListener {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var locationManager: LocationManager;
    private val locationPermissionCode = 2;
    private var dialogBuilder: AlertDialog.Builder? = null;
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java);

        _binding = FragmentHomeBinding.inflate(inflater, container, false);
        val root: View = binding.root;

        dialogBuilder = AlertDialog.Builder(context);

        binding.buttonLoadCoordinates.setOnClickListener {
            automatizeLocation("2");
        }

        automatizeLocation("1");

        return root;
    }

    private fun automatizeLocation(operator: String) {
        //binding.textHome.text = "Caricamento in corso....attendere";
        Toast.makeText(context, "Inizio ricerca coordinate GPS", Toast.LENGTH_SHORT).show();
        binding.LatitudineValue.text = "xxx.xxx";
        binding.LongitudineValue.text = "xxx.xxx";
        if (
            ContextCompat.checkSelfPermission( requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) !=
                PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission( requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
               0
            );
            binding.LatitudineValue.text = "";
            binding.LongitudineValue.text = "";
            Toast.makeText(context, "Errore permessi accesso GPS", Toast.LENGTH_SHORT).show();
        }
        else {
            locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager;
            if ( !locationManager!!.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                dialogBuilder!!.setMessage("Errore GPS, attivare il segnale")
                .setCancelable(false)
                .setPositiveButton("CHIUDI", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() });
                val alert = dialogBuilder!!.create();
                alert.setTitle("Errore GPS");
                alert.show();
                binding.LatitudineValue.text = "";
                binding.LongitudineValue.text = "";
            }
            else {
                //if (operator.equals("1")) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        3000,
                        5f,
                        this
                    );
                    //binding.textHome.text = "";
                /*}
                else if (operator.equals("2")) {
                    var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
                    mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                        val location: Location? = task.result;
                        if (location != null) {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault());
                            val list: List<Address> =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>;
                            binding?.textHome?.text = "Latitudine: " + list[0].latitude + "\n"+
                                                      "Longitudine:" + list[0].longitude;
                        }
                    }
                }*/
            }
        }
    }
    override fun onPause() {
        super.onPause();
        System.out.println("onPause() HomeFragment()");
        locationManager.removeUpdates(this);
    }
    override fun onResume() {
        super.onResume();
        System.out.println("onResume() HomeFragment()");
        automatizeLocation("1");
    }
    override fun onLocationChanged(location: Location) {
        if (
            binding != null &&
            binding.LatitudineValue != null &&
            binding.LatitudineValue.text != null &&
            binding.LongitudineValue != null &&
            binding.LongitudineValue.text != null
        ) {
            binding.LatitudineValue.text = ""+location.latitude;
            binding.LongitudineValue.text = ""+location.longitude;
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView();
        _binding = null;
    }
}