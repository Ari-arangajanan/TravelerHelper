package com.MITProjectService.bot.domain;import jakarta.persistence.*;import lombok.Data;import java.io.Serializable;import java.util.Date;@Data@Entity@Table(name = "sn_user")public class SnUser implements Serializable {    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    @Column(name = "id")    private Long id;    @Column(name = "telegram_id", unique = true)    private Long telegramId;    @Column(name = "user_name", nullable = false)    private String userName;    @Column(name = "first_name")    private String firstName;    @Column(name = "last_name")    private String lastName;    @Column(name = "email")    private String email;    @Column(name = "phone", unique = true)    private String phone;    @Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")    private Date updateTime;    @Column(name = "create_time", nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")    private Date registrationDate;    @Column(name = "preferred_language")    private String preferredLanguage;    @Column(name = "status", nullable = false, columnDefinition = "int default 1")    private int status;    @Column(name = "user_type")    private int type;}