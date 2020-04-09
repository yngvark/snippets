package com.yngvark.pc_init.robot

import java.io.Console
import java.lang.RuntimeException

class ConsolePasswordReader {

    fun read(): SomewhatSecureString {
        when (val console : Console? = System.console()) {
            null -> {
                throw RuntimeException("Not connected to console. Exiting")
            }
            else -> {
                val pw:CharArray = console.readPassword("Enter your password => ")
                return SomewhatSecureString(pw)
            }
        }
    }
}