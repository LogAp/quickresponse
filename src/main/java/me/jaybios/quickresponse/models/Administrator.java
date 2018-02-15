package me.jaybios.quickresponse.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
@SuperUser
public class Administrator extends User {
}
