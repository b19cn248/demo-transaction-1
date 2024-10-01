package com.opn.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address_user")
@Data
@NoArgsConstructor
public class AddressUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private int addressId;

  private int userId;

  public AddressUser(int addressId, int userId) {
    this.addressId = addressId;
    this.userId = userId;
  }
}
