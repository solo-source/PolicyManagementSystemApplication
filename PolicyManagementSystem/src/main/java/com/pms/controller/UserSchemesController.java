package com.pms.controller;

import com.pms.entity.Scheme;
import com.pms.entity.UserSchemes;
import com.pms.exception.InvalidEntityException;
import com.pms.service.UserSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-schemes")
public class UserSchemesController {

    @Autowired
    UserSchemeService userSchemeService;

    @PostMapping("/apply")
    public UserSchemes applyToSchemes(@RequestParam int id, @RequestParam int userId) throws InvalidEntityException{
        return userSchemeService.applyToScheme(id, userId);
    }

    @GetMapping("/user/{userId}")
    public List<UserSchemes> allAppledSchemesOfUser(@PathVariable int userId){
        return userSchemeService.allAppliedSchemesOfUser(userId);
    }

    @GetMapping("/active-schemes")
    public List<Scheme> allActiveSchemes(){
        return userSchemeService.allActiveSchemes();
    }
    @GetMapping
    public ResponseEntity<List<UserSchemes>> getUserAndSchemes() {
        return ResponseEntity.ok(userSchemeService.getUsersAndSchemes());
    }

}
