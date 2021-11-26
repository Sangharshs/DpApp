package com.dpzislamic.girlspic.Model;

public class EnglishQuoteModel {
    String quote,category;

    public EnglishQuoteModel() {

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

    public EnglishQuoteModel(String quote, String category) {
        this.quote = quote;
        this.category = category;
    }
}
