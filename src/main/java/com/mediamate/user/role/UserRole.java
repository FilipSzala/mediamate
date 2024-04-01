package com.mediamate.user.role;

import com.mediamate.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "ROLE")

public abstract class UserRole  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JoinColumn (
          name = "id",
          updatable = false
  )
  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate createAt = LocalDate.now();

  @OneToOne(
          mappedBy = "userRole",
          cascade = {CascadeType.PERSIST,CascadeType.MERGE})
  private User user;


  public UserRole(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public UserRole() {
  }


  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDate getCreateAt() {
    return createAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
