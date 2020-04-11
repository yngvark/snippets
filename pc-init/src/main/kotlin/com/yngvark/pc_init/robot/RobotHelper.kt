package com.yngvark.pc_init.robot

import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.io.IOException
import java.util.concurrent.TimeUnit


class RobotHelper(private val robot: Robot) {
    var debugMode: Boolean = false

    private val specialChars = mapOf(
        '$' to listOfNotNull(KeyEvent.VK_SHIFT, KeyEvent.VK_4),
        '_' to listOfNotNull(KeyEvent.VK_SHIFT, KeyEvent.VK_UNDERSCORE),
        '@' to listOfNotNull(KeyEvent.VK_SHIFT, KeyEvent.VK_2),
        '"' to listOfNotNull(KeyEvent.VK_SHIFT, KeyEvent.VK_QUOTE)
    )

    fun click(x: Int, y: Int): RobotHelper {
        robot.mouseMove(x, y)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

        return this
    }

    fun click(clicks:List<Click>, pauseAfterClick:Long): RobotHelper {
        clicks.forEach {
            click(it)
            Thread.sleep(pauseAfterClick)
        }

        return this
    }

    fun click(click: Click): RobotHelper {
        robot.mouseMove(click.x, click.y)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

        return this
    }

    private fun getKeyEventsForChar(char:Char):List<Int> {
        if (specialChars.containsKey(char)) {
            return specialChars[char] ?: error("no such key: $char")
        }

        if (char.isUpperCase()) {
            val keyEvent:Int = KeyEvent.getExtendedKeyCodeForChar(char.toInt())
            return listOf(KeyEvent.VK_SHIFT, keyEvent)
        }

        return listOf(KeyEvent.getExtendedKeyCodeForChar(char.toInt()))
    }

    fun type(text: String, charPause: Long = 0): RobotHelper {
        if (debugMode)
            println("Typing: $text")

        text.forEach {
            typeChar(it)

            if (charPause > 0)
                Thread.sleep(charPause)
        }
        return this
    }

    fun type(text: SomewhatSecureString): RobotHelper {
        if (debugMode)
            println("Typing (secure): ${text.asString()}")

        text.value.forEach {
            typeChar(it)
        }
        return this
    }

    private fun typeChar(char:Char) {
        val keyEventsForChar:List<Int> = getKeyEventsForChar(char)
        keyEventsForChar.forEach { keyEvent -> robot.keyPress(keyEvent)}
        keyEventsForChar.reversed().forEach { keyEvent -> robot.keyRelease(keyEvent)}
    }

    fun enter(): RobotHelper {
        robot.keyPress(KeyEvent.VK_ENTER)
        robot.keyRelease(KeyEvent.VK_ENTER)

        return this
    }

    fun run(cmd:String): String {
        try {
            val parts = cmd.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()

            proc.waitFor(1, TimeUnit.SECONDS)
            return proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    fun pressAndRelease(vararg keyCodes: Int): RobotHelper {
        keyCodes.forEach { robot.keyPress(it) }
        Thread.sleep(5)
        keyCodes.reversed().forEach { robot.keyRelease(it) }
        Thread.sleep(5)

        return this
    }

    fun sleep(ms: Long): RobotHelper {
        // println("Sleeping (debug=$debugMode): $ms")
        if (debugMode)
            Thread.sleep(ms + 1000)
        else
            Thread.sleep(ms)

        return this
    }

    fun getClipboardContents(): String {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String
        if (debugMode)
            println("Getting clipboard contents: $clipboard")

        return clipboard
    }

    fun clearClipboardContents() {
        val selection = StringSelection("")
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(selection, selection)
    }
}
