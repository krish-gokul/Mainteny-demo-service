package com.mainteny.demo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentsRepository : CrudRepository<Payments, Long> {
  fun findByClientRequestId(clientRequestId: String): Payments
}
