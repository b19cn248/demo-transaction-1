package com.opn.demo.service;

import com.opn.demo.dto.response.AddressDTO;
import com.opn.demo.entity.Address;

import java.util.List;

public interface AddressService {
  Address save(AddressDTO addressDTO);

  List<Address> saveAll(List<AddressDTO> addressDTOList);
}
