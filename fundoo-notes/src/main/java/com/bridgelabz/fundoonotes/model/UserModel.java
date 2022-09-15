package com.bridgelabz.fundoonotes.model;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 09-09-2022
 * Purpose : Model for the User data Registration
 */

@Data
@Entity
@Table(name = "UserDetails")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "mobileNumber")
    private String mobileNumber;
    @Column(name = "emailId")
    private String emailId;
    @Column(name = "password")
    private String password;
    @Column(name = "DOB")
    private String DOB;
    @Column(name = "isActive")
    private Boolean isActive;
    @Column(name = "isDeleted")
    private Boolean isDeleted;
    @Column(name = "profilePic")
    private String profilePic;
    @Column(name = "registerAt")
    private LocalDateTime createdAt;
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public UserModel(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.mobileNumber = userDTO.getMobileNumber();
        this.emailId = userDTO.getEmailId();
        this.password = userDTO.getPassword();
        this.DOB = userDTO.getDOB();
        this.profilePic = userDTO.getProfilePic();

    }

    public UserModel() {
    }
}
