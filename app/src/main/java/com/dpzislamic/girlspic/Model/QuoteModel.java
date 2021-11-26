package com.dpzislamic.girlspic.Model;

public class QuoteModel {
    String quote,category;

    public QuoteModel() {

    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getCategory() {
        return category;
    }

    public String setCategory(String category) {
        this.category = category;
        return category;
    }

    public QuoteModel(String quote, String category) {
        this.quote = quote;
        this.category = category;
    }
}
