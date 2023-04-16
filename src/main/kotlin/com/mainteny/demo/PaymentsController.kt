package com.mainteny.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment")
class PaymentsController(
  @Autowired var paymentsService: PaymentsService
) {

  @PostMapping()
  fun addPaymentRequest(@RequestBody paymentRequest: Payments): Payments {
    return paymentsService.addPaymentRequest(paymentRequest)
  }

  @GetMapping("status/{clientRequestId}")
  fun getPaymentRequestState(@PathVariable clientRequestId: String): PaymentStates {
    return paymentsService.getPaymentStatus(clientRequestId)
  }

  @GetMapping("pending/all")
  fun getAllPendingPaymentStates(): List<PendingPaymentStates> {
    return paymentsService.getAllPendingPaymentStates()
  }

  @GetMapping("pending/success")
  fun getAllSuccessfulPendingPaymentStates(): List<PendingPaymentStates> {
    return paymentsService.getAllSuccessfulPendingPaymentStates("SUCCESS")
  }

  @GetMapping("pending/failed")
  fun getAllFailedPendingPaymentStates(): List<PendingPaymentStates> {
    return paymentsService.getAllFailedPendingPaymentStates("FAILED")
  }
}
