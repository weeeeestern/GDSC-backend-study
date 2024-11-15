package com.example.todoapi.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @JsonProperty("loginId")
    @Column(name = "login_id",columnDefinition = "varchar(20)")
    private String loginId;

    @JsonProperty("password")
    @Column(columnDefinition = "varchar(20)")
    private String password;

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

}
