package com.example.jamarkushodge.blk;

import android.support.annotation.NonNull;

import java.util.ArrayList;


/**
 * Created by jamarkushodge on 2/17/18.
 */

public class Figure implements Comparable<Figure>{




    private int id = 0 ;
    private String name;
    private String imgURL;
    private String followURL;
    private String bio;
    private String facts;
    private ArrayList <Fact> faqs;



    public Figure(){
        name = " ";
        imgURL = " ";
        bio = " ";
        followURL = " ";
        faqs = new ArrayList<>();
        id = ++id;

    }

    public Figure(String name, String followURL, String bio,String imgURL, ArrayList<Fact> faqs){

        this.name = name;
        this.followURL = followURL;
        this.bio = bio;
        this.imgURL = imgURL;

        this.faqs = faqs;

        id = ++id;

    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public void setFactsArr(String factsArr){
        this.facts = factsArr;
    }

    public void setFollowURL(String url){
        followURL = url;
    }

    public void addfact(Fact faq){
        faqs.add(faq);
    }



    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImgURL(){
        return imgURL;
    }

    public String getBio(){
        return bio;
    }

    public ArrayList<Fact> getFactsArr(){
        return faqs;
    }

    public String getFollowURL(){
        return followURL;
    }

    @Override
    public String toString(){
        return new StringBuilder()
                .append("Name --").append(name+"\n")
                .append("Bio --").append(bio+"\n")
                .append("Picture Url --").append(imgURL+"\n")
                .append("Facts -- ").append(facts+"'\n")
                .append("Follow Url -- ").append(followURL + "\n").toString();

    }

    @Override
    public int compareTo(@NonNull Figure figure) {

        if ((name != null ) && (figure.getName() != null)) {
            return name.compareTo(figure.getName());
        }
        else
           return 0;
    }

}
