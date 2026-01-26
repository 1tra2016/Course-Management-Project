package model;

import java.time.LocalDate;


public class EnrollmentDetail {
    private int enrollmentId;

    private int courseId;
    private String courseName;
    private int duration;
    private String instructor;

    private LocalDate registeredAt;
    private enrollmentStatus status;

    public EnrollmentDetail( int enrollmentId, int courseId, String courseName, int duration, String instructor, LocalDate registeredAt, enrollmentStatus status){
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.instructor = instructor;
        this.registeredAt = registeredAt;
        this.status = status;
    }

    //get
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getDuration() {
        return duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    public enrollmentStatus getStatus() {
        return status;
    }

    //set
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setStatus(enrollmentStatus status) {
        this.status = status;
    }
}

