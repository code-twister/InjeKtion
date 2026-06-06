package dev.codetwister.injektion

const val CHAR_VERTICAL_BAR = "┃"
const val CHAR_BOX_TOP_RIGHT = "┗"
const val CHAR_DASH = "━"
const val CHAR_BOX_TOP_BOTTOM_RIGHT = "┣"

fun InjeKtionScope.printRecursive(prefix: String = "", isLast: Boolean = true, isRoot: Boolean = true) {
    val marker = when {
        isRoot -> "$CHAR_DASH$CHAR_DASH "
        isLast -> "$CHAR_BOX_TOP_RIGHT$CHAR_DASH$CHAR_DASH "
        else -> "$CHAR_BOX_TOP_BOTTOM_RIGHT$CHAR_DASH$CHAR_DASH "
    }

    println("$prefix$marker$name (factories: ${factories.size})")

    val newPrefix = when {
        isRoot -> "$prefix   "
        isLast -> "$prefix    "
        else -> "$prefix$CHAR_VERTICAL_BAR   "
    }

    val children = childScopes.values.toList()
    children.forEachIndexed { index, child ->
        child.printRecursive(newPrefix, index == children.size - 1, false)
    }
}
