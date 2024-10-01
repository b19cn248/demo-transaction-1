package com.opn.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(unique = true)
  private String username;
  private String pass;

  public Account(String username, String pass) {
    this.pass = pass;
    this.username = username;
  }
}
