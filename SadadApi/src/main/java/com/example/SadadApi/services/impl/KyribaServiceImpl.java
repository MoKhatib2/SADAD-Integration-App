package com.example.SadadApi.services.impl;

import org.springframework.stereotype.Service;

import com.example.SadadApi.models.SadadRecord;
import com.example.SadadApi.services.KyribaService;

@Service
public class KyribaServiceImpl implements KyribaService{

    @Override
    public boolean releaseToKyriba(SadadRecord record) {
        return true;
    }

}
