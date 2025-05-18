package com.polymind.entity.user;

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
    private Long userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    private String plat;
    @Column(name = "user_profile_image")
    private String profile_image;

    @Builder
    public User(Long userId,String name, String email, String plat, String profile_image) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.plat = plat;
        this.profile_image = profile_image;
    }
}
