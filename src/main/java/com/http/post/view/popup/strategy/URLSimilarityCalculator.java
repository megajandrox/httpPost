package com.http.post.view.popup.strategy;

public class URLSimilarityCalculator {

    private URLSimilarityStrategy strategy;

    private static URLSimilarityCalculator instance;

    public static URLSimilarityCalculator getInstance() {
        if (instance == null) {
            instance = new URLSimilarityCalculator(SimilarityType.Levenshtein);
        }
        return instance;
    }

    public static URLSimilarityCalculator switchStrategy(SimilarityType strategyType) {
        instance = new URLSimilarityCalculator(strategyType);
        return instance;
    }

    private URLSimilarityCalculator(SimilarityType strategyType) {
        switch (strategyType) {
            case Levenshtein:
                strategy = new LevenshteinSimilarity();
                break;
            case JaroWinkler:
                strategy = new JaroWinklerCheckSimilarity();
                break;
            case Jaccard:
                strategy = new JaccardCheckSimilarity();
                break;
        }
    }

    public boolean checkSimilarity(String url1, String url2) {
        return strategy.areSimilar(url1, url2);
    }
}
