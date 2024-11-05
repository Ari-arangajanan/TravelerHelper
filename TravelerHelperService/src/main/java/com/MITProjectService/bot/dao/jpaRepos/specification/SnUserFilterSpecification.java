package com.MITProjectService.bot.dao.jpaRepos.specification;import com.MITProjectService.bot.domain.SnUser;import com.MITProjectService.bot.request.SnUserRequest;import jakarta.persistence.criteria.Predicate;import org.springframework.data.jpa.domain.Specification;import org.springframework.stereotype.Service;import org.springframework.util.StringUtils;@Servicepublic class SnUserFilterSpecification {    public static Specification<SnUser> findAndFilter(SnUserRequest request) {        return (root, query, cb) -> {            Predicate predicate = cb.conjunction();            if (StringUtils.hasText(request.getUserName())) {                predicate = cb.and(predicate, cb.like(root.get("userName"), "%" + request.getUserName() + "%"));            }            if (null != request.getTelegramId()) {                predicate = cb.and(predicate, cb.like(root.get("telegramId"), "%" + request.getTelegramId() + "%"));            }            predicate = cb.and(predicate, cb.equal(root.get("type"), 1));            return predicate;        };    }}