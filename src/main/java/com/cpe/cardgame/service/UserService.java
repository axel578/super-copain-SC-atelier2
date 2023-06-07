package com.cpe.cardgame.service;

import com.cpe.cardgame.model.UserGame;
import com.cpe.cardgame.repositories.UserRepository;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Example method for updating a user
    public ResponseMessage<UserGame> updateUser(UserGame userGame) {
        var result =  userRepository.save(userGame);
        ResponseMessage<UserGame> responseMessage = new ResponseMessage<>(result);
        if(result == null)
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("User not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<UserGame> getUser(Integer id) {
        var result =  userRepository.findById(id);
        ResponseMessage<UserGame> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("User not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<UserGame> connect(String login, String pwd){
        var result = userRepository.findUserConnexion(pwd, login);
        ResponseMessage<UserGame> responseMessage = new ResponseMessage<UserGame>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("User not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    // Other methods for CRUD operations or custom queries can be defined here
}