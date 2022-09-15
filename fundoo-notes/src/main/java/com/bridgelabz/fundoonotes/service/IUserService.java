package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.util.Response;
import com.bridgelabz.fundoonotes.util.ResponseUtil;

import java.util.List;

public interface IUserService {

    public ResponseUtil addUser(UserDTO userDTO);

    public Response login(String emailId, String password);

    public ResponseUtil updateUser(UserDTO userDTO, Long id, String token);

    public List<UserModel> getUsers(String token);

    public ResponseUtil deleteUser(Long id, String token);

    public ResponseUtil getUser(Long id, String token);

    public Response updatePassword(String token, String password);

    public Response resetPassword(String emailId);

    public Boolean validate(String token);

    public Response restoreUser(Long id, String token);

    public Response deleteUsers(Long id, String token);

    public Response deletePermanent(Long id, String token);

}
