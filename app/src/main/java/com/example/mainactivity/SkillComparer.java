package com.example.mainactivity;
import com.example.mainactivity.classes.UserSkills;
import java.util.ArrayList;
import java.util.List;

public class SkillComparer {

    public static List<UserSkills> findMostSimilarUsers(UserSkills targetUser, List<UserSkills> allUsers) {
        List<String> allSkills = SimilarityUtil.getAllUniqueSkills(allUsers);

        int[] targetVector = SimilarityUtil.getSkillVector(targetUser, allSkills);

        List<UserSkills> similarUsers = new ArrayList<>();
        for (UserSkills user : allUsers) {
            if (user.getUserId() != targetUser.getUserId()) {
                int[] userVector = SimilarityUtil.getSkillVector(user, allSkills);

                double similarity = SimilarityUtil.cosineSimilarity(targetVector, userVector);

                if (similarity > 0) {
                    user.setSimilarityScore(similarity);
                    similarUsers.add(user);
                }
            }
        }
        similarUsers.sort((u1, u2) -> Double.compare(u2.getSimilarityScore(), u1.getSimilarityScore()));
        return similarUsers;
    }
}