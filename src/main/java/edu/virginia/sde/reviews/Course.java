package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COURSES", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
// unique needed?
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "ID")
    private int id;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "NUMBER", nullable = false)
    private int number;

    @Column(name = "Title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Review> reviews = new HashSet<>();

    public Course() {
        // required by Hibernate, do not delete
    }

    public Course(int id, String subject, int number, String title) {
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.id = id;
    }

    public long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", number=" + number +
                ", title='" + title + '\'' +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return subject.equals(course.subject) &&
                number == course.number &&
                title.equals(course.title);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
