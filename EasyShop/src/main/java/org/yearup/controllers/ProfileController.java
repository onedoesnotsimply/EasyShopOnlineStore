package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_USER')")
public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao=userDao;
    }

    @GetMapping("")
    public Profile getById(Principal principal){
        int userId = getUserId(principal);

        return profileDao.getProfileById(userId);
    }

    @PutMapping("")
    public void updateProfile(@RequestBody Profile profile, Principal principal){
        int userId = getUserId(principal);

        profileDao.updateProfile(userId,profile);
    }

    private int getUserId(Principal principal){
        // get the currently logged-in username
        String userName = principal.getName();
        // find database user by userId
        User user = userDao.getByUserName(userName);
        return user.getId();
    }
}
