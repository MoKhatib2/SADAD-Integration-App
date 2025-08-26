package com.example.SadadApi.models;

import jakarta.persistence.Entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Vendor extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "biller_id")
    private Biller biller;

    @OneToMany(mappedBy = "vendor")
    private Set<VendorSite> vendorSites;
}
