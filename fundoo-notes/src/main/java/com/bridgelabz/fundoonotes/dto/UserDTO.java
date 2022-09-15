package com.bridgelabz.fundoonotes.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 09-09-2022
 * Purpose : dto for the User data
 */

@Data
public class UserDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First letter of first name must be capital")
    private String name;
    @Pattern(regexp = "[0-9]{10}", message = "Invalid Mobile Number")
    private String mobileNumber;
    @Email(message = "Insert valid email Id")
    @Pattern(regexp = "[a-z][A-Z a-z 0-9]+[@][a-z]+[.][a-z]{2,}", message = "Invalid email id")
    private String emailId;
    @NotNull(message = "Enter Your Password")
    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}", message = "Invalid password")
    private String password;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "date of birth must be in YYYY-MM-DD format")
    private String DOB;
    @NotNull
    private String profilePic;


}