package com.opn.demo.service;

import com.opn.demo.entity.FullName;

public interface FullNameService {
  FullName save(String firstname, String lastname);

  void updateByIndex(
        int id ,String firstname, String lastname
  );
}
