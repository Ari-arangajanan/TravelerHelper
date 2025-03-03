package com.MITProjectService.bot.dao.jpaRepos;import com.MITProjectService.bot.domain.Payment;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;@Repositorypublic interface PaymentRepository extends JpaRepository<Payment, Long> {    @Query("SELECT p FROM Payment p WHERE p.booking.id = :bookingId")    Payment getByBookingId(Long bookingId);}