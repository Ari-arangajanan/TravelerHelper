package com.MITProjectService.bot.Services.impl;import com.MITProjectService.bot.Services.ServiceProviderService;import com.MITProjectService.bot.dao.jpaRepos.ServiceProviderRepository;import com.MITProjectService.bot.domain.ServiceProvider;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;@Servicepublic class ServiceProviderServiceImpl implements ServiceProviderService {    public ServiceProviderServiceImpl(ServiceProviderRepository serviceProviderRepository) {        this.serviceProviderRepository = serviceProviderRepository;    }    private final ServiceProviderRepository serviceProviderRepository;    @Override    @Transactional    public void saveServiceProvider(ServiceProvider serviceProvider) {        serviceProviderRepository.save(serviceProvider);    }    @Override    public Page<ServiceProvider> getAllServiceProvider(Specification<ServiceProvider> filter, Pageable pageable) {        return serviceProviderRepository.findAll(filter, pageable);    }}