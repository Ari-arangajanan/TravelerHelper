package com.MITProjectAdmin.Controller.management;import com.MITProjectService.bot.Services.ServiceRegistrationService;import com.MITProjectService.bot.Services.ServicesService;import com.MITProjectService.bot.dao.jpaRepos.specification.ServiceRegistrationFilterSpecification;import com.MITProjectService.bot.dao.jpaRepos.specification.SnServiceFilterSpecification;import com.MITProjectService.bot.domain.ServiceRegistration;import com.MITProjectService.bot.domain.Services;import com.MITProjectService.bot.request.ServiceRegistrationRequest;import com.MITProjectService.bot.request.ServiceRequest;import com.MITProjectService.bot.request.dto.RegistrationApprovalDTO;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;import java.util.Map;@RestController@RequestMapping("/admin/service")public class ServiceManagementController {    private final ServiceRegistrationService serviceRegistrationService;    private final ServicesService servicesService;    public ServiceManagementController(ServiceRegistrationService serviceRegistrationService, ServicesService servicesService) {        this.serviceRegistrationService = serviceRegistrationService;        this.servicesService = servicesService;    }    // Approve or Reject a Services Registration    @PutMapping("/registrations/approve")    public ResponseEntity<Map<String, ?> >updateRegistrationStatus(@Valid @RequestBody RegistrationApprovalDTO dto) {        Map<String, ?> updatedRegistration = serviceRegistrationService.updateRegistrationStatus(dto);        return ResponseEntity.ok(updatedRegistration);    }    // get services    @PostMapping(value = "/getServices")    public ResponseEntity<?> getAllServices(@Valid @RequestBody ServiceRequest request){        Pageable pageRequest = PageRequest.of(request.getPage(), request.getLimit());        Specification<Services> servicesSpecification = SnServiceFilterSpecification.servicesSpecificationFilter(request);        Page<Services> allServicesPage = servicesService.getServicePage(pageRequest,servicesSpecification);        return ResponseEntity.ok(allServicesPage);    }    @PostMapping(value = "/getAllServiceRegistration")    public ResponseEntity<?> getAllServiceRegistration(@Valid @RequestBody ServiceRegistrationRequest request){        Pageable pageRequest = PageRequest.of(request.getPage(), request.getLimit());        Specification<ServiceRegistration> serviceRegistrationSpecification = ServiceRegistrationFilterSpecification.serviceRegistrationSpecification(request);        Page<ServiceRegistration> serviceRegistrationPage = serviceRegistrationService.getAllRegistrationsPage(pageRequest,serviceRegistrationSpecification);        return ResponseEntity.ok(serviceRegistrationPage);    }}