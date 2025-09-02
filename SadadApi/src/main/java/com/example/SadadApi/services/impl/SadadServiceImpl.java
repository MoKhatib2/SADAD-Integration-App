package com.example.SadadApi.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.SadadRecordDto;
import com.example.SadadApi.dtos.SadadRecordStatusDto;
import com.example.SadadApi.models.Bank;
import com.example.SadadApi.models.BankAccount;
import com.example.SadadApi.models.BaseEntity;
import com.example.SadadApi.models.Biller;
import com.example.SadadApi.models.Business;
import com.example.SadadApi.models.CostCenter;
import com.example.SadadApi.models.ExpenseAccount;
import com.example.SadadApi.models.InvoiceType;
import com.example.SadadApi.models.LegalEntity;
import com.example.SadadApi.models.Location;
import com.example.SadadApi.models.Organization;
import com.example.SadadApi.models.SadadCostCenter;
import com.example.SadadApi.models.SadadRecord;
import com.example.SadadApi.models.SadadRecord.SadadStatus;
import com.example.SadadApi.models.User;
import com.example.SadadApi.models.Vendor;
import com.example.SadadApi.models.VendorSite;
import com.example.SadadApi.repositories.BankAccountRepository;
import com.example.SadadApi.repositories.BankRepository;
import com.example.SadadApi.repositories.BillerRepository;
import com.example.SadadApi.repositories.BusinessRepository;
import com.example.SadadApi.repositories.CostCenterRepository;
import com.example.SadadApi.repositories.ExpenseAccountRepositroy;
import com.example.SadadApi.repositories.InvoiceTypeRepository;
import com.example.SadadApi.repositories.LegalEntityRepository;
import com.example.SadadApi.repositories.LocationRepository;
import com.example.SadadApi.repositories.OrganizationRepository;
import com.example.SadadApi.repositories.SadadRecordRepository;
import com.example.SadadApi.repositories.VendorRepository;
import com.example.SadadApi.repositories.VendorSiteRepository;
import com.example.SadadApi.responses.BankAccountResponse;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.CostCenterResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.responses.SadadRecordResponse;
import com.example.SadadApi.services.FusionErpService;
import com.example.SadadApi.services.KyribaService;
import com.example.SadadApi.services.SadadRecordService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SadadServiceImpl implements SadadRecordService{
    final private OrganizationRepository organizationRepository;
    final private LegalEntityRepository legalEntityRepository;
    final private BankRepository bankRepository;
    final private BankAccountRepository bankAccountRepository;
    final private BillerRepository billerRepository;
    final private VendorRepository vendorRepository;
    final private VendorSiteRepository vendorSiteRepository;
    final private InvoiceTypeRepository invoiceTypeRepository;
    final private ExpenseAccountRepositroy expenseAccountRepositroy;
    final private BusinessRepository businessRepository;
    final private LocationRepository locationRepository;
    final private CostCenterRepository costCenterRepository;
    final private SadadRecordRepository sadadRecordRepository;

    final private KyribaService kyribaService;
    final private FusionErpService fusionErpService;

    @Override
    @Transactional
    public GenericResponse<SadadRecordResponse> createRecord(@Valid SadadRecordDto dto) {
        Organization organization = organizationRepository.findById(dto.organizationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization not found"));
    
        LegalEntity legalEntity = legalEntityRepository.findById(dto.legalEntityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found"));
    
        Bank remitterBank = bankRepository.findById(dto.remitterBankId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank not found"));
    
        BankAccount bankAccount = bankAccountRepository.findById(dto.remitterBankAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank Account not found"));
    
        Biller biller = billerRepository.findById(dto.billerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Biller not found"));
    
        Vendor vendor = vendorRepository.findById(dto.vendorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor not found"));
    
        VendorSite vendorSite = vendorSiteRepository.findById(dto.vendorSiteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor Site not found"));
    
        InvoiceType invoiceType = invoiceTypeRepository.findById(dto.invoiceTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice Type not found"));
    
        ExpenseAccount expenseAccount = expenseAccountRepositroy.findById(dto.expenseAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense Account not found"));
    
        Business business = businessRepository.findById(dto.businessId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business not found"));
    
        Location location = locationRepository.findById(dto.locationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found"));

        String validationMessage = validateDependencies(organization, remitterBank, bankAccount, biller, vendor, vendorSite);
        if (!validationMessage.equals("OK")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validationMessage);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        SadadRecord sadadRecord = new SadadRecord();
        sadadRecord.setOrganization(organization);
        sadadRecord.setLegalEntity(legalEntity);
        sadadRecord.setRemitterBank(remitterBank);
        sadadRecord.setBankAccount(bankAccount);
        sadadRecord.setBiller(biller);
        sadadRecord.setVendor(vendor);
        sadadRecord.setVendorSite(vendorSite);
        sadadRecord.setInvoiceType(invoiceType);
        sadadRecord.setInvoiceNumber(dto.invoiceNumber());
        sadadRecord.setSubscriptionAccountNumber(dto.subscriptionAccountNumber());
        sadadRecord.setAmount(dto.amount());
        sadadRecord.setExpenseAccount(expenseAccount);
        sadadRecord.setBusiness(business);
        sadadRecord.setLocation(location);
        sadadRecord.setStatus(SadadStatus.SAVED);
        sadadRecord.setCreatedAt(LocalDateTime.now());
        sadadRecord.setCreatedBy(user.getFirstName() + " " + user.getLastName());
        
        Set<SadadCostCenter> allocations = createAllocations(dto, sadadRecord, false);
        sadadRecord.setAllocations(allocations);
    
        SadadRecord saved = sadadRecordRepository.save(sadadRecord);
    
        SadadRecordResponse response = createResponse(saved);
    
        return new GenericResponse<>("Record created successfully", response);
    }
    
    @Override
    @Transactional
    public GenericResponse<SadadRecordResponse> updateRecord(@Valid SadadRecordDto dto, Long id) {
        if(id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        SadadRecord sadadRecord = sadadRecordRepository.findAllWithAllocationsAndCostCentersById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));

        if (sadadRecord.getStatus() != SadadStatus.SAVED) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only update saved records");
        }

        Organization organization = organizationRepository.findById(dto.organizationId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization not found"));

        LegalEntity legalEntity = legalEntityRepository.findById(dto.legalEntityId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found"));

        Bank remitterBank = bankRepository.findById(dto.remitterBankId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank not found"));

        BankAccount bankAccount = bankAccountRepository.findById(dto.remitterBankAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank Account not found"));

        Biller biller = billerRepository.findById(dto.billerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Biller not found"));

        Vendor vendor = vendorRepository.findById(dto.vendorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor not found"));

        VendorSite vendorSite = vendorSiteRepository.findById(dto.vendorSiteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor Site not found"));

        InvoiceType invoiceType = invoiceTypeRepository.findById(dto.invoiceTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invoice Type not found"));

        ExpenseAccount expenseAccount = expenseAccountRepositroy.findById(dto.expenseAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense Account not found"));

        Business business = businessRepository.findById(dto.businessId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business not found"));

        Location location = locationRepository.findById(dto.locationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found"));
        
        
        String validationError = validateDependencies(organization, remitterBank, bankAccount, biller, vendor, vendorSite);

        if (!validationError.equals("OK")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validationError);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        sadadRecord.setOrganization(organization);
        sadadRecord.setLegalEntity(legalEntity);
        sadadRecord.setRemitterBank(remitterBank);
        sadadRecord.setBankAccount(bankAccount);
        sadadRecord.setBiller(biller);
        sadadRecord.setVendor(vendor);
        sadadRecord.setVendorSite(vendorSite);
        sadadRecord.setInvoiceType(invoiceType);
        sadadRecord.setInvoiceNumber(dto.invoiceNumber());
        sadadRecord.setSubscriptionAccountNumber(dto.subscriptionAccountNumber());
        sadadRecord.setAmount(dto.amount());
        sadadRecord.setExpenseAccount(expenseAccount);
        sadadRecord.setBusiness(business);
        sadadRecord.setLocation(location);
        sadadRecord.setUpdatedAt(LocalDateTime.now());
        sadadRecord.setUpdatedBy(user.getFirstName() + " " + user.getLastName());
        createAllocations(dto, sadadRecord, true);

        SadadRecord updated = sadadRecordRepository.save(sadadRecord);
        SadadRecordResponse response = createResponse(updated);
        return new GenericResponse<>("Record updated successfully", response);
    }

    @Override
    @Transactional
    public GenericResponse<SadadRecordResponse> duplicateRecord(IdDto dto) {
         if(dto.id() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        SadadRecord sadadRecord = sadadRecordRepository.findAllWithAllocationsAndCostCentersById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));

        SadadRecord duplicated = new SadadRecord();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        duplicated.setOrganization(sadadRecord.getOrganization());
        duplicated.setLegalEntity(sadadRecord.getLegalEntity());
        duplicated.setRemitterBank(sadadRecord.getRemitterBank());
        duplicated.setBankAccount(sadadRecord.getBankAccount());
        duplicated.setBiller(sadadRecord.getBiller());
        duplicated.setVendor(sadadRecord.getVendor());
        duplicated.setVendorSite(sadadRecord.getVendorSite());
        duplicated.setInvoiceType(sadadRecord.getInvoiceType());
        duplicated.setInvoiceNumber(sadadRecord.getInvoiceNumber());
        duplicated.setSubscriptionAccountNumber(sadadRecord.getSubscriptionAccountNumber());
        duplicated.setAmount(sadadRecord.getAmount());
        duplicated.setExpenseAccount(sadadRecord.getExpenseAccount());
        duplicated.setBusiness(sadadRecord.getBusiness());
        duplicated.setLocation(sadadRecord.getLocation());
        duplicated.setStatus(sadadRecord.getStatus());
        duplicated.setCreatedAt(LocalDateTime.now());
        duplicated.setCreatedBy(user.getFirstName() + " " + user.getLastName());

        Set<SadadCostCenter> allocations = new HashSet<>();
        for (SadadCostCenter sadadCostCenter : sadadRecord.getAllocations()) {
            SadadCostCenter allocation = new SadadCostCenter();
            allocation.setSadadRecord(duplicated);
            allocation.setCostCenter(sadadCostCenter.getCostCenter());
            allocation.setPercentage(sadadCostCenter.getPercentage());
            allocations.add(allocation);
        }
        
        duplicated.setAllocations(allocations);
        duplicated = sadadRecordRepository.save(duplicated);

        SadadRecordResponse response = createResponse(duplicated);

        return new GenericResponse<>("Record duplicated successfully", response);
    }

    @Override
    public GenericResponse<SadadRecordResponse> confirmRecord(Long id) {
        if(id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        SadadRecord sadadRecord = sadadRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));
        
        if (sadadRecord.getStatus() == SadadStatus.CONFIRMED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is already confirmed");
        }
        if (sadadRecord.getStatus() != SadadStatus.SAVED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot confirm a " + sadadRecord.getStatus().toString() + " record");
        }
        sadadRecord.setStatus(SadadStatus.CONFIRMED);

        SadadRecord saved = sadadRecordRepository.save(sadadRecord);

        SadadRecordResponse response = createResponse(saved);

        return new GenericResponse<>("Record confirmed successfully", response);
    }

    @Override
    public GenericResponse<SadadRecordResponse> cancelRecord(Long id) {
        if(id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        SadadRecord sadadRecord = sadadRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));
        
        if (sadadRecord.getStatus() == SadadStatus.CANCELED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is already caneled");
        }
        if (sadadRecord.getStatus() != SadadStatus.SAVED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot cancel a " + sadadRecord.getStatus().toString() + " record");
        }
        sadadRecord.setStatus(SadadStatus.CANCELED);

        SadadRecord saved = sadadRecordRepository.save(sadadRecord);

        SadadRecordResponse response = createResponse(saved);

        return new GenericResponse<>("Record canceled successfully", response);
    }

    @Override
    public GenericResponse<SadadRecordResponse> releaseRecord(Long id) {
        if(id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        SadadRecord sadadRecord = sadadRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));

        if (sadadRecord.getStatus() == SadadStatus.RELEASED || sadadRecord.getStatus() == SadadStatus.INVOICE_FAILED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is already released");
        }   
        
        if (sadadRecord.getStatus() != SadadStatus.CONFIRMED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot cancel a " + sadadRecord.getStatus().toString() + " record");
        }

        boolean released = kyribaService.releaseToKyriba(sadadRecord);
        boolean invoiceCreated = false;
        if (released) {
                invoiceCreated = fusionErpService.createInvoice(sadadRecord);
            if (!invoiceCreated) {
                sadadRecord.setStatus(SadadStatus.INVOICE_FAILED);
            } else {
                sadadRecord.setStatus(SadadStatus.RELEASED);
            }
        } else {
                sadadRecord.setStatus(SadadStatus.CONFIRMED);
        }
    
        SadadRecord saved = sadadRecordRepository.save(sadadRecord);

        SadadRecordResponse response = createResponse(saved);
        return new GenericResponse<>("Record released successfully", response);
    }

    @Override
    public GenericResponse<SadadRecordResponse> retryInvoiceRecord(Long id) {
        if(id == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        SadadRecord sadadRecord = sadadRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));

        if (sadadRecord.getStatus() == SadadStatus.RELEASED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is already released");
        }   

        if (sadadRecord.getStatus() != SadadStatus.INVOICE_FAILED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Record was not released");
        }

        boolean invoiceCreated = fusionErpService.createInvoice(sadadRecord);
        if (!invoiceCreated) {
                sadadRecord.setStatus(SadadStatus.INVOICE_FAILED);
        } else {
                sadadRecord.setStatus(SadadStatus.RELEASED);
        }
         
        SadadRecord saved = sadadRecordRepository.save(sadadRecord);

        SadadRecordResponse response = createResponse(saved);
        return new GenericResponse<>("Invoice created successfully", response);
    }

    @Override
    public GenericResponse<List<SadadRecordResponse>> findAll() {
        List<SadadRecordResponse> records = sadadRecordRepository.findAllWithAllRelations().stream()
                .map(this::toDto)
                .toList();

        return new GenericResponse<>("records retrieved successfully", records);
    }

    @Override
    public GenericResponse<SadadRecordResponse> findById(Long id) {
        SadadRecord sadadRecord = sadadRecordRepository.findAllDataById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SADAD Record not found"));

        return new GenericResponse<>("record retrieved successfully", toDto(sadadRecord));
    }

    @Override
    public GenericResponse<List<SadadRecordResponse>> findByStatus(SadadRecordStatusDto SadadRecordStatusDto) {
        List<SadadRecordResponse> records = sadadRecordRepository.findAllByStatus(SadadStatus.valueOf(SadadRecordStatusDto.status().toUpperCase())).stream()
                .map(this::toDto)
                .toList();

        return new GenericResponse<>("records retrieved successfully", records);
    }

    private String validateDependencies(
        Organization organization, 
        Bank remitterBank,
        BankAccount bankAccount,
        Biller biller, 
        Vendor vendor, 
        VendorSite vendorSite
        ) {
                if (organization.getBanks()==null || !organization.getBanks().contains(remitterBank)) {
                        return "Bank does not belong to the specified organization";
                }
                
                if (!bankAccount.getBank().getId().equals(remitterBank.getId())) {
                        return "Bank account does not belong to the specified bank";
                }
        

                if (!vendorSite.getVendor().getId().equals(vendor.getId())) {
                        return "Vendor site does not belong to the specified vendor";
                }
        
                if (biller.getVendors()==null || !biller.getVendors().contains(vendor)) {
                        return "Vendor does not belong to the specified biller";
                }
        
                return "OK"; 
        }

   private Set<SadadCostCenter> createAllocations(SadadRecordDto dto, SadadRecord sadadRecord, boolean update) {
        Set<SadadCostCenter> allocations;
        if(update) {
                allocations = sadadRecord.getAllocations();
                sadadRecord.getAllocations().clear();
        } else {
                allocations = new HashSet<>();
        }
        BigDecimal totalPercentage = BigDecimal.ZERO;
        HashSet<Long> foundIds = new HashSet<>();
        for (SadadRecordDto.CostCenterDto costCenterDto : dto.costCenters()) {
            CostCenter costCenter = costCenterRepository.findById(costCenterDto.costCenterId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cost Center not found"));
            if (foundIds.contains(costCenter.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate cost centers");
            }
            foundIds.add(costCenter.getId());
            SadadCostCenter allocation = new SadadCostCenter();
            allocation.setSadadRecord(sadadRecord);
            allocation.setCostCenter(costCenter);
            allocation.setPercentage(costCenterDto.percentage());
    
            allocations.add(allocation);
            totalPercentage = totalPercentage.add(costCenterDto.percentage());
        }

        if (totalPercentage.compareTo(BigDecimal.valueOf(100)) != 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Total percentage must equal 100%");
        }
        return allocations;
       
   }

   private SadadRecordResponse createResponse(SadadRecord sadadRecord) {
        List<CostCenterResponse> costCentersResponse = new ArrayList<>();
        for (SadadCostCenter sadadCostCenter : sadadRecord.getAllocations()) {
            costCentersResponse.add(
                new CostCenterResponse(
                        sadadCostCenter.getCostCenter().getId(),
                        sadadCostCenter.getCostCenter().getCode(), 
                        sadadCostCenter.getCostCenter().getDescription(), 
                        sadadCostCenter.getPercentage()
                )
             );
        }
        return new SadadRecordResponse(
                sadadRecord.getId(),
                new CodeNameResponse(sadadRecord.getOrganization().getId(), sadadRecord.getOrganization().getCode(), sadadRecord.getOrganization().getName()),
                new CodeNameResponse(sadadRecord.getLegalEntity().getId(), sadadRecord.getLegalEntity().getCode(), sadadRecord.getLegalEntity().getName()),
                new CodeNameResponse(sadadRecord.getRemitterBank().getId(), sadadRecord.getRemitterBank().getCode(), sadadRecord.getRemitterBank().getName()),
                new BankAccountResponse(sadadRecord.getBankAccount().getId(), sadadRecord.getBankAccount().getAccountNumber(), sadadRecord.getBankAccount().getAccountName()),
                new CodeNameResponse(sadadRecord.getBiller().getId(), sadadRecord.getBiller().getCode(), sadadRecord.getBiller().getName()),
                new CodeNameResponse(sadadRecord.getVendor().getId(), sadadRecord.getVendor().getCode(), sadadRecord.getVendor().getName()),
                new CodeNameResponse(sadadRecord.getVendorSite().getId(), sadadRecord.getVendorSite().getCode(), sadadRecord.getVendorSite().getName()),
                new CodeNameResponse(sadadRecord.getInvoiceType().getId(), sadadRecord.getInvoiceType().getName(), sadadRecord.getInvoiceType().getName()),
                sadadRecord.getInvoiceNumber(),
                sadadRecord.getSubscriptionAccountNumber(),
                sadadRecord.getAmount(),
                new CodeNameResponse(sadadRecord.getExpenseAccount().getId(), sadadRecord.getExpenseAccount().getCode(), sadadRecord.getExpenseAccount().getName()),
                new CodeNameResponse(sadadRecord.getBusiness().getId(), sadadRecord.getBusiness().getCode(), sadadRecord.getBusiness().getName()),
                new CodeNameResponse(sadadRecord.getLocation().getId(), sadadRecord.getLocation().getCode(), sadadRecord.getLocation().getName()),
                costCentersResponse,
                sadadRecord.getStatus().toString()
        );
   }

   private SadadRecordResponse toDto(SadadRecord sadadRecord) {
        return new SadadRecordResponse(
            sadadRecord.getId(),
            toCodeNameResponse(sadadRecord.getOrganization()),
            toCodeNameResponse(sadadRecord.getLegalEntity()),
            toCodeNameResponse(sadadRecord.getRemitterBank()),
            new BankAccountResponse(sadadRecord.getBankAccount().getId(), sadadRecord.getBankAccount().getAccountNumber(), sadadRecord.getBankAccount().getAccountName()),
            toCodeNameResponse(sadadRecord.getBiller()),
            toCodeNameResponse(sadadRecord.getVendor()),
            toCodeNameResponse(sadadRecord.getVendorSite()),
            new CodeNameResponse(sadadRecord.getInvoiceType().getId(), sadadRecord.getInvoiceType().getName(), sadadRecord.getInvoiceType().getName()),
            sadadRecord.getInvoiceNumber(),
            sadadRecord.getSubscriptionAccountNumber(),
            sadadRecord.getAmount(),
            toCodeNameResponse(sadadRecord.getExpenseAccount()),
            toCodeNameResponse(sadadRecord.getBusiness()),
            toCodeNameResponse(sadadRecord.getLocation()),
            sadadRecord.getAllocations()
                .stream()
                .map(sc -> new CostCenterResponse(
                        sc.getCostCenter().getId(),
                        sc.getCostCenter().getCode(),
                        sc.getCostCenter().getDescription(),
                        sc.getPercentage()
                ))
                .toList(),
            sadadRecord.getStatus() != null ? sadadRecord.getStatus().name() : null
        );
    }
    private CodeNameResponse toCodeNameResponse(BaseEntity entity) {
        if (entity == null) return null;
        return new CodeNameResponse(entity.getId(), entity.getCode(), entity.getName());
    }

    
}
