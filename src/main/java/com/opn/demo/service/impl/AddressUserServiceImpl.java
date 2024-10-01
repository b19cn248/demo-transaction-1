package com.opn.demo.service.impl;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.entity.AddressUser;
import com.opn.demo.exception.user.ForeignBetweenUserAndAddressExceptionIsNotExistException;
import com.opn.demo.repository.AddressUserRepository;
import com.opn.demo.service.AddressUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressUserServiceImpl implements AddressUserService {

  private final AddressUserRepository addressUserRepository;

  @Transactional
  @Override
  public void save(int userId, int addressId) {
    log.info("(save) userId:{},addressId:{}", userId, addressId);
    AddressUser addressUser = new AddressUser(addressId, userId);
    addressUserRepository.save(addressUser);
  }

  @Transactional
  @Override
  public void saveListAddressUser(int userId, List<Integer> listAddressId) {
    log.info("(saveListAddressUser) userId:{},listAddressId:{}",
          userId, listAddressId);
    List<AddressUser> newListAddressUser = new ArrayList<>();
    for (Integer addressId : listAddressId) {
      newListAddressUser.add(new AddressUser(addressId, userId));
    }
    addressUserRepository.saveAll(newListAddressUser);
  }




  @Override
  public List<Integer> getListAddressIdByUserId(int userId) {
    log.info("(getListAddressIdByUserId):userId:{}", userId);
    return addressUserRepository.
          getListAddressIdByUserId(userId);
  }

  @Override
  @Transactional
  public void deleteByUserIdAndListAddressIds(int userId, List<Integer> addressIds) {
    log.info("(deleteByUserIdAndListAddressIds) userId:{},addressIds:{}", userId, addressIds);
    if (addressUserRepository.countUserIdAndListAddressId(userId, addressIds) < addressIds.size()) {
      log.error(MessageExceptionConstant.FOREIGN_BETWEEN_ADDRESS_AND_USER_IS_NOT_EXIST);
        throw new ForeignBetweenUserAndAddressExceptionIsNotExistException();
    }
    addressUserRepository.deleteByUserIdAndListAddressIds(userId, addressIds);
  }
}
