package com.example.SadadApi.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
public class Organization extends BaseEntity{
    @ManyToMany
    @JoinTable(
        name = "organization_bank", 
        joinColumns = @JoinColumn(name = "organization_id"), 
        inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private Set<Bank> banks;

    @OneToMany(mappedBy = "organization")
    private Set<LegalEntity> legalEntities;
}
