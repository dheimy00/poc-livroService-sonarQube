package br.com.livro.utils

import org.springframework.core.convert.converter.Converter
import org.springframework.lang.NonNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StringToUuidConverter: Converter<String,UUIDGenerator> {

    override fun convert(@NonNull uuid: String): UUIDGenerator? {
        return  UUIDGenerator(UUID.fromString(uuid))
    }
}