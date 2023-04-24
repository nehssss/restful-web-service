package com.haitaos.socialmedia.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "users_details")
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @JsonProperty("user_id")
    private Integer id;
    @JsonProperty("user_name")
    @Size(min=2, message = "Name should have at least 2 characters.")
    private String name;
    @JsonProperty("birth_date")
    @Past(message = "Birth Date should be in the past.")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    public User() {
    }

}
