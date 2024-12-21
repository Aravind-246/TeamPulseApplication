package uk.ac.tees.mad.teampulse.authentication.response


sealed class AuthState{
    object idle: AuthState()
    object loading: AuthState()
    object success: AuthState()
    data class failure(val error: String): AuthState()
}