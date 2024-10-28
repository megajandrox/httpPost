package com.http.post.view.popup.strategy;

import org.apache.commons.text.similarity.JaccardSimilarity;

public class JaccardCheckSimilarity implements URLSimilarityStrategy {

    private final JaccardSimilarity jaccard = new JaccardSimilarity();

    @Override
    public boolean areSimilar(String url1, String url2) {
        double jaccardSimilarity = jaccard.apply(url1, url2);
        if(jaccardSimilarity >= 0.5 && jaccardSimilarity <= 1.0) {
            return true;
        }
        return false;
    }
}
