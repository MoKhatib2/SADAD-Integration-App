package com.example.SadadApi;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.SadadApi.models.*;
import com.example.SadadApi.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.SadadRecordDto;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.responses.SadadRecordResponse;
import com.example.SadadApi.services.impl.SadadServiceImpl;

class SadadRecordServiceTest {

    @Mock private OrganizationRepository organizationRepository;
    @Mock private LegalEntityRepository legalEntityRepository;
    @Mock private BankRepository bankRepository;
    @Mock private BankAccountRepository bankAccountRepository;
    @Mock private BillerRepository billerRepository;
    @Mock private VendorRepository vendorRepository;
    @Mock private VendorSiteRepository vendorSiteRepository;
    @Mock private InvoiceTypeRepository invoiceTypeRepository;
    @Mock private ExpenseAccountRepositroy expenseAccountRepositroy;
    @Mock private BusinessRepository businessRepository;
    @Mock private LocationRepository locationRepository;
    @Mock private SadadRecordRepository sadadRecordRepository;
    @Mock private CostCenterRepository costCenterRepository;

    @InjectMocks
    private SadadServiceImpl sadadRecordService; 

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mockUser = new User();
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        when(authentication.getPrincipal()).thenReturn(mockUser);
    }

    private SadadRecordDto buildDto() {
        return new SadadRecordDto(
                1L, 1L, 1L, 1L, 1L, 1L,
                1L, 1L, "INV-123", "SUB-123", new BigDecimal("500.00"),
                1L, 1L, 1L,
                List.of(new SadadRecordDto.CostCenterDto(1L, new BigDecimal("100.00")))
        );
    }

    private void setupRepositoryMocks() {
        Organization organization = new Organization();
        Bank bank = new Bank(); bank.setId(1L);
        organization.setBanks(Set.of(bank));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBank(bank);

        Vendor vendor = new Vendor(); vendor.setId(1L);
        VendorSite vendorSite = new VendorSite(); vendorSite.setId(1L); vendorSite.setVendor(vendor);

        Biller biller = new Biller(); biller.setId(1L); biller.setVendors(Set.of(vendor));

        LegalEntity legalEntity = new LegalEntity(); legalEntity.setId(1L);
        InvoiceType invoiceType = new InvoiceType(); invoiceType.setId(1L);
        ExpenseAccount expenseAccount = new ExpenseAccount(); expenseAccount.setId(1L);
        Business business = new Business(); business.setId(1L);
        Location location = new Location(); location.setId(1L);
        CostCenter costCenter = new CostCenter(); costCenter.setId(1L);

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(legalEntityRepository.findById(1L)).thenReturn(Optional.of(legalEntity));
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(billerRepository.findById(1L)).thenReturn(Optional.of(biller));
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        when(vendorSiteRepository.findById(1L)).thenReturn(Optional.of(vendorSite));
        when(invoiceTypeRepository.findById(1L)).thenReturn(Optional.of(invoiceType));
        when(expenseAccountRepositroy.findById(1L)).thenReturn(Optional.of(expenseAccount));
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(costCenterRepository.findById(1L)).thenReturn(Optional.of(costCenter));
    }

    @Test
    void testCreateRecord_successful() {
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();

        when(sadadRecordRepository.save(any(SadadRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        GenericResponse<SadadRecordResponse> response = sadadRecordService.createRecord(dto);

        assertEquals("Record created successfully", response.message());
        verify(sadadRecordRepository).save(any(SadadRecord.class));
    }

    @Test
    void testCreateRecord_OrganizationNotFound() {
        SadadRecordDto dto = buildDto();
        when(organizationRepository.findById(dto.organizationId()))
                .thenReturn(Optional.empty()); // simulate not found

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> sadadRecordService.createRecord(dto));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Organization not found"));
    }

    @Test
    void testCreateRecord_VendorNotFound() {
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();
        when(vendorRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.createRecord(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Vendor not found"));
    }

    @Test
    void testCreateRecord_BankNotBelongToOrganization() {
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();

        Organization organization = new Organization();
        organization.setId(1L);
        Bank bank2 = new Bank(); bank2.setId(2L);
        organization.setBanks(Set.of(bank2));

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));

        when(sadadRecordRepository.save(any(SadadRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.createRecord(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Bank does not belong to the specified organization"));
    }

    @Test
    void testCreateRecord_VendorSiteNotBelongToVendor() {
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();

        Vendor vendor2 = new Vendor(); vendor2.setId(2L);
        VendorSite vendorSite = new VendorSite(); vendorSite.setId(1L); vendorSite.setVendor(vendor2);

        when(vendorSiteRepository.findById(1L)).thenReturn(Optional.of(vendorSite));

        when(sadadRecordRepository.save(any(SadadRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.createRecord(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Vendor site does not belong to the specified vendor"));
    }

    @Test
    void testCreateRecord_TotalPercentageNotHundred() {
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();

        CostCenter costCenter1 = new CostCenter(); costCenter1.setId(1L);
        CostCenter costCenter2 = new CostCenter(); costCenter2.setId(2L);

        SadadRecordDto newDto = new SadadRecordDto(
                dto.organizationId() , dto.legalEntityId(), dto.remitterBankId(), dto.remitterBankAccountId(),
                dto.billerId(), dto.vendorId(), dto.vendorSiteId(), dto.invoiceTypeId(),
                "INV-123", "SUB-456", BigDecimal.TEN, dto.expenseAccountId(), dto.businessId(), dto.locationId(),
                List.of(new SadadRecordDto.CostCenterDto(costCenter1.getId(), new BigDecimal(40)), new SadadRecordDto.CostCenterDto(costCenter2.getId(), new BigDecimal(50)))
        );

        when(costCenterRepository.findById(dto.costCenters().get(0).costCenterId())).thenReturn(Optional.of(costCenter1));
        when(costCenterRepository.findById(dto.costCenters().get(1).costCenterId())).thenReturn(Optional.of(costCenter2));

        when(sadadRecordRepository.save(any(SadadRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.createRecord(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Total percentage must equal 100%"));
    }

    @Test
    void testUpdateRecord_successful() {
        // Arrange
        Long recordId = 100L;
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();

        SadadRecord existingRecord = new SadadRecord();
        existingRecord.setId(recordId);
        existingRecord.setStatus(SadadRecord.SadadStatus.SAVED);
        existingRecord.setInvoiceNumber("OLD-INV-999");

        when(sadadRecordRepository.findAllWithAllocationsAndCostCentersById(recordId)).thenReturn(Optional.of(existingRecord));

        when(sadadRecordRepository.save(any(SadadRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        GenericResponse<SadadRecordResponse> response = sadadRecordService.updateRecord(dto, recordId);

        assertEquals("Record updated successfully", response.message());
        assertEquals("INV-123", response.data().invoiceNumber());
        verify(sadadRecordRepository).findAllWithAllocationsAndCostCentersById(recordId);
        verify(sadadRecordRepository).save(any(SadadRecord.class));
    }

    @Test
    void testUpdateRecord_StatusNotSaved() {
        // Arrange
        Long recordId = 100L;
        setupRepositoryMocks();
        SadadRecordDto dto = buildDto();

        SadadRecord existingRecord = new SadadRecord();
        existingRecord.setId(recordId);
        existingRecord.setStatus(SadadRecord.SadadStatus.CONFIRMED);
        existingRecord.setInvoiceNumber("OLD-INV-999");

        // Repositories
        when(sadadRecordRepository.findAllWithAllocationsAndCostCentersById(recordId)).thenReturn(Optional.of(existingRecord));

        when(sadadRecordRepository.save(any(SadadRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.updateRecord(dto, recordId);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Can only update saved records"));

    }

}
