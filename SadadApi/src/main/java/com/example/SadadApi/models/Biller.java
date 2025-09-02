package com.example.SadadApi.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Biller extends BaseEntity{

    @OneToMany(mappedBy = "biller")
    private Set<Vendor> vendors;
}
