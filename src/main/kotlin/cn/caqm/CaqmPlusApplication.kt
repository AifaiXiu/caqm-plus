package cn.caqm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = arrayOf("cn.caqm.repo"))
@EntityScan(basePackages = arrayOf("cn.caqm.model.entity"))
class CaqmPlusApplication

fun main(args: Array<String>) {
    runApplication<CaqmPlusApplication>(*args)
}
