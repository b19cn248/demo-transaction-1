package com.opn.demo.repository;

import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("""
        SELECT distinct new com.opn.demo.dto.response.UserDetailDTO
             (u.id, u.email, u.phoneNumber, u.accountNumber, u.balance, 
             f.firstname, f.lastname, a.username, 
             a.pass, ad.province, ad.district, ad.ward)
         FROM User u 
         JOIN FullName f ON u.fullnameId = f.id 
         JOIN Account a ON u.accountId = a.id 
         LEFT JOIN AddressUser au ON u.id = au.userId 
         LEFT JOIN Address ad ON au.addressId = ad.id
         Where u.isRemove=false 
         """)
  Slice<UserDetailDTO> getUserDetails(Pageable pageable);

  @Query("""
        SELECT distinct new com.opn.demo.dto.response.UserDetailDTO
             (u.id, u.email, u.phoneNumber, u.accountNumber, u.balance, 
             f.firstname, f.lastname, a.username, 
             a.pass, ad.province, ad.district, ad.ward)
         FROM User u 
         JOIN FullName f ON u.fullnameId = f.id 
         JOIN Account a ON u.accountId = a.id 
         LEFT JOIN AddressUser au ON u.id = au.userId 
         LEFT JOIN Address ad ON au.addressId = ad.id
        Where u.id=:userId and u.isRemove=false 
        """)
  List<UserDetailDTO> getUserByIndexDetail(int userId);

  @Modifying
  @Transactional
  @Query(
        """
        UPDATE User set balance=balance-:amount 
        WHERE accountNumber = :accountNumber
        """)
  void withdraw(String accountNumber, double amount);

  @Modifying
  @Transactional
  @Query(
        """
        UPDATE User set balance=balance+:amount 
        WHERE accountNumber = :accountNumber
        """)
  void deposit(String accountNumber, double amount);

  @Query("""
        SELECT new com.opn.demo.entity.User(
        u.id,u.email,
        u.phoneNumber,u.accountNumber ,u.balance, u.accountId,u.fullnameId)
        FROM User u 
        Where u.accountNumber=:accountNumberTransaction
        """
  )
  User findUserByAccountNumber(String accountNumberTransaction);

  @Modifying
  @Transactional
  @Query("""
        update User u set 
        u.email = :email , u.phoneNumber= :phoneNumber
        where u.id=:userId
        """)
  void updateUserByUserId(
        int userId, String email, String phoneNumber
  );

  @Query("""
        select  new com.opn.demo.entity.User(
        u.id, u.email, u.phoneNumber,
        u.accountNumber, u.balance,
        u.fullnameId,u.accountId )
        FROM User u 
        where u.isRemove=false and u.id=:id 
        """)
  User findUserById(int id);

  User findByEmail(String email);


  @Modifying
  @Transactional
  @Query("""
        update User u set 
        u.isRemove=true where u.id=:id
        """)
  void setIsRemoveIsTrueById(int id);


  @Modifying
  @Transactional
  @Query("""
        update User u set 
        u.isActive=true where u.email=:email
        """)
  void activeUserByEmail(String email);
}
