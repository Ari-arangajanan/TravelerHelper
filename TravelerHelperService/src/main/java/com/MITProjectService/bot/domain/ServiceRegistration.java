package com.MITProjectService.bot.domain;import com.MITProjectService.bot.enums.ServiceRegistrationEnum;import com.fasterxml.jackson.annotation.JsonBackReference;import com.fasterxml.jackson.annotation.JsonIgnore;import jakarta.persistence.*;import lombok.Data;import lombok.ToString;import javax.validation.constraints.Max;import javax.validation.constraints.Min;import java.time.LocalDateTime;import java.util.List;@Data@Entity@Table(name = "service_registration")public class ServiceRegistration {    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    @Column(name = "id")    private Long id;    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = true) // Allow NULL    @ToString.Exclude // Exclude to avoid circular reference    @JsonIgnore    private Services services; // Links to Services (nullable until approved)    @ManyToOne    @JoinColumn(name = "registered_id",referencedColumnName = "id", nullable = false)    @ToString.Exclude // Exclude to avoid circular reference    private ServiceProvider registeredBy; // Services provider who registered the services    @Column(name = "status", nullable = false)    private int status; // e.g., Pending, Approved, Rejected    @Column(name = "registration_date", nullable = false)    private LocalDateTime registrationDate;    @Column(name = "approval_date")    private LocalDateTime approvalDate;    @Column(name = "latitude", nullable = false)    @Min(-90)    @Max(90)    private Double latitude; // Latitude of the services location    @Column(name = "longitude", nullable = false)    @Min(-180)    @Max(180)    private Double longitude; // Longitude of the services location    @OneToMany(mappedBy = "serviceRegistration", cascade = CascadeType.ALL, orphanRemoval = true)  // Orphan Removal: Enabled for attributes to clean up dynamically.    @ToString.Exclude // Exclude to avoid circular reference    @JsonBackReference    private List<ServiceAttribute> attributes; // List of dynamic attributes    @Column(name = "category_id", nullable = false)    private Long categoryId; // Services category reference    @Column(name = "service_name", nullable = false)    private String serviceName; // Name of the services being registered    @Column(name = "description", nullable = false)    private String description; // Description of the services being registered    @Column(name = "base_price", nullable = false)    private Double basePrice; // Base price of the services    @Column(name = "rejectReason")    private String rejectReason; // Base price of the services    @PrePersist    protected void onCreate() {        registrationDate = LocalDateTime.now();    }    @PreUpdate    protected void onUpdate() {        if (ServiceRegistrationEnum.APPROVED.getVal() == status) {            approvalDate = LocalDateTime.now();        }    }}