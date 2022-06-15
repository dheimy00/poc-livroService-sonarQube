package br.com.livro.mapper

interface Mapper<Entity, Dto> {

    fun convertEntityToDto(entity:Entity): Dto

    fun convertDtoToEntity(dto:Dto): Entity
}