package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name; // must be unique

    @Column(name = "PASSWORD")
    private String password;

//    @OneToMany(mappedBy = "")
//    private List<Course> courses;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews = new HashSet<>();

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
//        courses = new ArrayList<>();
        // do not delete
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public List<Course> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }
//
//    public boolean addCourse(Course course) {
//        if (contains(course)) {
//            return false;
//        }
//        courses.add(course);
//        return true;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public boolean contains(Course course) {
//        return courses.contains(course);
//    }
//    public boolean remove(Course course) {
//        return courses.remove(course);
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
