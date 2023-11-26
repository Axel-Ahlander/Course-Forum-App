package edu.virginia.sde.reviews;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class User {
    @Id
    @Column(name = "NAME")
    private String name; // must be unique

    @Column(name = "PASSWORD")
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
        // do not delete
    }

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // equals?
}
