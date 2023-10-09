package com.example.proyecto.ui.home;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.R;
import com.example.proyecto.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> deviceListAdapter;
    private ArrayList<BluetoothDevice> devices;
    private BluetoothSocket bluetoothSocket;

    private OutputStream outputStream;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devices = new ArrayList<>();
        deviceListAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);

        //ListView listView = root.findViewById(R.id.deviceListView);
        //listView.setAdapter(deviceListAdapter);

        if (bluetoothAdapter == null) {
            // Bluetooth no está disponible en este dispositivo
            // Puedes mostrar un mensaje de error o tomar medidas apropiadas
            return null;
        }

        // Verifica si el Bluetooth está habilitado
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, 1);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // Método para establecer la conexión Bluetooth con un dispositivo seleccionado
    private void connectToDevice(int position) {
        try {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                // Si no tienes los permisos, solicítalos al usuario
                //ActivityCompat.requestPermissions(requireContext(), new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, 1);
            }
            BluetoothDevice selectedDevice = devices.get(position);
            bluetoothSocket = selectedDevice.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            // Aquí puedes enviar y recibir datos a través del socket Bluetooth
        } catch (IOException e) {
            e.printStackTrace();
            // Maneja las excepciones apropiadamente
        }
    }

    // Método para enviar un comando al dispositivo LED
    private void sendCommand(String command) {
        if (bluetoothSocket != null) {
            try {
                outputStream = bluetoothSocket.getOutputStream(); // Obtiene el flujo de salida del socket Bluetooth
                outputStream.write(command.getBytes()); // Envía el comando como bytes
                outputStream.flush(); // Limpia el flujo de salida
            } catch (IOException e) {
                e.printStackTrace();
                // Maneja las excepciones apropiadamente
            }
        }
    }

}