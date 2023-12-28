package com.example.imageloader.common.util

import java.security.MessageDigest
import java.sql.Timestamp
import kotlin.text.Charsets.UTF_8

fun generateHash(publicKey: String, privateKey: String, timestamp: Long): String {
    val md = MessageDigest.getInstance("MD5")
    val md5ByteArray = md.digest((timestamp.toString() + privateKey + publicKey).toByteArray(UTF_8))
    return md5ByteArray.toHex()
}

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

fun String.toHttps() = replace("http","https")
