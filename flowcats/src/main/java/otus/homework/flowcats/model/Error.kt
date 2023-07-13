package otus.homework.flowcats.model

class Error(exception: Throwable) : Result<Throwable>(exception) {
}