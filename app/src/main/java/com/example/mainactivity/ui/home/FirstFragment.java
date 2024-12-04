    package com.example.mainactivity.ui.home;

    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.util.Log;
    import android.view.GestureDetector;
    import android.view.LayoutInflater;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.mainactivity.activities.ProfileActivity;
    import com.example.mainactivity.adapters.ProfileAdapter;
    import com.example.mainactivity.R;
    import com.example.mainactivity.adapters.ProfileSimAdapter;
    import com.example.mainactivity.classes.Profile;
    import com.example.mainactivity.DatabaseHelper;
    import com.example.mainactivity.classes.User;
    import com.example.mainactivity.classes.UserSkills;
    import com.example.mainactivity.SimilarityUtil;
    import com.example.mainactivity.fragments.ProfileFragment;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

    public class FirstFragment extends Fragment {

        private RecyclerView simRec, oppRec;
        private int userId, profUserId;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_first, container, false);

            if (getArguments() != null){
                userId = getArguments().getInt("currUserId");
                profUserId = getArguments().getInt("profUserId");
                Log.d("FirstFragment", "currUserId: " + userId + ", profUserId: " + profUserId);
        }
            Log.d("FirstFragment", "userId: " + userId + ", profUserId: " + profUserId);
            simRec = view.findViewById(R.id.simRec);
            simRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            oppRec = view.findViewById(R.id.oppRec);
            oppRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            loadSimilarProfiles(userId);
            return view;
        }

        private void loadSimilarProfiles(int userID) {
            DatabaseHelper db = new DatabaseHelper(getContext());
            SQLiteDatabase database = db.getReadableDatabase();

            List<String> skills = db.getUserSkills(database, userID);
            UserSkills currentUserSkill = new UserSkills(userID, skills);
            if (currentUserSkill == null) {
                Log.e("FirstFragment", "Error: currentUserSkill is null for userID: " + userID);
                return;
            }

            List<UserSkills> allUserSkills = db.getAllUsersWithSkills();
            if (allUserSkills == null || allUserSkills.isEmpty()) {
                Log.e("FirstFragment", "Error: No users found in the database.");
                return;
            }

            SimilarityUtil.updateSimilarityScores(allUserSkills);
            List<UserSkills> topMatches = SimilarityUtil.getTopMatches(currentUserSkill, allUserSkills, allUserSkills.size());
            if (topMatches == null || topMatches.isEmpty()) {
                Log.e("FirstFragment", "Error: No top matches found.");
                return;
            }

            List<UserSkills> firstTen = topMatches.subList(0, topMatches.size() / 2);
            List<Profile> firstTenProfiles = convertToProfiles(firstTen);
            ProfileSimAdapter simRecAdapter = new ProfileSimAdapter(firstTenProfiles);
            simRec.setAdapter(simRecAdapter);

            List<UserSkills> lastTen = topMatches.subList(topMatches.size() / 2, topMatches.size());
            Collections.sort(lastTen);
            List<Profile> lastTenProfiles = convertToProfiles(lastTen);
            ProfileSimAdapter oppRecAdapter = new ProfileSimAdapter(lastTenProfiles);
            oppRec.setAdapter(oppRecAdapter);

            for(UserSkills prof : firstTen) {
                Log.d("Similarities", prof.getUserId() + String.valueOf(prof.getSimilarityScore()));
            }
            for(UserSkills prof : lastTen) {
                Log.d("Similarities", prof.getUserId() + String.valueOf(prof.getSimilarityScore()));
            }

            // Manually set click listeners for simRec and oppRec items
            setRecyclerViewItemClickListener(simRec, firstTenProfiles);
            setRecyclerViewItemClickListener(oppRec, lastTenProfiles);
        }

        private void setRecyclerViewItemClickListener(RecyclerView recyclerView, List<Profile> profiles) {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && gestureDetector.onTouchEvent(e)) {
                        int position = rv.getChildAdapterPosition(childView);
                        Profile profile = profiles.get(position);

                        Bundle bundle = new Bundle();
                        bundle.putInt("currUserId", userId);
                        bundle.putInt("profUserId", profile.getId());

                        ProfileFragment profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, profileFragment)
                                .addToBackStack(null)
                                .commit();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                }
            });
        }

        private boolean onTouch(View childView, Profile profile) {
            // Handle profile click
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            bundle.putInt("profUserId", profile.getId());

            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, profileFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        private List<Profile> convertToProfiles(List<UserSkills> userSkills) {
            List<Profile> profiles = new ArrayList<>();
            DatabaseHelper db = new DatabaseHelper(getContext());

            for (UserSkills userSkill : userSkills) {
                if (userSkill.getUserId() != userId) {
                    User user = db.getUserByPersonalId(userSkill.getUserId());
                    Profile profile = new Profile(user.getPersonalId(), user.getFirstName(), user.getLastName());
                    profiles.add(profile);
                }
            }
            return profiles;
        }
    }
