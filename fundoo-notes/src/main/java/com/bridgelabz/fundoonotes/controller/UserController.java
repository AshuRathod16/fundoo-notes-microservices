package com.bridgelabz.fundoonotes.controller;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.service.IUserService;
import com.bridgelabz.fundoonotes.util.Response;
import com.bridgelabz.fundoonotes.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to fundoo notes project";
    }

    @PostMapping("/addUser")
    public ResponseEntity<ResponseUtil> addUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseUtil responseUtil = userService.addUser(userDTO);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String emailId, @RequestParam String password) {
        Response response = userService.login(emailId, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ResponseUtil> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id,
                                                   @RequestHeader String token) {
        ResponseUtil responseUtil = userService.updateUser(userDTO, id, token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<List> getUsers(@RequestHeader String token) {
        List<UserModel> responseUtil = userService.getUsers(token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ResponseUtil> deleteUser(@RequestHeader String token, @RequestParam Long id) {
        ResponseUtil responseUtil = userService.deleteUser(id, token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<ResponseUtil> getUser(@RequestParam Long id, @RequestHeader String token) {
        ResponseUtil responseUtil = userService.getUser(id, token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Response> updatePassword(@RequestHeader String token, @RequestParam String password) {
        Response response = userService.updatePassword(token, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId) {
        Response response = userService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public Boolean validate(@PathVariable String token) {
        return userService.validate(token);
    }

    @PutMapping("/restoreUser")
    public ResponseEntity<Response> restoreUser(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.restoreUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<Response> deleteUsers(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.deleteUsers(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/deletePermanent")
    public ResponseEntity<Response> deletePermanent(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.deletePermanent(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
