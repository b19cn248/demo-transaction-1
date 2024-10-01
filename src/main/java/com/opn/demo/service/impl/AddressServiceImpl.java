package com.opn.demo.service.impl;

import com.opn.demo.dto.response.AddressDTO;
import com.opn.demo.entity.Address;
import com.opn.demo.repository.AddressRepository;
import com.opn.demo.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  @Transactional
  @Override
  public Address save(AddressDTO addressDTO) {
    log.info("(save) addressDTO:{}", addressDTO);
    Address address = new Address(
          addressDTO.getProvince(),
          addressDTO.getDistrict(),
          addressDTO.getWard()
    );
    return addressRepository.save(address);
  }

  @Transactional
  @Override
  public List<Address> saveAll(List<AddressDTO> addressDTOList) {
    log.info("(saveAll) addressDTOList:{}", addressDTOList);
    List<Address> newAddress = new ArrayList<>();
    for (AddressDTO addressDTO : addressDTOList) {
      newAddress.add(new Address(
            addressDTO.getProvince(),
            addressDTO.getDistrict(),
            addressDTO.getWard()
      ));
    }
    return addressRepository.saveAll(newAddress);
  }
}
