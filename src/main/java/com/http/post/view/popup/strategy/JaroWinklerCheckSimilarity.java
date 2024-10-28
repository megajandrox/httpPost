package com.http.post.view.popup.strategy;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;

public class JaroWinklerCheckSimilarity implements URLSimilarityStrategy {

    private final JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

    @Override
    public boolean areSimilar(String url1, String url2) {
        double jaroWinklerSimilarity = jaroWinkler.apply(url1, url2);
        if(jaroWinklerSimilarity >= 0.5 && jaroWinklerSimilarity <= 0.84) {
            return true;
        }
        return false;
    }
}
