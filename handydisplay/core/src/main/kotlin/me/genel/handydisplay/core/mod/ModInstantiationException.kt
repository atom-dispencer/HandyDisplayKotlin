package me.genel.handydisplay.core.mod

import java.lang.reflect.Constructor
import java.lang.reflect.Modifier

/**
 * Typically thrown by the `ModManager` while it is creating instances of mods' main classes, hence the presence of a `Constructor<AbstractMod>?` in the
 * constructor.
 */
class ModInstantiationException(
    message: String, clazz: Class<AbstractMod>, cons: Constructor<AbstractMod>?, cause: Throwable
) : Exception(
    StringBuilder()
        .appendLine(message)
        .appendLine("While loading mod: ${clazz.name}")
        .appendLine("With constructor: ${formatConstructorInfo(cons)}")
        .toString(), cause
)

private fun formatConstructorInfo(cons: Constructor<*>?): String {
    return if (cons != null) "${formatConstructorModifiers(cons.modifiers)} ${formatConstructorArgs(cons)}" else "<null constructor>"
}

private fun formatConstructorArgs(cons: Constructor<*>) = cons.parameterTypes.joinToString(", ", "(", ")") { it.name }
private fun formatConstructorModifiers(modifiers: Int): String {
    val builder = StringBuilder()

    if (Modifier.isPrivate(modifiers)) builder.append("private ")
    else if (Modifier.isPublic(modifiers)) builder.append("public ")
    else if (Modifier.isProtected(modifiers)) builder.append("protected ")

    if (Modifier.isFinal(modifiers)) builder.append("final ")

    return builder.toString()
}