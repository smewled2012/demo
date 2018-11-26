package com.example.demo.security;

import com.example.demo.Flight;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User_Data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // @Column(name = "first_name", nullable = false)
    // @Size(min = 3)
    private String firstName;

    //@Column(name = "last_name", nullable = false)
    // @Size(min = 3)
    private String lastName;

    // @Column(name = "Date_of_Birth", nullable = false)
    // @Size(min = 3)
    private String dob;

    // @Column(name = "Citizenship", nullable = false)
    //  @Size(min = 3)
    private String citizenship;

    //@Column(name = "phone number", nullable = false)
    // @Size(min = 7)
    private String phonenum;

    // @Column(name = "email", nullable = false)
    // @Size(min = 3)
    private String email;

    // @Column(name = "password", nullable = false)
    //  @Size(min = 3)
    private String password;

    // @Column(name = "enabled")
    private boolean enabled;

    // @Column(name = "username", unique = true)
    //  @Size(min = 3)
    private String username;

    @ManyToMany
    private List<Flight> flights;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {

    }

    public User(String firstName, String lastName, String dob, String citizenship,
                String phonenum, String email, String password, boolean enabled, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.citizenship = citizenship;
        this.phonenum = phonenum;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.username = username;
        roles = new ArrayList<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getDob() {
        return dob;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}
