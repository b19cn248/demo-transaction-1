package com.opn.demo.service.impl;

import com.opn.demo.entity.FullName;
import com.opn.demo.repository.FullNameRepository;
import com.opn.demo.service.FullNameService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class  FullNameServiceImpl implements FullNameService {

  private final FullNameRepository fullNameRepository;


  @Transactional
  @Override
  public FullName save(String firstname, String lastname) {
    log.info("(save) firstname:{},lastname:{}", firstname, lastname);
    FullName fullName = new FullName(firstname, lastname);
    return fullNameRepository.save(fullName);
  }

  @Override
  @Transactional
  public void updateByIndex(
        int id, String firstname, String lastname) {
    log.info("(updateByUserId) userId:{},firstname:{},lastname:{}"
          , id, firstname, lastname);
    fullNameRepository.updateFullNameByUserId(
          id, firstname, lastname
    );
  }

}
