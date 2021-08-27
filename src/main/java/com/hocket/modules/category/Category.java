package com.hocket.modules.category;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Category {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    public Category(String title) {
        this.title = title;
    }
}
