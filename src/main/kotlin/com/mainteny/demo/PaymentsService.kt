package com.mainteny.demo

import org.springframework.stereotype.Service

@Service
interface PaymentsService {
  fun addPaymentRequest(paymentRequest: Payments): Payments
  fun getPaymentStatus(clientRequestId: String): PaymentStates
  fun getAllPendingPaymentStates(): List<PendingPaymentStates>
  fun getAllSuccessfulPendingPaymentStates(state: String): List<PendingPaymentStates>
  fun getAllFailedPendingPaymentStates(state: String): List<PendingPaymentStates>
}
