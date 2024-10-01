package com.opn.demo.service;


import java.util.List;

public interface AddressUserService {
  void save(int userId, int addressId);

  void saveListAddressUser(int userId, List<Integer> listAddressId);


  List<Integer>getListAddressIdByUserId(int userId);

  void  deleteByUserIdAndListAddressIds(int userId, List<Integer>addressIds);

}
