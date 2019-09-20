package com.gallelloit.rest.webservices.restfulwebservices.user;

import com.gallelloit.rest.webservices.restfulwebservices.post.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int userCounter = 3;
    private static int postCounter = 6;

    static {

        List<Post> posts1 = new ArrayList<>();
        List<Post> posts2 = new ArrayList<>();
        List<Post> posts3 = new ArrayList<>();

        posts1.add(new Post(1, new Date(),"Primer post de Pedro", "Este es el primer post"));
        posts1.add(new Post(2, new Date(), "Segundo post de Pedro", "Este es el segundo post"));
        posts1.add(new Post(3, new Date(), "Tercer post de Pedro", "Este es el tercer post"));

        posts2.add(new Post(4, new Date(),"Primer post de María", "Este es el primer post"));
        posts2.add(new Post(5, new Date(), "Segundo post de María", "Este es el segundo post"));
        posts2.add(new Post(6, new Date(), "Tercer post de María", "Este es el tercer post"));

/*
        posts3.add(new Post(7, new Date(),"Primer post de Marta", "Este es el primer post"));
        posts3.add(new Post(8, new Date(), "Segundo post de Marta", "Este es el segundo post"));
        posts3.add(new Post(9, new Date(), "Tercer post de Marta", "Este es el tercer post"));
*/

        users.add(new User (1, "Pedro", new Date(), posts1));
        users.add(new User (2, "María", new Date(), posts2));
        users.add(new User (3, "Marta", new Date(), posts3));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if (user.getId() == null){
            user.setId(++userCounter);
        }
        users.add(user);

        return user;
    }

    public User findOne(int id){

        for (User user: users
             ) {
            if (user.getId() == id){
                return user;
            }
        }

        return null;

    }

    //Retrieve all posts for a user
    public List<Post> getUserPosts(int userId){
        User user = this.findOne(userId);

        if (user != null){
            return user.getPosts();
        }

        return null;
    }

    //Retrieve one post
    public Post getPostFromUser(User user, int postId){
        if (user == null){
            return null;
        }else{
            for (Post post: user.getPosts()
                 ) {
                if (post.getId() == postId){
                    return post;
                }
            }
            return null;
        }
    }

    //Create post for a user
    public Post savePostForUser(User user, Post post) {
        if (post.getId() == null){
            post.setId(++postCounter);
        }
        user.getPosts().add(post);

        return post;
    }

    //Delete a user
    public User deleteUserById(int userId) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()){

            User user = iterator.next();

            if (user.getId() == userId){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
