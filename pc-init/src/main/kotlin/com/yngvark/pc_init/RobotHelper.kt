package com.yngvark.pc_init

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.io.IOException
import java.util.concurrent.TimeUnit

class RobotHelper(private val robot: Robot) {
    fun click(x: Int, y: Int) {
        robot.mouseMove(x, y)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

    fun click(click:Click):RobotHelper {
        robot.mouseMove(click.x, click.y)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        return this;
    }

    private fun getKeyEventsForChar(char:Char):List<Int> {
        val specialChars = mapOf('$' to listOfNotNull(KeyEvent.VK_SHIFT, KeyEvent.VK_4))

        return specialChars.getOrElse(char) {
            listOf(KeyEvent.getExtendedKeyCodeForChar(char.toInt()))
        }
    }

    fun type(text: String):RobotHelper {
        text.forEach {
            val keyEventsForChar:List<Int> = getKeyEventsForChar(it)
            keyEventsForChar.forEach { keyEvent -> robot.keyPress(keyEvent)}
            keyEventsForChar.reversed().forEach { keyEvent -> robot.keyRelease(keyEvent)}
        }

        return this;
    }

    fun enter() {
        robot.keyPress(KeyEvent.VK_ENTER)
        robot.keyRelease(KeyEvent.VK_ENTER)
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

    fun pressAndRelease(vararg keyCodes: Int) {
        keyCodes.forEach { robot.keyPress(it) }
        keyCodes.reversed().forEach { robot.keyRelease(it) }
    }

    fun sleep(ms: Long) {
        Thread.sleep(ms);
    }
}
