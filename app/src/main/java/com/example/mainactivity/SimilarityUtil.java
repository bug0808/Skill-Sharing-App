package com.example.mainactivity;
import com.example.mainactivity.classes.UserSkills;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimilarityUtil {

    //get all unique skills of all users
    public static List<String> getAllUniqueSkills(List<UserSkills> users) {
        Set<String> uniqueSkills = new HashSet<>();
        for (UserSkills user : users) {
            uniqueSkills.addAll(user.getSkills());
        }
        return new ArrayList<>(uniqueSkills);
    }

    //convert users skills into vector values
    public static int[] getSkillVector(UserSkills user, List<String> allSkills) {
        int[] vector = new int[allSkills.size()];
        for (int i = 0; i < allSkills.size(); i++) {
            if (user.getSkills().contains(allSkills.get(i))) {
                vector[i] = 1;
            } else {
                vector[i] = 0;
            }
        }
        return vector;
    }

    //cosine simularity
    public static double cosineSimilarity(int[] vectorA, int[] vectorB) {
        int dotProduct = 0;
        int normA = 0;
        int normB = 0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
