## Mainteny Demo Service

This service accepts payment requests and processes it asynchronously.

While a payment request is added, the response is sent with the state of the payment request as ACCEPTED.

Then after a random interval from 0 to 10 minutes, the status will be randomly changed to either SUCCESS or FAILED,
and when the state is updated it is also added to a pending payment states table which acts as a queue of processed Payment requests.