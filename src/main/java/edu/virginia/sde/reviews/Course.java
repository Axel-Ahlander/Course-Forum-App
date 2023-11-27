package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COURSES", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
// unique needed?
public class Course {
    // TODO: make sure id is unique, see hibernate lecture, unique = true in line does not enforce, still double check
    // TODO: equals method
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "ID")
    private int id;

    @Column(name = "COURSE_NAME", nullable = false, length = 128)
    // unique = true would've went in above line, check on if nullable is effective or not as well
    private String courseName;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "NUMBER", nullable = false)
    private int number;

    @Column(name = "Title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "course")
    private Set<Review> reviews = new HashSet<>();

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Course() {
        // required by Hibernate, do not delete
    }

    public Course(String courseName, String subject, int number, String title, String instructor) {
        this.courseName = courseName;
        this.subject = subject;
        this.number = number;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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



    // equals should only be equal if same subject, number, and title

}
