package com.cpe.cardgame.controller;

import com.cpe.cardgame.model.UserGame;
import com.cpe.cardgame.service.UserService;
import com.cpe.cardgame.utils.ResponseMessage;
import com.cpe.cardgame.viewmodel.AuthDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;

@Controller
public class UserController extends BaseController {
    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseMessage<UserGame> createUser(UserGame userGame) {
        return this.userService.updateUser(userGame);
    }

    @GetMapping("/user/{id}")
    public ResponseMessage<UserGame> getUserById(@PathVariable("id") int id) {
         return this.userService.getUser(id);
    }
    @RequestMapping(value = { "/connexion"}, method = RequestMethod.GET)
	public String connexion(Model model) {
		AuthDTO userform = new AuthDTO();
		model.addAttribute("connectForm", userform);
		return "connectForm";
	}
    @RequestMapping(value="/connexion-user", method = RequestMethod.POST)
    public String connecUser(@ModelAttribute("connectForm") AuthDTO userform, HttpServletRequest httprequest){
        var user = this.userService.connect(userform.getUsername(), userform.getPassword());
        httprequest.getSession().setAttribute("USER", user.getResponse().getId());
        return "index";
    }

    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public ResponseEntity<String> connectUser(@ModelAttribute("connectForm") AuthDTO userform, HttpServletRequest httprequest) {
        var user = this.userService.connect(userform.getUsername(), userform.getPassword());

        if (user.isSuccess()) {
            // Generate JWT token
            if(DataKey==null)
            {
                BaseController.DataKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            }
            String jwt = Jwts.builder()
                    .setSubject(user.getResponse().getId().toString()) // Assuming user ID is a String
                    .signWith(BaseController.DataKey)
                    .compact();

            // Return JWT token in the response body
            return ResponseEntity.ok().body(jwt);
        } else {
            // Handle invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @RequestMapping(value="/my-profile", method = RequestMethod.GET)
    public String UserProfile(Model model, HttpServletRequest httprequest){
        var user = GetByUser(httprequest);
        if(user == 0)
        {
            AuthDTO authForm = new AuthDTO();
            model.addAttribute("connectForm", authForm);
            return "connectForm";
        }

        return "myProfile";
    }


    @RequestMapping(value = { "/create-user"}, method = RequestMethod.GET)
	public String createUser(Model model) {
		UserGame userform = new UserGame();
		model.addAttribute("createForm", userform);
		return "createForm";
	}

    @RequestMapping(value="/create-user", method = RequestMethod.POST)
    public String createUserAction(Model model,@ModelAttribute("createForm") UserGame userform){
        var user = this.userService.updateUser(userform);
        AuthDTO authForm = new AuthDTO();
		model.addAttribute("connectForm", authForm);
        return "connectForm";
    }

}