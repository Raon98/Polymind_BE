package com.tellin.entity.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_info")
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    private String provider;
    @Column(name = "user_profile_image")
    private String profile_image;

    @Column(name = "role")
    private String role;


    @Builder
    public User(String userId,String name, String email, String provider, String profile_image) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.profile_image = profile_image;
        this.role = "USER";
    }

    @Builder
    public User(String userId,String name, String email, String provider, String profile_image,String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.profile_image = profile_image;
        this.role = role;
    }
}
