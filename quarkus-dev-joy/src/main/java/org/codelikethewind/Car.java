package org.codelikethewind;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Car extends PanacheEntity {
    @Column(name="make")
    public String make;

    @Column(name="model")
    public String model;

    @Column(name="year")
    public String year;
}
