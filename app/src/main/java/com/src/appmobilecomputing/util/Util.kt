package com.src.appmobilecomputing.util

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.src.appmobilecomputing.MainActivity

class Util() { /*val mainActivity: MainActivity*/
    @SuppressLint("MissingPermission")
    fun returnBluetoothDevicesList() : ArrayList< ArrayList<String> > {
        val bluetoothDevices = ArrayList< ArrayList<String> >();
        /*val startForResult = mainActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data;
                }
        };
        val bluetoothManager: BluetoothManager = mainActivity.getSystemService(BluetoothManager::class.java);
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            println("Util() returnBluetoothDevicesList() Bluetooth not support");
        }
        else {
            println("Util() returnBluetoothDevicesList() Bluetooth support !!!");
            if (bluetoothAdapter?.isEnabled == false) {
                startForResult.launch(Intent(mainActivity, MainActivity::class.java));
                println("Util() returnBluetoothDevicesList() Bluetooth not enabled");
            }
            else {
                println("Util() returnBluetoothDevicesList() Bluetooth enabled !!!");
                println("Util() returnBluetoothDevicesList() Bluetooth list device start");
                val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices;
                pairedDevices?.forEach {
                        bluetoothDevice ->
                    val deviceName = bluetoothDevice.name;
                    val deviceHardwareAddress = bluetoothDevice.address;
                    val bluetoothDevice = ArrayList<String>(2);
                    println("Util() returnBluetoothDevicesList() Bluetooth name = "+deviceName);
                    bluetoothDevices.add(bluetoothDevice);
                }
                println("Util() returnBluetoothDevicesList() Bluetooth list device stop");
            }
        }*/
        return bluetoothDevices;
    }
}