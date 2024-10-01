package com.opn.demo.repository;

import com.opn.demo.entity.AddressUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AddressUserRepository extends JpaRepository<AddressUser,Integer> {
  @Transactional
  void deleteAddressUserByUserId(int userId);

  @Modifying
  @Transactional
  @Query("""
        DELETE FROM AddressUser au 
        WHERE au.userId = :userId 
        and au.addressId=:addressId
        """)
  void deleteAddressUserByAddressIdAndUserId(int userId, int addressId);

  @Query("""
        select au.addressId FROM AddressUser au 
        WHERE au.userId = :userId
        """)
  List<Integer> getListAddressIdByUserId(int userId);

  @Query("""
            SELECT COUNT(au) > 0 FROM AddressUser au 
            WHERE au.userId = :userId 
            and au.addressId = :addressId
            """)
  boolean existsByUserIdAndAddressId(int userId, int addressId);

  @Modifying
  @Transactional
  @Query("""
        DELETE FROM AddressUser au 
        WHERE au.userId = :userId AND au.addressId IN :addressIds
        """)
  void deleteByUserIdAndListAddressIds(int userId, List<Integer> addressIds);

  @Query("""
            SELECT COUNT(au)  FROM AddressUser au 
            WHERE au.userId = :userId 
            and au.addressId in :addressIds
            """)
  int countUserIdAndListAddressId(int userId, List<Integer> addressIds);
}
