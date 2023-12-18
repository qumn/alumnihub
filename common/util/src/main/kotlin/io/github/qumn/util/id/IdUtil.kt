package io.github.qumn.util.id


class IdUtil {
    companion object {
        val snowflake = Snowflake()
    }
}

fun IdUtil.Companion.nextId() =
    this.snowflake.nextId()
