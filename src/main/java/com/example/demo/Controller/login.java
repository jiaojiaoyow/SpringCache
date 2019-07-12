package com.example.demo.Controller;

import com.example.demo.Dao.UserMapper;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class login {
    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @RequestMapping("login")
    public ModelAndView login(User user){
        User user1=userService.selectByPrimaryKey(user.getUsername());
        if(user.getPassword().equals(user1.getPassword())){
            return new ModelAndView("success");
        }
        else {
            return new ModelAndView("fail");
        }
    }

    @RequestMapping("edit")
    public ModelAndView edit(User user){
        User myuser=userService.updateByPrimaryKeySelective(user);
        if(myuser.getUsername()!=null){
            return new ModelAndView("success");
        }
        else {
            return new ModelAndView("fail");
        }
    }

    @RequestMapping("logout")
    @CacheEvict(value = "user",key = "#user.username", beforeInvocation = false)
    public ModelAndView logout(User user){
        User myUser=userService.selectByPrimaryKey(user.getUsername());
        if(user.getPassword().equals(myUser.getPassword())){
            userService.deleteByPrimaryKey(user.getUsername());
            return new ModelAndView("success");
        }
        else {
            return new ModelAndView("fail");
        }
    }
}
