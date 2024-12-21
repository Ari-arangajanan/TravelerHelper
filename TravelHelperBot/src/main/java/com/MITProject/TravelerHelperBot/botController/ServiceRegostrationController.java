package com.MITProject.TravelerHelperBot.botController;import com.MITProjectService.bot.Services.ServiceRegistrationService;import com.MITProjectService.bot.dao.jpaRepos.specification.ServiceRegistrationFilterSpecification;import com.MITProjectService.bot.domain.ServiceRegistration;import com.MITProjectService.bot.request.ServiceRegistrationRequest;import com.MITProjectService.bot.request.dto.ServiceRegistrationDTO;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;@CrossOrigin(origins = "http://localhost:5174", originPatterns = "https://1758-61-245-171-47.ngrok-free.app", allowCredentials = "true")@RestController@RequestMapping(path = "app/serviceProvider/serviceRegistration")public class ServiceRegostrationController {    private final ServiceRegistrationService serviceRegistrationService;    public ServiceRegostrationController(ServiceRegistrationService serviceRegistrationService) {        this.serviceRegistrationService = serviceRegistrationService;    }    // 1. Submit a Services Registration    @PostMapping("/registrations")    public ResponseEntity<ServiceRegistration> submitServiceRegistration(            @RequestBody ServiceRegistrationDTO dto) {        ServiceRegistration registration = serviceRegistrationService.submitServiceRegistration(dto);        return ResponseEntity.ok(registration);    }    // 2. View All Registrations    @PostMapping("/AllRegistrationsByProvider")    public ResponseEntity<?> getAllRegistrations(@RequestBody ServiceRegistrationRequest request) {        PageRequest pageable = PageRequest.of(request.getPage(), request.getLimit());        Page<ServiceRegistration> registrations = serviceRegistrationService.getAllRegistrations(pageable,request.getServiceProviderId());        return ResponseEntity.ok(registrations);    }    // 3. View Registration Details    @GetMapping("/registrations/{registrationId}")    public ResponseEntity<ServiceRegistration> getRegistrationDetails(            @PathVariable Long registrationId) {        ServiceRegistration registration = serviceRegistrationService.getRegistrationDetails(registrationId);        return ResponseEntity.ok(registration);    }    // 4. View service regisrations Services    @PostMapping("/services")    public ResponseEntity<?> getAllServices(@RequestBody ServiceRegistrationRequest request) {        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());        Specification<ServiceRegistration> specification = ServiceRegistrationFilterSpecification.serviceRegistrationSpecification(request);        Page<ServiceRegistration> services = serviceRegistrationService.getAllRegistrationsPage(pageable,specification);        return ResponseEntity.ok(services);    }}