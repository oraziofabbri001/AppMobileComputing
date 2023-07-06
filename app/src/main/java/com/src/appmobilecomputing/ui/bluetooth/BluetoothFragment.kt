package com.src.appmobilecomputing.ui.bluetooth

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.appmobilecomputing.MainActivity
import com.src.appmobilecomputing.databinding.FragmentBluetoothBinding
import com.src.appmobilecomputing.databinding.ListviewitemBluetoothBinding


class BluetoothFragment : Fragment() {

    private var _binding: FragmentBluetoothBinding? = null;
    private var _binding_: ListviewitemBluetoothBinding? = null;

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!;
    private val binding_ get() = _binding_!!;
    private var dialogBuilder: AlertDialog.Builder? = null;
    //private var bluetoothDevices = ArrayList<String>();

    @SuppressLint("MissingPermission", "UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bluetoothViewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java);
        _binding = FragmentBluetoothBinding.inflate(inflater, container, false);
        _binding_ = ListviewitemBluetoothBinding.inflate(inflater, container, false);
        val root: View = binding.root;
        bluetoothViewModel.text.observe(viewLifecycleOwner) {

        }

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            /*result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data;
                    // Handle the Intent
                    //do stuff here
                }*/
        };

        dialogBuilder = AlertDialog.Builder(context);

        /*val bluetoothDevices: ArrayList<String> = arrayListOf("primo device","secondo device");
        val bluetoothArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            bluetoothDevices
        )
        binding.listviewBluetooth.adapter = bluetoothArrayAdapter;*/

        /*val users = arrayListOf("Virat Kohli", "Rohit Sharma", "Steve Smith","Kane Williamson", "Ross Taylor");
        var mListView = binding.listviewBluetooth;
        val arrayAdapter = ArrayAdapter(
            requireActivity(),
            com.src.appmobilecomputing.R.layout.listviewitem_bluetooth,
            users
        );
        binding.listviewBluetooth.adapter = arrayAdapter;*/

        /*println("Bluetooth new 1");
        val bluetoothBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                println("Bluetooth new 2");
                val action: String = intent.action as String;
                when(action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        val deviceName = device!!.name;
                        val deviceHardwareAddress = device?.address;
                        println("Bluetooth new name = " + deviceName);
                        //bluetoothDevices.add("New:"+deviceName);
                        Toast.makeText(context, "New device "+deviceName, Toast.LENGTH_SHORT).show();
                        //bluetoothArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
        val bluetoothFilter = IntentFilter(BluetoothDevice.ACTION_FOUND);
        context?.registerReceiver(bluetoothBroadcastReceiver, bluetoothFilter);*/

        val bluetoothDevices = ArrayList<String>();// = arrayListOf("Virat Kohli", "Rohit Sharma", "Steve Smith","Kane Williamson", "Ross Taylor");
        val bluetoothArrayAdapter = ArrayAdapter(
            requireActivity(),
            com.src.appmobilecomputing.R.layout.listviewitem_bluetooth,
            bluetoothDevices
        );
        binding.listviewBluetooth.adapter = bluetoothArrayAdapter;
        binding.listviewBluetooth.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            Toast.makeText(context, ""+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
        }

