package com.jpa.mapping;

import com.jpa.doamin.Item;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("typeA")
public class Album extends Item {

    private String artist;
}
