package com.jpa.mapping;

import com.jpa.doamin.Item;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
