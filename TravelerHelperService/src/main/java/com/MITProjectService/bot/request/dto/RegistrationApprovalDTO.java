package com.MITProjectService.bot.request.dto;import lombok.Data;@Datapublic class RegistrationApprovalDTO {    private Long requestId;    private String status; // "Approved" or "Rejected"    private String reason; // Optional reason for rejection}