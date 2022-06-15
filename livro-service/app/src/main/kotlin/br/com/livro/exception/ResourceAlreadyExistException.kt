package br.com.livro.exception

import java.lang.RuntimeException

class ResourceAlreadyExistException(message: String) : RuntimeException(message) {
}