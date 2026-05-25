package com.example.infovet
import java.security.MessageDigest
import java.security.SecureRandom

fun gerarHashSenha(senha: String): String {

    val bytes = senha.toByteArray(Charsets.UTF_8)

    val md = MessageDigest.getInstance("SHA-256")

    val digest = md.digest(bytes)
    return digest.joinToString("") { "%02x".format(it) }
}

fun gerarSalt(): String {
    val random = SecureRandom()
    val saltBytes = ByteArray(16)
    random.nextBytes(saltBytes)

    return saltBytes.joinToString("") { "%02x".format(it) }
}

fun gerarToken(): String {
    val random = SecureRandom()
    val saltBytes = ByteArray(32)
    random.nextBytes(saltBytes)

    return saltBytes.joinToString("") { "%02x".format(it) }
}



fun isSenhaForte(senha: String): Boolean {

    val regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$".toRegex()
    return regex.matches(senha)
}
fun getExpiracao(): Long{
    val tempoAtual = System.currentTimeMillis()
    val vinteQuatroHorasEmMilissegundos = 24 * 60 * 60 * 1000 // 86.400.000 ms
    val tempoExpiracao = tempoAtual + vinteQuatroHorasEmMilissegundos
    return tempoExpiracao
}