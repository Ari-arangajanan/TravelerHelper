package com.MITProjectService.bot.Services.impl;import com.MITProjectService.bot.Services.ServiceCategoryService;import com.MITProjectService.bot.dao.jpaRepos.ServiceCategoryRepository;import com.MITProjectService.bot.domain.ServiceCategory;import com.MITProjectService.bot.domain.SnUser;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;import org.springframework.stereotype.Service;import java.util.List;import java.util.Optional;@Servicepublic class ServiceCategoryServiceImpl implements ServiceCategoryService {    @Autowired    private ServiceCategoryRepository repository;    @Override    public Page<ServiceCategory> getAllCategories(Specification<ServiceCategory> filter, Pageable pageable) {        return repository.findAll(filter,pageable);    }    @Override    public Optional<ServiceCategory> getCategoryById(Long id) {        return repository.findById(id);    }    @Override    public ServiceCategory saveCategory(ServiceCategory serviceCategory) {        return repository.save(serviceCategory);    }    @Override    public ServiceCategory updateCategory(Long id, ServiceCategory updatedCategory) {        return repository.findById(id).map(category -> {            category.setCategoryName(updatedCategory.getCategoryName());            category.setDescription(updatedCategory.getDescription());            category.setStatus(updatedCategory.getStatus());            return repository.save(category);        }).orElseThrow(() -> new RuntimeException("Category not found with id " + id));    }    @Override    public void deleteCategory(Long id) {        repository.deleteById(id);    }    // Update status of a ServiceCategory    @Override    public ServiceCategory updateStatus(Long id, int status) {        Optional<ServiceCategory> optionalCategory = repository.findById(id);        if (optionalCategory.isPresent()) {            ServiceCategory category = optionalCategory.get();            category.setStatus(status);            return repository.save(category);        } else {            throw new RuntimeException("ServiceCategory not found with ID: " + id);        }    }}