package com.yngvark.pc_init

import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.io.IOException
import java.util.concurrent.TimeUnit

class RobotHelper(private val robot: Robot) {
    fun click(x: Int, y: Int): RobotHelper {
        robot.mouseMove(x, y)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

        return this
    }

    fun click(clicks:List<Click>, pauseInbetween:Long):RobotHelper {
        clicks.forEach {
            click(it)
            println("Clicking: $it")
            Thread.sleep(pauseInbetween)
        }

        return this
    }

    fun click(click:Click):RobotHelper {
        robot.mouseMove(click.x, click.y)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

        return this
    }

    private fun getKeyEventsForChar(char:Char):List<Int> {
        val specialChars = mapOf('$' to listOfNotNull(KeyEvent.VK_SHIFT, KeyEvent.VK_4))

        if (specialChars.containsKey(char)) {
            return specialChars[char] ?: error("no such key: $char")
        }

        if (char.isUpperCase()) {
            val keyEvent:Int = KeyEvent.getExtendedKeyCodeForChar(char.toInt())
            return listOf(KeyEvent.VK_SHIFT, keyEvent)
        }

        return listOf(KeyEvent.getExtendedKeyCodeForChar(char.toInt()))
    }

    fun type(text: String):RobotHelper {
        text.forEach {
            val keyEventsForChar:List<Int> = getKeyEventsForChar(it)
            keyEventsForChar.forEach { keyEvent -> robot.keyPress(keyEvent)}
            keyEventsForChar.reversed().forEach { keyEvent -> robot.keyRelease(keyEvent)}
        }

        return this;
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
                //.directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()

            proc.waitFor(5, TimeUnit.SECONDS)
            return proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    fun pressAndRelease(vararg keyCodes: Int): RobotHelper {
        keyCodes.forEach { robot.keyPress(it) }
        keyCodes.reversed().forEach { robot.keyRelease(it) }

        return this
    }

    fun sleep(ms: Long): RobotHelper {
        Thread.sleep(ms)
        return this
    }

    fun getClipboardContents(): String {
        return Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String
    }
}
