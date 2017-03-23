package com.piemicrosystems.piv;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by pie on 22/03/2017.
 */

public class PageIndicatorView extends LinearLayout {
    Context context;
    int numberOfPages;
    int currentPage;
    String selectedPageColor;
    String unSelectedPagesColor;
    float indicatorSize;

    TextView[] indicators;

    private PageIndicatorView(Context context) {
        super(context);
        this.context = context;
    }

    private PageIndicatorView(Context context, int numberOfPages, float indicatorSize, String selectedPageColor, String unSelectedPagesColor) throws InvalidPagesCountException {
        super(context);
        this.context = context;

        if (numberOfPages <= 0) {
            throw new InvalidPagesCountException();
        }

        this.numberOfPages = numberOfPages;
        this.currentPage = 0;
        this.selectedPageColor = selectedPageColor;
        this.unSelectedPagesColor = unSelectedPagesColor;
        this.indicatorSize = indicatorSize;

        setupDots(currentPage);
    }

    private void setupDots(int selectedPage) {
        removeAllViews();

        indicators = new TextView[numberOfPages];
        TextView indicator = null;

        for (int i = 0; i < indicators.length; i++) {
            indicator = new TextView(context);
            indicator.setText(Html.fromHtml("&#8226;"));
            indicator.setTextColor(Color.parseColor(unSelectedPagesColor));
            indicator.setTextSize(indicatorSize);

            indicators[i] = indicator;
            addView(indicator);
        }

        indicators[selectedPage].setTextColor(Color.parseColor(selectedPageColor));
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int page) throws PageOutOfBoundsException {

        if (page < 0 || page >= numberOfPages) {
            throw new PageOutOfBoundsException(page);
        }

        setupDots(page);
    }

    public static PageIndicatorView newInstance(Context context, int numberOfPages, float indicatorSize, String selectedPageColor, String unSelectedPagesColor) throws InvalidPagesCountException {
        return new PageIndicatorView(context, numberOfPages, indicatorSize, selectedPageColor, unSelectedPagesColor);
    }

    public class PageOutOfBoundsException extends Exception {
        public PageOutOfBoundsException(int pageIndex) {
            super(String.format("Page index %d out of bounds", pageIndex));
        }
    }

    public class InvalidPagesCountException extends Exception {
        public InvalidPagesCountException() {
            super("Number of pages can not be less than or equal to 0");
        }
    }
}
