package com.MITProjectService.admin.dao;import com.MITProjectService.admin.domain.SysUser;import jakarta.persistence.EntityManager;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Repository;@Repositorypublic class SystemUserRepoImpl implements SystemUserRepo{    private EntityManager entityManager;    @Autowired    public SystemUserRepoImpl(EntityManager entityManager) {        this.entityManager = entityManager;    }    @Override    public SysUser save(SysUser sysUser) {        entityManager.persist(sysUser);        return null;    }}