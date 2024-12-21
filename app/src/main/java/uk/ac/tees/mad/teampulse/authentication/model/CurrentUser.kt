package uk.ac.tees.mad.teampulse.authentication.model

data class CurrentUser(
    val name: String,
    val username: String,
    val phoneNumber: String,
    val profileImgUrl: String
)
