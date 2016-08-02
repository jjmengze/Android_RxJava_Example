package com.ameng.rxexample;

import java.util.ArrayList;

/**
 * Created by ameng on 8/2/16.
 */
public class Student {
    private String name;
    private ArrayList<Course> courses;

    public Student(String name) {
        this.name = name;
        courses = new ArrayList<>();
    }

    public void setCourses(Course... name) {
        for (Course course : name) {
            courses.add(course);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
