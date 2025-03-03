package com.MITProjectService.bot.domain;import com.fasterxml.jackson.annotation.JsonFormat;import com.fasterxml.jackson.annotation.JsonIgnore;import com.fasterxml.jackson.annotation.JsonProperty;import jakarta.persistence.*;import lombok.Data;import java.io.Serial;import java.io.Serializable;import java.time.LocalDateTime;@Data@Entity@Table(name = "bookings")public class Bookings implements Serializable {    @Serial    private static final long serialVersionUID = 1L;    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    private Long id;    @ManyToOne(cascade = CascadeType.ALL)    @JoinColumn(name = "service_type_id", referencedColumnName = "id", nullable = false)    @JsonIgnore    private ServiceType serviceType; // Links to ServiceType    @ManyToOne(cascade = CascadeType.ALL)    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)    @JsonIgnore    private SnUser user; // Links to User    @Column(name = "status", nullable = false)    private int status; // PENDING 0, ACCEPTED 1, REJECTED 2, CANCELLED 3, COMPLETED 4    @Column(name = "created_at", nullable = false, updatable = false)    private LocalDateTime createdAt;    @Column(name = "updated_at")    private LocalDateTime updatedAt;    @Column(name = "booking_date_from", nullable = false)    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")    private LocalDateTime bookingDateFrom;    @Column(name = "booking_date_to", nullable = false)    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")    private LocalDateTime bookingDateTo;    @Column(name = "price", nullable = false)    private Double price;    @Column(name = "reject_reason")    private String rejectReason;    @Column(name = "order_id")    private String orderId;    @ManyToOne(cascade = CascadeType.ALL)    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)    @JsonIgnore    private Services services;    @Transient    @JsonProperty("user_id")    public Long getUserId() {        return user.getId();    }    @Transient    @JsonProperty("serviceProvider_id")    public Long getServiceProviderId() {        return services.getServiceProvider().getId();    }    @Transient    @JsonProperty("service_id")    public Long getServiceId() {        return services.getId();    }    @Transient    @JsonProperty("user_name")    public String getUserName() {        return user.getUserName()   ;    }    @Transient    @JsonProperty("service_provider_name")    public String getServiceProviderName() {        return services.getServiceProvider().getUserName();    }    @Transient    @JsonProperty("service_name")    public String getServiceName() {        return services.getServiceName();    }    @PrePersist    protected void onCreate() {        createdAt = LocalDateTime.now();        services = serviceType.getServiceRegistration().getServices();    }    @PreUpdate    protected void onUpdate() {        updatedAt = LocalDateTime.now();    }}