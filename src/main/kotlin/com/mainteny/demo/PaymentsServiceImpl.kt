package com.mainteny.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.concurrent.schedule

@Service
class PaymentsServiceImpl(
  @Autowired var paymentsRepository: PaymentsRepository,
  @Autowired var paymentStatesRepository: PaymentStatesRepository,
  @Autowired var pendingPaymentStatesRepository: PendingPaymentsRepository
) : PaymentsService {
  override fun addPaymentRequest(paymentRequest: Payments): Payments {
    var payment = Payments()

    payment.clientRequestId = paymentRequest.clientRequestId
    payment.amount = paymentRequest.amount
    payment = paymentsRepository.save(payment)

    var paymentState = PaymentStates()
    paymentState.payment = payment
    paymentState.state = "ACCEPTED"
    paymentStatesRepository.save(paymentState)

    val randomTime: Long = (0..600000).random().toLong()
    println(randomTime / 60000)
    Timer("Updating Status", false).schedule(randomTime) {
      val state = listOf<String>("SUCCESS", "FAILED").random()
      var newPaymentState = PaymentStates()
      newPaymentState.payment = payment
      newPaymentState.state = state
      paymentStatesRepository.save(newPaymentState)

      var pendingPaymentState = PendingPaymentStates()
      pendingPaymentState.paymentStates = newPaymentState
      pendingPaymentStatesRepository.save(pendingPaymentState)
    }

    return payment
  }

  override fun getPaymentStatus(clientRequestId: String): PaymentStates {
    return paymentStatesRepository.findFirstOneByPaymentClientRequestIdOrderByCreatedAtDesc(clientRequestId)
  }

  override fun getAllPendingPaymentStates(): List<PendingPaymentStates> {
    return pendingPaymentStatesRepository.findAll().toList()
  }

  override fun getAllSuccessfulPendingPaymentStates(state: String): List<PendingPaymentStates> {
    return pendingPaymentStatesRepository.findPendingPaymentStatesByPaymentStatesState("SUCCESS")
  }

  override fun getAllFailedPendingPaymentStates(state: String): List<PendingPaymentStates> {
    return pendingPaymentStatesRepository.findPendingPaymentStatesByPaymentStatesState("FAILED")
  }
}
