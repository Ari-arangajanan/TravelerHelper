package com.MITProjectService.admin.service.impl;import com.MITProjectService.admin.dao.SystemUserRepo;import com.MITProjectService.admin.domain.SysUser;import com.MITProjectService.admin.service.SysUserService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Qualifier;import org.springframework.stereotype.Service;import java.util.Date;@Servicepublic class SysUserServiceImpl implements SysUserService {    private final SystemUserRepo systemUserRepo;    @Autowired    public SysUserServiceImpl(SystemUserRepo systemUserRepo) {        this.systemUserRepo = systemUserRepo;    }    @Override    public SysUser addSysUser(SysUser sysUser) {        sysUser.setCreate_time(new Date());        return systemUserRepo.save(sysUser);    }}