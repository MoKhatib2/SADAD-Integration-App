package com.example.SadadApi.services;

import com.example.SadadApi.models.SadadRecord;
import com.example.SadadApi.responses.FusionInvoiceResponse;

public interface FusionErpService {
    boolean createInvoice(SadadRecord record);
}
