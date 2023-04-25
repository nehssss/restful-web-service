package com.haitaos.socialmedia.user;


import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class UserJPAResource {
    private UserRepository userRepository;

    private PostRepository postRepository;

    public UserJPAResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}") // spring data rest will auto hidde the id
    public EntityModel<User> retrieveUser(@PathVariable("id") int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("id:" + id);
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }


//    @GetMapping("/users/{id}")
//    public User retrieveUser(@PathVariable("id") Long id) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isEmpty())
//            throw new UserNotFoundException("id:" + id);
//        return user.get();
//    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userRepository.deleteById(id);
    }


    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable("id") int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("id:" + id);
        return user.get().getPosts();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saveUSer = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUSer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable("id") int id
            , @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("id:" + id);

        post.setUser(user.get());

        Post save = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }






}

