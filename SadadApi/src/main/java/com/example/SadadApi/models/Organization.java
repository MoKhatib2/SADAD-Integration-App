package com.example.SadadApi.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy= "organization")
    private Set<BankAccount> bankAccounts = new HashSet<>();

}
