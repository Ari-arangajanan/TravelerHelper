package com.MITProjectService.bot.domain;import jakarta.persistence.*;import lombok.Data;@Data@Entitypublic class ServiceProvider extends SnUser {    @Column(name = "category")    String category;    @Column(name = "location")    String location;}