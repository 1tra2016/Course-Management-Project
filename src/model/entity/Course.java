package model.entity;

import java.sql.Timestamp;

public class Course {
    private int id;
    private String name;
    private int duration;
    private String instructor;
    private Timestamp createdAt;

    public Course() {
    }

    public Course(int id, String name, int duration, String instructor, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.instructor = instructor;
        this.createdAt = createdAt;
    }
    // Getter & Setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

