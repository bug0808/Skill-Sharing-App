package com.example.mainactivity.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.adapters.ProfileAdapter;
import com.example.mainactivity.R;
import com.example.mainactivity.classes.Profile;
import com.example.mainactivity.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

        private androidx.appcompat.widget.SearchView searchView;
        private RecyclerView recyclerView;
        private FloatingActionButton fabSearch;
        private ProfileAdapter profileAdapter;
        private List<Profile> profileList;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_first, container, false);


            fabSearch = view.findViewById(R.id.fab_search);

            // Set up RecyclerView
            profileList = new ArrayList<>();
            profileAdapter = new ProfileAdapter(profileList);
            //recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            //recyclerView.setAdapter(profileAdapter);

            // Handle FAB click to toggle search
            //fabSearch.setOnClickListener(v -> toggleSearchView());

            // Listen for search query text changes
            /** searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchProfiles(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (TextUtils.isEmpty(newText)) {
                        hideRecyclerView();
                    } else {
                        searchProfiles(newText);
                    }
                    return true;
                }
            });

            // Handle SearchView close button
            searchView.setOnCloseListener(() -> {
                hideSearchView();
                return true;
            });

            return view;
        }

        private void toggleSearchView() {
            if (searchView.getVisibility() == View.GONE) {
                searchView.setVisibility(View.VISIBLE);
                searchView.requestFocus();
            } else {
                hideSearchView();
            }
        }

        private void hideSearchView() {
            searchView.setVisibility(View.GONE);
            searchView.setQuery("", false);
            hideRecyclerView();
        }

        private void hideRecyclerView() {
            recyclerView.setVisibility(View.GONE);
            profileList.clear();
            profileAdapter.notifyDataSetChanged();
        }

        private void searchProfiles(String query) {
            profileList.clear();

            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            // Modify the query to only get id, firstName, and lastName, ordered by lastName
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                    "SELECT id, first_name, last_name FROM users WHERE first_name LIKE ? ORDER BY last_name ASC",
                    new String[]{"%" + query + "%"}
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
                    String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                    // Create Profile object with id, firstName, and lastName only
                    profileList.add(new Profile(id, firstName, lastName));
                }
                cursor.close();
            }

            if (!profileList.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
            }

            profileAdapter.notifyDataSetChanged();
        } **/
            return view;
    }
}

