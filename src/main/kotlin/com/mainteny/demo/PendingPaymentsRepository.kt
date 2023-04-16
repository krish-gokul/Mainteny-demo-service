package com.mainteny.demo

import org.springframework.data.repository.CrudRepository

interface PendingPaymentsRepository : CrudRepository<PendingPaymentStates, Long> {
  fun findPendingPaymentStatesByPaymentStatesState(state: String): List<PendingPaymentStates>
}
