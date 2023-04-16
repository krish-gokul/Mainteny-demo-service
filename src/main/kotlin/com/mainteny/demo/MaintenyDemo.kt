package com.mainteny.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MaintenyDemo

fun main(args: Array<String>) {
  runApplication<MaintenyDemo>(*args)
}
