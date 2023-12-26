package io.github.qumn.framework.exception

public fun bizRequire(value: Boolean): Unit {
    if (!value) {
        throw BizNotAllowedException("操作不被允许")
    }
}

public fun bizRequire(value: Boolean, lazyError: () -> BizError): Unit {
    if (!value) {
        lazyError().toThrow()
    }
}

public fun bizRequireNotNull(value: Any?): Unit {
    if (value == null) {
        throw BizNotAllowedException("操作不被允许")
    }
}

public fun bizRequireNotNull(value: Any?, lazyMessage: () -> Any): Unit {
    if (value == null) {
        throw BizNotAllowedException(lazyMessage().toString())
    }
}
