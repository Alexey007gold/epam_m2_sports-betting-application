package com.epam.training.sportsbetting.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin")
public class AdminEntity extends UserEntity {

    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Builder
    public AdminEntity(String email, char[] password, boolean enabled, @Size(min = 3) String name) {
        super(email, password, enabled);
        this.name = name;
    }
}
