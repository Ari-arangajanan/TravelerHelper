package com.MITProjectService.bot.request;import com.MITProjectService.bot.domain.Bookings;import com.fasterxml.jackson.annotation.JsonProperty;import jakarta.persistence.*;import lombok.Data;import java.time.LocalDateTime;@Datapublic class PaymentRequest {    private Long id;    private Long bookingId;    private Double amount;    private Integer paymentStatus; // PAID 2, PENDING 1, FAILED 3, REFUNDED 4    private LocalDateTime createdAt;}