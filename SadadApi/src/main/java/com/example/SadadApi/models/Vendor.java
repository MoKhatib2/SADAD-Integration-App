package com.example.SadadApi.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Vendor extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "biller_id", nullable = false)
    private Biller biller;

    @OneToMany(mappedBy = "vendor")
    private Set<VendorSite> vendorSites = new HashSet<>();
}
