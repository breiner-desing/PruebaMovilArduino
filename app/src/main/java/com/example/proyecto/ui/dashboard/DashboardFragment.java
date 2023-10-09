package com.example.proyecto.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.R;
import com.example.proyecto.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private List<String> selectedShapes = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //GridLayout gridLayout = root.findViewById(R.id.gridLayout);

        //int numCheckBoxes = 16; // Cambia esto al número deseado de CheckBoxes

        //for (int i = 0; i < numCheckBoxes; i++) {
        //    CheckBox checkBox = new CheckBox(requireContext());
        //    checkBox.setId(View.generateViewId()); // Genera un ID único para cada CheckBox
        //    checkBox.setText(""); // Cambia el texto según sea necesario

            // Configura el diseño de cada CheckBox (ancho, alto, etc.) aquí si es necesario

            // Agrega el CheckBox al GridLayout
        //    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        //    checkBox.setLayoutParams(params);
        //    gridLayout.addView(checkBox);
        //}

        Button button2 = root.findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                GridLayout gridLayout = root.findViewById(R.id.gridLayout);

                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    View chil = gridLayout.getChildAt(i);
                    if (chil instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) chil;
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                String shape = checkBox.getText().toString();
                                if (isChecked) {
                                    selectedShapes.add(shape);
                                    System.out.println(shape);
                                } else {
                                    selectedShapes.remove(shape);
                                    System.out.println(shape);
                                }
                            }
                        });
                    }
                }

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}