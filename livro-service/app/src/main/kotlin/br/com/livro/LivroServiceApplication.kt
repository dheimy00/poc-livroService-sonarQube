package br.com.livro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class LivroServiceApplication

fun main(args: Array<String>) {
    runApplication<LivroServiceApplication>(*args)
}

