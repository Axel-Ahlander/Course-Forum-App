package edu.virginia.sde.reviews;

import jakarta.persistence.*;

@Entity
@Table(name = "COURSES", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
// unique needed?
public class Course {
    // TODO: make sure id is unique, see hibernate lecture, unique = true in line does not enforce, still double check
    // TODO: equals method
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "ID")
    private long id;

    @Column(name = "COURSE_NAME", nullable = false, length = 128)
    // unique = true would've went in above line, check on if nullable is effective or not as well
    private String courseName;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "NUMBER", nullable = false)
    private int number;

    @Column(name = "Title", nullable = false)
    private String title;

    // TODO: have rating field in course class or Review class?
    // TODO: Have instructor or not?
    @Column(name = "INSTRUCTOR", nullable = false)
    private String instructor;

    public Course(String courseName, String instructor) {
        this.courseName = courseName;
        this.instructor = instructor;
    }

    public Course() {
        // required by Hibernate, do not delete
    }

    public Course(String courseName, String subject, int number, String title, String instructor) {
        this.courseName = courseName;
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.instructor = instructor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    // equals should only be equal if same subject, number, and title

}
