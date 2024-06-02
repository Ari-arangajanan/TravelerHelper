package com.MITProjectService.admin.dao.impl;import com.MITProjectService.admin.dao.JpaRepos.SysUserRepository;import com.MITProjectService.admin.dao.SystemUserRepo;import com.MITProjectService.admin.domain.system.Authorities;import com.MITProjectService.admin.domain.system.SysUser;import com.MITProjectService.admin.request.SystemUserRequest;import com.MITProjectService.admin.vo.SystemUserVo;import com.MITProjectService.exceptionhandling.DataTransactionalException;import jakarta.persistence.EntityManager;import jakarta.persistence.NoResultException;import jakarta.persistence.TypedQuery;import jakarta.transaction.Transactional;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageRequest;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Repository;@Repositorypublic class SystemUserRepoImpl implements SystemUserRepo {    private final Logger log = LoggerFactory.getLogger(SystemUserRepoImpl.class);    private final EntityManager entityManager;    private final SysUserRepository sysUserRepository;    public SystemUserRepoImpl(EntityManager entityManager, SysUserRepository sysUserRepository) {        this.entityManager = entityManager;        this.sysUserRepository = sysUserRepository;    }    @Override    @Transactional    public SysUser save(SysUser sysUser) {        try {            // Ensure authorities are not null            if (sysUser.getAuthorities() != null) {                // Loop through authorities and save if not already persisted                for (Authorities authority : sysUser.getAuthorities()) {                    if (authority.getId() == null) {                        entityManager.persist(authority);                    }                }            }            entityManager.persist(sysUser);            return sysUser;        }catch (DataIntegrityViolationException e){            if (e.getRootCause() != null &&  e.getLocalizedMessage().contains("Duplicate entry")){                throw new DataTransactionalException("The username or phone number already exists. Please choose a different username.");            }else throw new DataTransactionalException(e.getLocalizedMessage());        }        catch (Exception e){            log.error("Error saving user", e);            if (e.getMessage()!= null &&  e.getLocalizedMessage().contains("Duplicate entry")){                throw new DataTransactionalException("The username already exists. Please choose a different username.");            }else throw new DataTransactionalException(e.getLocalizedMessage());        }    }    @Override    public SysUser findByUserName(String name) {        TypedQuery<SysUser> query = entityManager.createQuery("SELECT u FROM SysUser u WHERE u.username = :userName AND u.status = 1", SysUser.class);        query.setParameter("userName", name);        SysUser sysUser = null;        try {            sysUser = query.getSingleResult();            return sysUser;        } catch (NoResultException e) { // Handle case where no user is found            System.out.println("result newfound "+ e);            return null;        } catch (Exception e) {            // Handle other potential exceptions            log.error("Error finding user by username", e);            return null;        }    }    @Override    public Page<SysUser> findAll(SystemUserRequest systemUserRequest) {        Pageable pageable = PageRequest.of(systemUserRequest.getPage(), systemUserRequest.getLimit());        Page<SysUser> page = sysUserRepository.findAll(pageable);        return page;    }}