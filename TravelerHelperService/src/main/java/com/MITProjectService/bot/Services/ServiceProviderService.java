package com.MITProjectService.bot.Services;import com.MITProjectService.bot.domain.ServiceProvider;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;public interface ServiceProviderService {    void saveServiceProvider(ServiceProvider serviceProvider);    Page<ServiceProvider> getAllServiceProvider(Pageable pageable);}