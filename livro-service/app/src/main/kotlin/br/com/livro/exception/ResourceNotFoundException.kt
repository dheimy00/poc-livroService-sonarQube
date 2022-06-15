package br.com.livro.exception

import java.lang.RuntimeException

class ResourceNotFoundException(message: String) : RuntimeException(message) {
}