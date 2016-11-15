package com.example.user.task_2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by User on 11/14/2016.
 */

public class Contact implements Serializable {
    private String id;
    private String name;
    private ArrayList<String> numbers;
    private ArrayList<String> emails;
    private String iconString;

    public Contact(String id, String name, String iconString, ArrayList<String> numbers, ArrayList<String> emails) {
        this.id = id;
        this.iconString = iconString;
        this.emails = emails;
        this.numbers = numbers;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public String getIconString() {
        return iconString;
    }
}
