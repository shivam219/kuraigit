package com.kurai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kurai.entity.SubscriptionPlanType;
import com.kurai.repository.SubscriptionPlanTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanTypeService {

	@Autowired
    private SubscriptionPlanTypeRepository repository;

    public List<SubscriptionPlanType> getAllTypes() {
        return repository.findAll();
    }
}
