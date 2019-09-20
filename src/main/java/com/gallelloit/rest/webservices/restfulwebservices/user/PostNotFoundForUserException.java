package com.gallelloit.rest.webservices.restfulwebservices.user;

public class PostNotFoundForUserException extends RuntimeException {
    public PostNotFoundForUserException(int userId, int postId) {
        super(String.format("Post %d not found for user: %d", postId, userId));
    }
}
