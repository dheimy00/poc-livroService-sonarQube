package br.com.livro.utils

import java.util.UUID
import java.util.StringJoiner
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

class UUIDGenerator(@get:JsonValue val id: UUID) {

    override fun toString(): String {
        return StringJoiner(", ", UUIDGenerator::class.java.simpleName + "[", "]")
                .add(String.format("id=%s", id))
                .toString()
    }

    companion object {
        @JsonCreator
        fun fromString(id: String?): UUIDGenerator {
            return UUIDGenerator(UUID.fromString(id))
        }
    }
}