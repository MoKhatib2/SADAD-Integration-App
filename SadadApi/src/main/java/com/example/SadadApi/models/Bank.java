package com.example.SadadApi.models;

import java.util.Set;

import jakarta.persistence.Entity;
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
public class Bank extends BaseEntity{

    @ManyToMany(mappedBy = "banks")
    private Set<Organization> organizations;

    @OneToMany(mappedBy = "bank")
    private Set<BankAccount> bankAccounts;
    
}
