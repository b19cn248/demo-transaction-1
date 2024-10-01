package com.opn.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(unique = true)
  private String accountNumber;
  private String email;
  private String phoneNumber;
  private double balance;
  private int fullnameId;
  private int accountId;
  @Column(nullable = false)
  private boolean isRemove;
  @Column(nullable = false)
  private boolean isActive;

  public User(
        String accountNumber,
        String email,
        String phoneNumber,
        double balance,
        int fullnameId,
        int accountId
  ) {
    this.accountNumber = accountNumber;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.balance = balance;
    this.fullnameId = fullnameId;
    this.accountId = accountId;
  }

  public User(
        int id,
        String accountNumber,
        String email,
        String phoneNumber,
        double balance,
        int fullnameId,
        int accountId) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.balance = balance;
    this.fullnameId = fullnameId;
    this.accountId = accountId;
  }

  public int getId() {
    return id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public double getBalance() {
    return balance;
  }

  public int getFullnameId() {
    return fullnameId;
  }

  public int getAccountId() {
    return accountId;
  }
}
