package com.MITProjectService.bot.Services;import com.MITProjectService.bot.domain.Services;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;public interface ServicesService {    Page<Services> getServicePage(Pageable pageRequest, Specification<Services> servicesSpecification);}