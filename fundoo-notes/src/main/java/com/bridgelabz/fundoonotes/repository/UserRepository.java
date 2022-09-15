package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 09-09-2022
 * Purpose : Repository connected to the database
 */

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {


    Optional<UserModel> findByEmailId(String emailId);
}
