package org.player64.mariuszspetitions;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Setter
@Getter
public class Petition {
    private Long id;

    @NotEmpty(message = "Title must not be empty")
    @Pattern(regexp = ".*\\S.*", message = "Title must not be only whitespace")
    private String title;
    private String createdAt;

    @Valid
    @NotNull(message = "User must not be null")
    private User createdBy;

    @Valid
    private ArrayList<User> signUsers = new ArrayList<>();

    public Petition() {
    }

    public Petition(String title, User createdBy) {
        this.title = title;
        this.createdBy = createdBy;
        this.createdAt = java.time.LocalDate.now().toString();
    }
}
