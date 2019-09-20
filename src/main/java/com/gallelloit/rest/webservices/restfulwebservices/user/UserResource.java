package com.gallelloit.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.gallelloit.rest.webservices.restfulwebservices.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    UserDaoService userDaoService;

    // Retrieve all users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {

        List<User> userList = userDaoService.findAll();

        if (userList == null || userList.size() == 0){
            throw new NoUsersFoundException();
        }

        return userDaoService.findAll();
    }

    // Retrieve user
    @GetMapping("/users/{userId}")
    public Resource<User> retrieveUser(@PathVariable int userId){

        User user = userDaoService.findOne(userId);

        if (user == null){
            throw new UserNotFoundException(userId);
        }

        Resource<User> resource = new Resource<User>(user);

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user){
        User myUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(myUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //Retrieve all posts for a user
    @GetMapping("/users/{userId}/posts")
    public List<Post> retrieveAllPostsForUser(@PathVariable int userId){

        Resource<User> resource = this.retrieveUser(userId);

        User user = resource.getContent();

        List<Post> posts = user.getPosts();

        if (posts == null || posts.size() == 0){
            throw new PostsNotFoundForUserException(userId);
        }
        return userDaoService.getUserPosts(userId);
    }

    // Retrieve post by userId and postId
    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrievePostFromUser(@PathVariable int userId, @PathVariable int postId) {
        Resource<User> resource = this.retrieveUser(userId);

        User user = resource.getContent();

        Post post = userDaoService.getPostFromUser(user, postId);

        if (post == null){
            throw new PostNotFoundForUserException(user.getId(), postId);
        }

        return post;
    }

    // Create post for a user
    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int userId, @RequestBody Post post){
        Resource<User> resource = this.retrieveUser(userId);

        User user = resource.getContent();

        Post myPost = userDaoService.savePostForUser(user, post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(myPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    // Delete user by id
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUserById (@PathVariable int userId){
        User user = userDaoService.deleteUserById(userId);

        if (user == null){
            throw new UserNotFoundException(userId);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId).toUri();

        return ResponseEntity.noContent().build();
    }

}
