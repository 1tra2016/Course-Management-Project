package model.entity;

import model.enrollmentStatus;

import java.sql.Timestamp;


public class Enrollment {
    private int id;
    private int idStudent;
    private int idCourse;
    private Timestamp registeredAt;
    private enrollmentStatus status = enrollmentStatus.WAITING;

    public Enrollment(){}

    public Enrollment(int idStudent, int idCourse){
        this.idStudent = idStudent;
        this.idCourse = idCourse;
    }

    public Enrollment(int id, int idStudent, int idCourse, Timestamp registeredAt, enrollmentStatus status) {
        this.id = id;
        this.idStudent = idStudent;
        this.idCourse = idCourse;
        this.registeredAt = registeredAt;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdStudent() {
        return idStudent;
    }
    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }
    public int getIdCourse() {
        return idCourse;
    }
    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }
    public Timestamp getRegisteredAt() {
        return registeredAt;
    }
    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }
    public enrollmentStatus getStatus() {
        return status;
    }
    public void setStatus(enrollmentStatus status) {
        this.status = status;
    }
}
