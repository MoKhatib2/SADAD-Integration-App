package com.example.SadadApi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.SadadApi.dtos.SadadRecordDto;
import com.example.SadadApi.models.*;
import com.example.SadadApi.repositories.*;
import com.example.SadadApi.responses.*;
import com.example.SadadApi.services.impl.SadadServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

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
                List.of(new SadadRecordDto.CostCenterDto(1L, new BigDecimal("50.00")))
        );
    }

    private void setupRepositoryMocks() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(new Organization()));
        when(legalEntityRepository.findById(1L)).thenReturn(Optional.of(new LegalEntity()));
        when(bankRepository.findById(1L)).thenReturn(Optional.of(new Bank()));
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(new BankAccount()));
        when(billerRepository.findById(1L)).thenReturn(Optional.of(new Biller()));
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(new Vendor()));
        when(vendorSiteRepository.findById(1L)).thenReturn(Optional.of(new VendorSite()));
        when(invoiceTypeRepository.findById(1L)).thenReturn(Optional.of(new InvoiceType()));
        when(expenseAccountRepositroy.findById(1L)).thenReturn(Optional.of(new ExpenseAccount()));
        when(businessRepository.findById(1L)).thenReturn(Optional.of(new Business()));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(new Location()));
    }

    @Test
    void testCreateRecord_Success() {
        SadadRecordDto dto = buildDto();
        setupRepositoryMocks();

        SadadRecord savedRecord = new SadadRecord();
        savedRecord.setId(100L);
        when(sadadRecordRepository.save(any(SadadRecord.class))).thenReturn(savedRecord);

        GenericResponse<SadadRecordResponse> response = sadadRecordService.createRecord(dto);

        assertNotNull(response);
        assertEquals("Record created successfully", response.message());
        assertNotNull(response.data());
        verify(sadadRecordRepository, times(1)).save(any(SadadRecord.class));
    }

    @Test
    void testCreateRecord_OrganizationNotFound() {
        SadadRecordDto dto = buildDto();
        when(organizationRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.createRecord(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Organization not found"));
    }

    @Test
    void testCreateRecord_VendorNotFound() {
        SadadRecordDto dto = buildDto();
        setupRepositoryMocks();
        when(vendorRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> {
            sadadRecordService.createRecord(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Vendor not found"));
    }
}
