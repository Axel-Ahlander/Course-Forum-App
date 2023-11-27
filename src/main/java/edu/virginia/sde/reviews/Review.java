package edu.virginia.sde.reviews;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "COMMENT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "course", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    public Review(int id, int rating, String comment, Course course, User user) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.course = course;
        this.user = user;
        setDate();
    }

    public Review(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = date.format(format);
        LocalDate convertedDate = LocalDate.parse(formattedDate, format);
        this.date = convertedDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return id == review.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
