package com.vipulsolanki.kloudinary.utils

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.io.InputStream

class MessageDigestUtil {

    companion object {
        fun md5(inputstream: InputStream) : String {
            return String(Hex.encodeHex(DigestUtils.md5(inputstream)))
        }
    }
}