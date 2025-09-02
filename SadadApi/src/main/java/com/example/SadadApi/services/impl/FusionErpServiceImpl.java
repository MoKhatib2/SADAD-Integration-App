package com.example.SadadApi.services.impl;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.SadadApi.models.FusionInvoice;
import com.example.SadadApi.models.SadadRecord;
import com.example.SadadApi.repositories.FusionInvoiceRepository;
import com.example.SadadApi.services.FusionErpService;

@Service
public class FusionErpServiceImpl implements FusionErpService{
    final private FusionInvoiceRepository fusionInvoiceRepository;

    public FusionErpServiceImpl(FusionInvoiceRepository fusionInvoiceRepository) {
        this.fusionInvoiceRepository = fusionInvoiceRepository;
    }

    @Override
    public boolean createInvoice(SadadRecord record) {
        Random random = new Random();
        int randomNumber = random.nextInt(10);

        if (randomNumber <= 7) {
            FusionInvoice invoice = new FusionInvoice();
            invoice.setInvoiceNumber(record.getInvoiceNumber());
            invoice.setInvoiceDate(LocalDate.now());
            invoice.setInvoiceType(record.getInvoiceType().getName());
            invoice.setAmount(record.getAmount());
            invoice.setSupplierName(record.getVendor().getName());
            fusionInvoiceRepository.save(invoice);
            return true;
        }

       return false;

    }   

}
