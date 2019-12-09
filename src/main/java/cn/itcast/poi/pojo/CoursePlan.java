package cn.itcast.poi.pojo;

import java.io.Serializable;
import java.util.Date;

public class CoursePlan implements Serializable {

    private Integer id;
    private String course;
    private Long studentNumber;
    private String name;
    private String studentClass;
    private Date testTime;
    private String testPlace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getTestPlace() {
        return testPlace;
    }

    public void setTestPlace(String testPlace) {
        this.testPlace = testPlace;
    }

    @Override
    public String toString() {
        return "CoursePlan{" +
                "id=" + id +
                ", course='" + course + '\'' +
                ", studentNumber=" + studentNumber +
                ", name='" + name + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", testTime=" + testTime +
                ", testPlace='" + testPlace + '\'' +
                '}';
    }
}
