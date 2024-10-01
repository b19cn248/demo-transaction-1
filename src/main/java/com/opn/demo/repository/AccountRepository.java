package com.opn.demo.repository;


import com.opn.demo.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface AccountRepository extends JpaRepository<Account, Integer> {
  @Modifying
  @Transactional
  @Query("""
        UPDATE Account a SET
         a.username = :username, a.pass = :pass 
         WHERE a.id = :id
        """)
  void update(int id, String username, String pass);


  Account findAccountByUsername(String username);
}
