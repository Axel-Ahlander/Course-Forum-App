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

    @Column(name = "Rating")
    private Float rating; //use wrapper to allow null

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Review> reviews = new HashSet<>();

    public Course() {
        // required by Hibernate, do not delete
    }

    public Course(int id, String subject, int number, String title, Float rating) {
        this.subject = subject;
        this.number = number;
        this.title = title;
        this.id = id;
        this.rating = rating;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", number=" + number +
                ", title='" + title + '\'' +
                ", rating=" + rating +
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
