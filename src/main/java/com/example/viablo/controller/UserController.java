package com.example.viablo.controller;

import com.example.viablo.entity.ObjResponse;
import com.example.viablo.entity.Role;
import com.example.viablo.entity.User;
import com.example.viablo.entity.request.userLogin;
import com.example.viablo.entity.response.UserResponse;
import com.example.viablo.jwt.JwtTokenUtils;
import com.example.viablo.service.RoleService;
import com.example.viablo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
@CrossOrigin("http://localhost:3000/")
@RestController
public class UserController {
    public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/AvatarImage/";
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleService roleService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody userLogin userLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            System.out.println("alo alo "+user.getAvatar() );
            String token = jwtTokenUtils.generateToken(user);
            UserResponse userResponse = new UserResponse(user.getId(),user.getUsername(),user.getAvatar(), user.getEmail(), token, user.getRoles());
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestParam("email") String email,
                                      @RequestParam("username") String username,
                                      @RequestParam("password") String password,
                                      @RequestParam("avatar") MultipartFile file) throws IOException {
        Optional<User> userOptional = userService.findUserByUsername(username);
        Optional<User> userOptional1 = userService.findUserByEmail(email);
        if (userOptional.isPresent() || userOptional1.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjResponse("fail", "that bai", ""));
        } else {
            String UuidImage;
            if (!file.isEmpty()) {
                Random rand = new Random();
                int ranNum = rand.nextInt(1000) + 1;
                UuidImage = String.valueOf(ranNum) + file.getOriginalFilename();
                File path = new File(ResourceUtils.getURL("classpath:static/AvatarImage").getPath()).getAbsoluteFile();
                String uploadDirr = path.getAbsolutePath();
                Path filepath = Paths.get(uploadDirr, UuidImage);
                Files.write(filepath, file.getBytes());

                Path filepath1= Paths.get(uploadDir,UuidImage);
                Files.write(filepath1,file.getBytes());
                Set<Role> roles = new HashSet<>();
                Role role_user = roleService.findRoleByName("ROLE_CUSTOMER");
                roles.add(role_user);

                User user = new User();
                user.setUsername(username);
                user.setAvatar(UuidImage);
                user.setPassword(passwordEncoder.encode(password));
                user.setEmail(email);
                user.setRoles(roles);
                userService.registerUser(user);
                return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","thanh cong",""));
            }else{
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(new ObjResponse("fail","Thêm thất bại",""));
            }


//
//            String password = passwordEncoder.encode(user.getPassword());
//            Set<Role> roles = new HashSet<>();
//            Role role_user = roleService.findRoleByName("ROLE_CUSTOMER");
//            roles.add(role_user);
//            User user1 = new User(user.getUsername(),password,user.getEmail(),roles);
//            userService.registerUser(user1);
//            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","thanh cong",""));

        }
    }

    @PostMapping("/api/follow/{uId}/{fId}")
    public ResponseEntity<?> followUser(@PathVariable int uId,@PathVariable int fId){
        Optional<User> userID = userService.getUserById(uId);
        Optional<User> followedUser = userService.getUserById(fId);
        if(userID.isPresent() && followedUser.isPresent()){
            User user = userID.get();
            user.addFollow(followedUser.get());
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","thanh cong",""));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","that bai",""));

        }
    }

    @GetMapping("/api/u/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            User user1 = user.get();
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","thanh cong",user1));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ObjResponse("ok","that bai",""));

        }
    }

//    @GetMapping("/api/checkfollow/uId/fId")
//    public ResponseEntity<?> checkFollow(@PathVariable int uId,@PathVariable int fId){
//
//    }


}
