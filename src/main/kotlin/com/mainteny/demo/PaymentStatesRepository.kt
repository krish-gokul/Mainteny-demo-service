package com.mainteny.demo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentStatesRepository : CrudRepository<PaymentStates, Long> {
  fun findFirstOneByPaymentClientRequestIdOrderByCreatedAtDesc(paymentClientRequestId: String): PaymentStates
}
