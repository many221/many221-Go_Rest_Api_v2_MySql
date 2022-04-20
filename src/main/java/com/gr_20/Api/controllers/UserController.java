package com.gr_20.Api.controllers;
import com.gr_20.Api.models.UserModel;
import com.gr_20.Api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final String URL = "https://gorest.co.in/public/v2/users";

    @Autowired
    private UserRepository userRepo;


    //X> Create
    //CreateOneUser
    //How to add multiple Users?
    @PostMapping
    public ResponseEntity<UserModel> createOneUser (@RequestBody UserModel newUser){
        
       return new ResponseEntity<> (userRepo.save ( newUser),HttpStatus.CREATED);
       
    }

    //X> Read
    //GetUserById
    @GetMapping("/{Id}")
    public UserModel getUserByID (@PathVariable int Id){
        
        return userRepo.findById (Id).orElseThrow(() -> new ResponseStatusException( HttpStatus.NOT_FOUND));
        
    }
    
    //GetAllUsers
    @GetMapping
    public ResponseEntity<Iterable<UserModel>> getAllUsers(){
        
        System.out.println (userRepo.count ());
        
        return new ResponseEntity<> ( userRepo.findAll (),HttpStatus.OK );
        
    }

    //O> Update
    
    //UpdateUserByID
    @PutMapping("/{Id}")
    public ResponseEntity<UserModel> updateUserById(@PathVariable int Id, @RequestBody UserModel updatedUser) {

        //Easy Way
        UserModel user = userRepo.findById ( Id ).orElseThrow (() -> new ResponseStatusException ( HttpStatus.NOT_FOUND ));

        if (updatedUser.getName () != null) {
            user.setName ( updatedUser.getName () );
        }
        if (updatedUser.getEmail () != null) {
            user.setEmail ( updatedUser.getEmail () );
        }
        if (updatedUser.getGender () != null) {
            user.setGender ( updatedUser.getGender () );
        }
        if (updatedUser.getStatus () != null) {
            user.setStatus ( updatedUser.getStatus () );
        }


        //Harder Way
//        UserModel userOld = userRepo.findById ( Id ).orElseThrow (() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        Field [] fields = updatedUser.getClass ().getFields ();
//
//        for (Field f:
//             fields) {
//            if (f != null) {
//                String fName = f.getName ();
//                try {
//
//                    Field fieldToUpdate = userOld.getClass ().getField (fName);
//
//                    fieldToUpdate.set ( UserModel,f );
//
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace ();
//                }
//            }
//        }

        //first attempt

//        Field [] userOldFields = userOld.getClass ().getFields ();
//        Field [] userNewFields = updatedUser.getClass ().getFields ();
//
//        for (int i = 0; i < userOldFields.length; i++) {
//            if (userNewFields[i] != null) {
//                userOldFields[i] = userNewFields[i];
//            }
//
//        }

        userRepo.save ( user );

        return new ResponseEntity<> ( user,HttpStatus.OK);
    }


    //X> Delete
    //DeleteByID
    @DeleteMapping("/{Id}")
    public ResponseEntity deleteUserById(@PathVariable int Id){

        userRepo.deleteById ( Id );

       return new ResponseEntity ("Deleted",HttpStatus.NO_CONTENT );
    }



    //X> Fill repo
    @GetMapping("/fill")
    public ResponseEntity getAllUser( RestTemplate restTemplate ){

        try {

            ArrayList<UserModel> allUserArray = new ArrayList<>();

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity( URL, UserModel[].class );

            allUserArray.addAll ( Arrays.asList ( Objects.requireNonNull ( response.getBody () ) ) );

            int totalPageNumber = Integer.parseInt (
                    Objects.requireNonNull (
                            response.getHeaders ().get (
                                    "X-Pagination-Pages" ) ).get ( 0 ) );

            for (int i = 2; i <= 5; i++) {

                String tempURl = URL + "?page=" + i;

                UserModel[] pageData = restTemplate.getForObject ( tempURl,UserModel[].class );

                assert pageData != null;

                allUserArray.addAll ( Arrays.asList ( pageData));

            }

            for (UserModel user:
                 allUserArray) {

                userRepo.save ( user );
            }

            System.out.println (allUserArray.size ());

            return new ResponseEntity ( allUserArray,HttpStatus.OK );

        } catch( Exception e ){

            System.out.println (e.getClass ());
            System.out.println (e.getMessage ());

            return new ResponseEntity( e.getMessage (), HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }

}
