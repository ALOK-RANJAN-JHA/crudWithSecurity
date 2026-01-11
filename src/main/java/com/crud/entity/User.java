package com.crud.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @Column(nullable = false)
    private String role = "USER";
}
