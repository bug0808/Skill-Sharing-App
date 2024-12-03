package com.example.mainactivity;
import com.example.mainactivity.classes.UserSkills;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class SimilarityUtil {

    public static List<String> getAllUniqueSkills(List<UserSkills> users) {
        Set<String> uniqueSkills = new HashSet<>();
        for (UserSkills user : users) {
            uniqueSkills.addAll(user.getSkills());
        }
        return new ArrayList<>(uniqueSkills);
    }

    public static int[] getSkillVector(UserSkills user, List<String> allSkills) {
        if (user.getSkills() == null) {
            return new int[allSkills.size()];
        }

        int[] vector = new int[allSkills.size()];
        Set<String> userSkillsLowercase = user.getSkills().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        for (int i = 0; i < allSkills.size(); i++) {
            if (userSkillsLowercase.contains(allSkills.get(i).toLowerCase())) {
                vector[i] = 1;
            } else {
                vector[i] = 0;
            }
        }
        return vector;
    }

    public static double cosineSimilarity(int[] vectorA, int[] vectorB) {
        int dotProduct = 0;
        int normA = 0;
        int normB = 0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }
        if (normA == 0 || normB == 0) return 0.0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static void updateSimilarityScores(List<UserSkills> users) {
        List<String> allSkills = getAllUniqueSkills(users);
        for (UserSkills user : users) {
            int[] userVector = getSkillVector(user, allSkills);
            for (UserSkills otherUser : users) {
                if (!user.equals(otherUser)) {
                    int[] otherVector = getSkillVector(otherUser, allSkills);
                    double similarity = cosineSimilarity(userVector, otherVector);
                    otherUser.setSimilarityScore(similarity);
                }
            }
        }
    }

    public static List<UserSkills> getTopMatches(UserSkills targetUser, List<UserSkills> users, int topN) {
        List<String> allSkills = getAllUniqueSkills(users);
        int[] targetVector = getSkillVector(targetUser, allSkills);

        for (UserSkills user : users) {
            if (!user.equals(targetUser)) {
                int[] userVector = getSkillVector(user, allSkills);
                double similarity = cosineSimilarity(targetVector, userVector);
                user.setSimilarityScore(similarity);
            }
        }

        return users.stream()
                .filter(user -> !user.equals(targetUser))
                .sorted((a, b) -> Double.compare(b.getSimilarityScore(), a.getSimilarityScore()))
                .limit(topN)
                .collect(Collectors.toList());
    }
}