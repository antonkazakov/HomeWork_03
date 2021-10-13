package otus.homework.flowcats.domain

interface Mapper<DTO, Domain> {
    fun toDomain(dto: DTO): Domain
}