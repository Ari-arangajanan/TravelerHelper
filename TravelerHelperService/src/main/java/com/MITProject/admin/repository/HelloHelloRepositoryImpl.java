package com.MITProject.admin.repository;import com.MITProject.admin.domain.Hello;import jakarta.persistence.EntityManager;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Repository;import org.springframework.transaction.annotation.Transactional;@Repositorypublic class HelloHelloRepositoryImpl implements HelloRepository {    @Autowired    private EntityManager entityManager;    @Override    @Transactional    public void helloSave(Hello hello) {        entityManager.persist(hello);    }}