package com.hocket.modules.tag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tag {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;
}
