package com.opn.demo.repository;

import com.opn.demo.entity.FullName;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface FullNameRepository extends JpaRepository<FullName,Integer> {

  @Modifying
  @Transactional
  @Query("""
        UPDATE FullName f set
        f.firstname=:firstname , f.lastname=:lastname
        WHERE f.id =:id
        """)
 void updateFullNameByUserId(
        int id, String firstname, String lastname
  );
}
