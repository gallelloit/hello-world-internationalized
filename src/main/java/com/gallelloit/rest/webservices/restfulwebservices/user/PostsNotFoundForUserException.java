package com.gallelloit.rest.webservices.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostsNotFoundForUserException extends RuntimeException {

    public PostsNotFoundForUserException(int userId){
        super(String.format("No posts found for user: %d", userId));
    }

}
