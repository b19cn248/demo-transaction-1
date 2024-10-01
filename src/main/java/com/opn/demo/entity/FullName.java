package com.opn.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "fullname")
@NoArgsConstructor
public class FullName {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String firstname;
  private String lastname;

  public FullName(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }
}
