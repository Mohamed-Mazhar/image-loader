package com.example.photodisplayer.common.util

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8


fun generateHash(publicKey: String, privateKey: String, timestamp: Long): String {
    val md = MessageDigest.getInstance("MD5")
    val md5ByteArray = md.digest((timestamp.toString() + privateKey + publicKey).toByteArray(UTF_8))
    return md5ByteArray.toHex()
}

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

fun String.toHttps() = replace("http", "https")

fun encodeToBase64(key: String, value: String): String {
    val stringToEncode = "$key:$value"
    return Base64.encodeToString(stringToEncode.toByteArray(), Base64.NO_WRAP)
}


fun getDominantColor(bitmap: Bitmap?): Int {
    val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 2, true)
    val dominantColor = newBitmap.getPixel(0, 0)
    val secondaryDominantColor = newBitmap.getPixel(0, 1)
    Log.d("Picture Colors", "DominantColor = $dominantColor, SecondaryDominantColor = $secondaryDominantColor")
    newBitmap.recycle()
    return secondaryDominantColor
}

fun getContrastColor(color: Int): Int {
    val y = ((299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000).toDouble()
    return if (y >= 128) Color.BLACK else Color.WHITE
}
