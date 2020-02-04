package com.example.telegabot.Currency;

public class Currency {
    private String title;
    private String pubDate;
    private String value;

    public Currency() {
    }

    public Currency(String title, String pubDate, String value) {
        this.title = title;
        this.pubDate = pubDate;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "title='" + title + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", value=" + value +
                '}';
    }
}
