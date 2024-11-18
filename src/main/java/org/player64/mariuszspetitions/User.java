package org.player64.mariuszspetitions;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;

    public User() {
    }

    public User(@NotEmpty String name, @NotEmpty @Email String email) {
        this.name = name;
        this.email = email;
    }
}