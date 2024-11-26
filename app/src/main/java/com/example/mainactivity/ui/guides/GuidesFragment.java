package com.example.mainactivity.ui.guides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.classes.Guide;
import com.example.mainactivity.GuideAdapter;
import com.example.mainactivity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class GuidesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GuideAdapter adapter;
    private List<Guide> guideList;
    private GuidesViewModel guidesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        guidesViewModel = new ViewModelProvider(this).get(GuidesViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_guides);
        guideList = new ArrayList<>();
        adapter = new GuideAdapter(guideList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabAddGuide = view.findViewById(R.id.fab_add_guide);
        fabAddGuide.setOnClickListener(v -> showAddGuideDialog());

        guidesViewModel.getGuideList().observe(getViewLifecycleOwner(), updatedList -> {
            guideList.clear();
            guideList.addAll(updatedList);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    // Show the dialog to add a new guide
    private void showAddGuideDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Guide");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_guide, null);
        builder.setView(dialogView);

        EditText inputTitle = dialogView.findViewById(R.id.input_title);
        EditText inputDescription = dialogView.findViewById(R.id.input_description);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = inputTitle.getText().toString();
            String description = inputDescription.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                guidesViewModel.addGuide(new Guide(title, description));

                Snackbar.make(requireView(), "Guide added successfully!", Snackbar.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Please enter both title and description.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}