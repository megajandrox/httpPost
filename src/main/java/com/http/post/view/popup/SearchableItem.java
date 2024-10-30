package com.http.post.view.popup;

import com.http.post.view.popup.strategy.SimilarityType;
import com.http.post.view.popup.strategy.URLSimilarityCalculator;

public interface SearchableItem {

    String getSearchField();

    default boolean similarTo(SearchableItem other) {
        return URLSimilarityCalculator.getInstance().checkSimilarity(getSearchField(), other.getSearchField());
    }
}