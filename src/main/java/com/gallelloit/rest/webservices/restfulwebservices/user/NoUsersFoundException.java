package com.gallelloit.rest.webservices.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUsersFoundException extends RuntimeException {

    public NoUsersFoundException() {
        super("No user were found");
    }
}
