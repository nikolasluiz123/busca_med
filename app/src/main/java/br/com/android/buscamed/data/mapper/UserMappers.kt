package br.com.android.buscamed.data.mapper

import br.com.android.buscamed.data.document.UserDocument
import br.com.android.buscamed.domain.model.User

fun UserDocument.toUser(): User {
    return User(
        id = id,
        name = name,
        normalizedName = normalizedName,
        email = email,
        password = null
    )
}

fun User.toUserDocument(): UserDocument {
    return UserDocument(
        id = id,
        name = name,
        normalizedName = normalizedName ?: "",
        email = email
    )
}