        binding.buttonloadBluetooth.setOnClickListener {
            view ->
            //if (ContextCompat.checkSelfPermission(requireActivity(), permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
            if (
                //ContextCompat.checkSelfPermission( requireActivity(), android.Manifest.permission.BLUETOOTH ) !=
                    //PackageManager.PERMISSION_GRANTED ||
                //ContextCompat.checkSelfPermission( requireActivity(), android.Manifest.permission.BLUETOOTH_ADMIN ) !=
                    //PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission( requireActivity(), android.Manifest.permission.BLUETOOTH_CONNECT ) !=
                    PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission( requireActivity(), android.Manifest.permission.BLUETOOTH_SCAN) !=
                    PackageManager.PERMISSION_GRANTED
                //ContextCompat.checkSelfPermission( requireActivity(), android.Manifest.permission.BLUETOOTH_ADVERTISE ) !=
                    //PackageManager.PERMISSION_GRANTED ||
                //ContextCompat.checkSelfPermission( requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    //PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf<String>(
                        //android.Manifest.permission.BLUETOOTH,
                        //android.Manifest.permission.BLUETOOTH_ADMIN,
                        android.Manifest.permission.BLUETOOTH_CONNECT,
                        android.Manifest.permission.BLUETOOTH_SCAN,
                        //android.Manifest.permission.BLUETOOTH_ADVERTISE,
                        //android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    PackageManager.PERMISSION_GRANTED
                );
                Toast.makeText(context, "Errore permessi accesso bluetooth", Toast.LENGTH_SHORT).show();
                //bluetoothDevices.add("Paired:test1");
                //bluetoothDevices.add("Paired:test2");
                bluetoothArrayAdapter.notifyDataSetChanged();
            }
            else {
                val bluetoothManager: BluetoothManager? = requireActivity().getSystemService(BluetoothManager::class.java);
                val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter();
                if (bluetoothAdapter == null) {
                    Toast.makeText(context, "Errore Bluetooth, non supportato", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(context, "Bluetooth, supportato", Toast.LENGTH_SHORT).show();
                    if (bluetoothAdapter?.isEnabled == false) {
                        //startForResult?.launch(Intent(requireActivity(), MainActivity::class.java));
                        Toast.makeText(context, "Errore Bluetooth non attivato, attivare Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Inizio ricerca dispositivi Bluetooth", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Bluetooth list inizio", Toast.LENGTH_SHORT).show();
                        bluetoothDevices.clear();
                        //Ricerca tutti i devic che sono stati agganciati precedentemente
                        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices;
                        pairedDevices?.forEach { bluetoothDevice ->
                            val deviceName = bluetoothDevice.name;
                            val deviceHardwareAddress = bluetoothDevice.address;
                            println("Bluetooth paired name = " + deviceName);
                            bluetoothDevices.add("Paired:"+deviceName);
                            //println("bluetoothDevices.size = "+bluetoothDevices.size);
                            //Toast.makeText(context, deviceName, Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(context, "Bluetooth list fine", Toast.LENGTH_SHORT).show();
                        //Ricerca tutti i device nuovi
                        //println("Bluetooth new 1");
                        val bluetoothBroadcastReceiver = object : BroadcastReceiver() {
                            override fun onReceive(context: Context, intent: Intent) {
                                println("Bluetooth new 2");
                                val action: String = intent.action as String;
                                when(action) {
                                    BluetoothDevice.ACTION_FOUND -> {
                                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                        val deviceName = device!!.name;
                                        val deviceHardwareAddress = device?.address;
                                        println("Bluetooth new name = " + deviceName);
                                        bluetoothDevices.add("New:"+deviceName);
                                        Toast.makeText(context, "New device "+deviceName, Toast.LENGTH_SHORT).show();
                                        bluetoothArrayAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                        val bluetoothFilter = IntentFilter(BluetoothDevice.ACTION_FOUND);
                        context?.registerReceiver(bluetoothBroadcastReceiver, bluetoothFilter);
                        if (bluetoothDevices==null || bluetoothDevices.size <= 0) {
                            //Toast.makeText(context, "Bluetooth list vuota", Toast.LENGTH_SHORT).show();
                            //println("Bluetooth list vuota");
                            bluetoothDevices.add("No devise found");
                        }
                        else {
                            //println("Bluetooth list non vuota");
                            //Toast.makeText(context, "Bluetooth list non vuota "+bluetoothDevices.size, Toast.LENGTH_SHORT).show();
                        }
                        //bluetoothDevices.add("Device di test");
                        //println("bluetoothDevices.size prima adaper = "+bluetoothDevices.size);

                        /*val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.activity_list_item,
                            bluetoothDevices
                        )
                        binding.listviewBluetooth.adapter = arrayAdapter;*/

                        //println("bluetoothDevices.size dopo adaper = "+bluetoothDevices.size);
                        bluetoothArrayAdapter.notifyDataSetChanged();
                    }
                }

            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}