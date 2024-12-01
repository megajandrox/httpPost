package com.http.post.view.popup.strategy;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class LevenshteinSimilarity implements URLSimilarityStrategy {

    private final LevenshteinDistance levenshtein = new LevenshteinDistance();

    @Override
    public boolean areSimilar(String url1, String url2) {
        int levDistance = levenshtein.apply(url1, url2);
        if(levDistance >= 0 && levDistance <= 20) {
            return true;
        }
        return false;
    }
}
