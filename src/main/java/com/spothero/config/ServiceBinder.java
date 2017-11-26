package com.spothero.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spothero.repositories.rate.RateScheduleRepository;
import com.spothero.repositories.rate.RateScheduleResourceRepository;
import com.spothero.services.rate.RateService;
import com.spothero.services.rate.RateServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class ServiceBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RateServiceImpl.class).to(RateService.class);
        bind(RateScheduleResourceRepository.class).to(RateScheduleRepository.class);

        bindFactory(JacksonClientConfig.class)
                .to(ObjectMapper.class)
                .qualifiedBy(new ClientMapperQualifier())
                .in(Singleton.class);
    }
}
