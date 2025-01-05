package com.MITProjectService.bot.dao.jpaRepos.specification;import com.MITProjectService.bot.domain.HiredServicesLog;import com.MITProjectService.bot.request.HiredServicesLogRequest;import jakarta.persistence.criteria.Predicate;import org.springframework.data.jpa.domain.Specification;import org.springframework.stereotype.Service;@Servicepublic class HiredServiceLogSpecification {    public static Specification<HiredServicesLog> hiredServiceLogFilter(HiredServicesLogRequest request) {        return (root, query, criteriaBuilder) -> {            Predicate predicate = criteriaBuilder.conjunction();            if (request.getBookingId() != null && request.getBookingId() != 0) {                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), request.getBookingId()));            }            if (request.getStatus() != null) {                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), request.getStatus()));            }            return predicate;        };    }}