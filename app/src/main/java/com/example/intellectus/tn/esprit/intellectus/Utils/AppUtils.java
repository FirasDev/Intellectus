package com.example.intellectus.tn.esprit.intellectus.Utils;

import androidx.annotation.NonNull;

public class AppUtils {

    public static String SERVER_URL = "http://intellectus.eu-4.evennode.com/";

    public static String CAPITALIZETXT(@NonNull String input) {

        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (i > 0 && word.length() > 0) {
                builder.append(" ");
            }

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }
}
