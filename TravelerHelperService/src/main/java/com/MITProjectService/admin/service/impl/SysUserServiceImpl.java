package com.MITProjectService.admin.service.impl;import com.MITProjectService.admin.dao.JpaRepos.AuthoritiesJpaRepo;import com.MITProjectService.admin.dao.JpaRepos.SysUserRepository;import com.MITProjectService.admin.dao.SystemUserRepo;import com.MITProjectService.admin.domain.system.Authorities;import com.MITProjectService.admin.domain.system.SysUser;import com.MITProjectService.admin.request.SystemUserRequest;import com.MITProjectService.admin.service.SysUserService;import com.MITProjectService.admin.vo.SystemUserVo;import jakarta.persistence.EntityManager;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.data.jpa.domain.Specification;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import org.springframework.web.bind.annotation.RequestBody;import org.w3c.dom.Entity;import java.util.Date;import java.util.HashSet;import java.util.Optional;import java.util.Set;@Servicepublic class SysUserServiceImpl implements SysUserService {    private final SystemUserRepo systemUserRepo;    private final SysUserRepository sysUserRepository;    private final AuthoritiesJpaRepo authoritiesJpaRepo;    @Autowired    public SysUserServiceImpl(SystemUserRepo systemUserRepo, SysUserRepository sysUserRepository, AuthoritiesJpaRepo authoritiesJpaRepo) {        this.systemUserRepo = systemUserRepo;        this.sysUserRepository = sysUserRepository;        this.authoritiesJpaRepo = authoritiesJpaRepo;    }    @Override    public boolean addSysUser(SysUser sysUser) {        sysUser.setCreate_time(new Date());        return systemUserRepo.save(sysUser);    }    @Override    public SysUser findByUserName(String userName) {        return systemUserRepo.findByUserName(userName);    }    @Override    public Page<SysUser> findAll(Specification<SysUser> specification, Pageable pageable) {        return sysUserRepository.findAll(specification,pageable);    }    @Override    @Transactional    public SysUser updateSysUser(SysUser sysUser) {        Optional<SysUser> sysUserOptional = sysUserRepository.findById(Long.valueOf(sysUser.getId()));        if (sysUserOptional.isPresent()){            SysUser updateUser = sysUserOptional.get();            updateUser.setUsername(sysUser.getUsername());            updateUser.setEmail(sysUser.getEmail());            updateUser.setPhone(sysUser.getPhone());            updateUser.setAddress(sysUser.getAddress());            updateUser.setAvatar(sysUser.getAvatar());            updateUser.setUpdate_time(new Date());            if (sysUser.getAuthorities() != null){                Set<Authorities> updatedAuthorities = new HashSet<>();                for (Authorities authority : sysUser.getAuthorities()) {                    if (authority.getId() != null) {                        Optional<Authorities> tempObj = authoritiesJpaRepo.findById(authority.getId());                        Authorities updateAuthority = null;                        if (tempObj.isPresent()) {                            updateAuthority = tempObj.get();                            updateAuthority.setRole(authority.getRole());                            updatedAuthorities.add(authoritiesJpaRepo.save(updateAuthority));                        }else {                            updatedAuthorities.add(authoritiesJpaRepo.save(authority));                        }                    }else {                        updatedAuthorities.add(authoritiesJpaRepo.save(authority));                    }                }                updateUser.setAuthorities(updatedAuthorities);                //sysUserRepository.save(updateUser            }            return sysUserRepository.save(updateUser);        }else {            throw new RuntimeException("User not found");        }    }    @Override    public boolean deleteSysUser(Long id) {        try {            Optional<SysUser> sysUserOptional = sysUserRepository.findById(id);            if (sysUserOptional.isPresent()) {                sysUserRepository.delete(sysUserOptional.get());                sysUserRepository.flush();                return true;            }            return false;        }catch (Exception e){            throw new RuntimeException(e);        }    }}