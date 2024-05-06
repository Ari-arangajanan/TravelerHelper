package com.MITProjectService.admin.domain.system;import jakarta.persistence.*;import lombok.AllArgsConstructor;import lombok.Data;import lombok.NoArgsConstructor;import java.io.Serializable;import java.util.Date;import java.util.Set;@Data@Entity@AllArgsConstructor@NoArgsConstructor@Table(name = "system_user")public class SysUser implements Serializable {    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)    @Column(name = "id")    private Integer id;    @Column(name = "username",unique = true, nullable = false)    private String username;    @Column(name = "password",nullable = false)    private String password;    @Column(name = "role", nullable = false)    private String role;    @Column(name = "status", nullable = false, columnDefinition = "int default 1")    private Integer status;    @Column(name = "email", unique = true)    private String email;    @Column(name = "phone", unique = true)    private String phone;    @Column(name = "address")    private String address;    @Column(name = "avatar")    private String avatar;    @Column(name = "secretKey")    private String secretKey;    @Column(name = "create_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")    private Date create_time;    @Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")    private Date update_time;    @ManyToMany(fetch = FetchType.EAGER)    private Set<Authorities> authorities;}