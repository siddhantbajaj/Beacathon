package com.example.siddhant.project;

/**
 * Created by siddhant on 29/12/16.
 */

public class RestrauntResponse {
    String menu_url;
    String cuisines;
    String url;
    int average_cost_for_two;
    String featured_image;

    public String getMenu_url() {
        return menu_url;
    }

    public String getCuisines() {
        return cuisines;
    }

    public String getUrl() {
        return url;
    }

    public int getAverage_cost_for_two() {
        return average_cost_for_two;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAverage_cost_for_two(int average_cost_for_two) {
        this.average_cost_for_two = average_cost_for_two;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }
}
