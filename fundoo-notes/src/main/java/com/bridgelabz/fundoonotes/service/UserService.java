package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.UserModel;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.util.Response;
import com.bridgelabz.fundoonotes.util.ResponseUtil;
import com.bridgelabz.fundoonotes.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 09-09-2022
 * Purpose: Creating method to send Email
 */

@Service
public class UserService implements IUserService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;


    @Override
    public ResponseUtil addUser(UserDTO userDTO) {
        UserModel userModel = new UserModel(userDTO);
        userModel.setCreatedAt(LocalDateTime.now());
        userRepository.save(userModel);
        String body = "Fundoo user is added successfully with userId " + userModel.getId();
        String subject = "Fundoo user is added successfully";
        mailService.send(userModel.getEmailId(), body, subject);
        return new ResponseUtil(200, "successfully", userModel);
    }

    @Override
    public Response login(String emailId, String password) {
        Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            if (isEmailPresent.get().getPassword().equals(password)) {
                String token = tokenUtil.createToken(isEmailPresent.get().getId());
                return new Response(200, "Login successfully", token);
            }
            throw new UserException(400, "Password is wrong");
        }
        throw new UserException(400, "No user is present with this email id");
    }

    @Override
    public ResponseUtil updateUser(UserDTO userDTO, Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setName(userDTO.getName());
                isUserPresent.get().setEmailId(userDTO.getEmailId());
                isUserPresent.get().setPassword(userDTO.getPassword());
                isUserPresent.get().setDOB(userDTO.getDOB());
                isUserPresent.get().setMobileNumber(userDTO.getMobileNumber());
                isUserPresent.get().setProfilePic(userDTO.getProfilePic());
                userRepository.save(isUserPresent.get());
                String body = "Fundoo user is added successfully with userId" + isUserPresent.get().getId();
                String subject = "Fundoo user is added successfully";
                mailService.send(isUserPresent.get().getEmailId(), body, subject);
                return new ResponseUtil(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not present");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    @Override
    public ResponseUtil getUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                return new ResponseUtil(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not present");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    @Override
    public List<UserModel> getUsers(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            List<UserModel> isUserPresent = userRepository.findAll();
            if (isUserPresent.size() > 0) {
                return isUserPresent;
            } else {
                throw new UserException(400, "User not present");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    @Override
    public ResponseUtil deleteUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                userRepository.delete(isUserPresent.get());
                return new ResponseUtil(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not present");
            }
        }
        throw new UserException(400, "Token is wrong");
    }


    @Override
    public Response updatePassword(String token, String password) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            isId.get().setPassword(password);
            userRepository.save(isId.get());
            return new Response(200, "Successfully", isId.get());
        } else {
            throw new UserException(400, "Token is wrong");
        }
    }

    @Override
    public Response resetPassword(String emailId) {
        Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = System.getenv("url");
            String body = "Reset the password using this link \n " + url +
                    "\n This token is use to reset the password \n" + token;
            String subject = "Reset password successfully";
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
            return new Response(200, "Successfull", isEmailPresent.get());
        } else {
            throw new UserException(400, "Wrong email id");
        }

    }

    @Override
    public Boolean validate(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Response restoreUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setIsActive(true);
                isUserPresent.get().setIsDeleted(false);
                userRepository.save(isUserPresent.get());
                return new Response(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not found with this id");
            }
        } else {
            throw new UserException(400, "Token is wrong");
        }
    }

    @Override
    public Response deleteUsers(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setIsActive(false);
                isUserPresent.get().setIsDeleted(true);
                userRepository.save(isUserPresent.get());
                return new Response(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not found with this id");
            }
        } else {
            throw new UserException(400, "Token is wrong");
        }
    }

    @Override
    public Response deletePermanent(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                userRepository.delete(isUserPresent.get());
                return new Response(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not found with this id");
            }
        } else {
            throw new UserException(400, "Token is wrong");
        }

    }
}
