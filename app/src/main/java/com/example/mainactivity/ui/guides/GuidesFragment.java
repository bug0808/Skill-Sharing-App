package com.example.mainactivity.ui.guides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.Guide;
import com.example.mainactivity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GuidesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GuidesAdapter adapter;
    private List<Guide> guideList;
    private GuidesViewModel guidesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guides, container, false);

        // Initialize ViewModel
        guidesViewModel = new ViewModelProvider(this).get(GuidesViewModel.class);

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_guides);
        guideList = new ArrayList<>();
        adapter = new GuidesAdapter(guideList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Floating Action Button
        FloatingActionButton fabAddGuide = view.findViewById(R.id.fab_add_guide);
        fabAddGuide.setOnClickListener(v -> showAddGuideDialog());

        // Observe Guide List
        guidesViewModel.getGuideList().observe(getViewLifecycleOwner(), updatedList -> {
            guideList.clear();
            guideList.addAll(updatedList);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void showAddGuideDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Guide");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_guide, null);
        builder.setView(dialogView);

        EditText inputTitle = dialogView.findViewById(R.id.input_title);
        EditText inputDescription = dialogView.findViewById(R.id.input_description);
        EditText inputContent = dialogView.findViewById(R.id.input_content); // New input for full content

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = inputTitle.getText().toString();
            String description = inputDescription.getText().toString();
            String content = inputContent.getText().toString();

            if (!title.isEmpty() && !description.isEmpty() && !content.isEmpty()) {
                guidesViewModel.addGuide(new Guide(title, description, content));
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
