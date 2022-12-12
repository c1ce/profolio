package com.profolio.entity;

import com.profolio.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="account")
@Getter
@Setter
@ToString
public class Account {
    @Id
    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
