package com.crud.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 4, message = "Password at least 4 chars")
    private String password;
}
