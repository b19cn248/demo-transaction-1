package com.opn.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String province;
  private String district;
  private String ward;

  public Address(
        String province, String district, String ward
  ) {
    this.province = province;
    this.district = district;
    this.ward = ward;
  }
}
