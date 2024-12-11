package com.MITProjectAdmin.Controller.management;import com.MITProjectService.bot.Services.ServiceCategoryService;import com.MITProjectService.bot.dao.jpaRepos.specification.SnServiceCategorySpecificationFilter;import com.MITProjectService.bot.domain.ServiceCategory;import com.MITProjectService.bot.request.ServiceCategoryRequest;import org.springframework.data.domain.PageRequest;import org.springframework.data.jpa.domain.Specification;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;import org.springframework.data.domain.Pageable;import java.util.List;@CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")@RestController@RequestMapping(path = "/admin/category")public class ServiceCategoryController {    private final ServiceCategoryService serviceCategoryService;    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {        this.serviceCategoryService = serviceCategoryService;    }    // Create or Update a ServiceCategory    @PostMapping("/CreateOrUpdate")    public ResponseEntity<ServiceCategory> createOrUpdateServiceCategory(@Valid @RequestBody ServiceCategory serviceCategory) {        return ResponseEntity.ok(serviceCategoryService.saveCategory(serviceCategory));    }    // Get all ServiceCategories    @GetMapping("/index")    public ResponseEntity<?> getAllServiceCategories(@RequestBody ServiceCategoryRequest request) {        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());        Specification<ServiceCategory> filter = SnServiceCategorySpecificationFilter.serviceCategoryNameFilter(request);        return ResponseEntity.ok(serviceCategoryService.getAllCategories(filter,pageable));    }    // Get a ServiceCategory by ID    @GetMapping("/{id}")    public ResponseEntity<ServiceCategory> getServiceCategoryById(@Valid @PathVariable Long id) {        return serviceCategoryService.getCategoryById(id)                .map(ResponseEntity::ok)                .orElse(ResponseEntity.notFound().build());    }    // Delete a ServiceCategory by ID    @DeleteMapping("/delete/{id}")    public ResponseEntity<Void> deleteServiceCategoryById(@Valid @PathVariable Long id) {        serviceCategoryService.deleteCategory(id);        return ResponseEntity.noContent().build();    }    // Update status of a ServiceCategory    @PatchMapping("/{id}/status")    public ResponseEntity<ServiceCategory> updateStatus(@Valid @PathVariable Long id, @Valid @RequestParam int status) {        return ResponseEntity.ok(serviceCategoryService.updateStatus(id, status));    }}