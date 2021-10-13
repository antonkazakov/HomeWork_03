package otus.homework.flowcats.api

import otus.homework.flowcats.domain.CatModel
import otus.homework.flowcats.domain.Mapper

class ApiMapper : Mapper<Fact, CatModel> {
    override fun toDomain(dto: Fact): CatModel {
        return CatModel(
            factText = dto.text
        )
    }
}