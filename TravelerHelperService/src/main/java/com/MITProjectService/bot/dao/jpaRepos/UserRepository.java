package com.MITProjectService.bot.dao.jpaRepos;import com.MITProjectService.bot.domain.User;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;@Repositorypublic interface UserRepository extends JpaRepository<User, Long> {}