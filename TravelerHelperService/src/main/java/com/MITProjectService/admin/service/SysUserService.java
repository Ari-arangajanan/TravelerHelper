package com.MITProjectService.admin.service;import com.MITProjectService.admin.domain.system.SysUser;public interface SysUserService {    SysUser addSysUser(SysUser sysUser);    SysUser findByUserName(String userName);}