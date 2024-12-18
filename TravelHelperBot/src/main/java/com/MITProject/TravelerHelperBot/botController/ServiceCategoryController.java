package com.MITProject.TravelerHelperBot.botController;import com.MITProjectService.bot.Services.ServiceCategoryService;import com.MITProjectService.bot.dao.jpaRepos.specification.SnServiceCategorySpecificationFilter;import com.MITProjectService.bot.domain.ServiceCategory;import com.MITProjectService.bot.request.ServiceCategoryRequest;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import java.util.List;@CrossOrigin(origins = "http://localhost:5175", allowCredentials = "true")@RestController@RequestMapping(path = "/app/category")public class ServiceCategoryController {    private final ServiceCategoryService serviceCategoryService;    public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {        this.serviceCategoryService = serviceCategoryService;    }    // Get all ServiceCategories    @PostMapping("/getAllCategory")    public ResponseEntity<?> getAllServiceCategories(@RequestBody ServiceCategoryRequest request) {        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());        Specification<ServiceCategory> filter = SnServiceCategorySpecificationFilter.serviceCategoryNameFilter(request);        return ResponseEntity.ok(serviceCategoryService.getAllCategories(filter,pageable));    }    // Get a ServiceCategory by ID    @GetMapping("/{id}")    public ResponseEntity<ServiceCategory> getServiceCategoryById(@PathVariable Long id) {        return serviceCategoryService.getCategoryById(id)                .map(ResponseEntity::ok)                .orElse(ResponseEntity.notFound().build());    }    // Delete a ServiceCategory by ID    @DeleteMapping("/delete/{id}")    public ResponseEntity<Void> deleteServiceCategoryById(@PathVariable Long id) {        serviceCategoryService.deleteCategory(id);        return ResponseEntity.noContent().build();    }    // Update status of a ServiceCategory    @PatchMapping("/{id}/status")    public ResponseEntity<ServiceCategory> updateStatus(@PathVariable Long id, @RequestParam int status) {        return ResponseEntity.ok(serviceCategoryService.updateStatus(id, status));    }}