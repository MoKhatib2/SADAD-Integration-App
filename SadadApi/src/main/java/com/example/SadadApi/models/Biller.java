package com.example.SadadApi.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Biller extends BaseEntity{

    @OneToMany(mappedBy = "biller")
    private Set<Vendor> vendors;
}
