def set_suffix() {
    WORKER_SUFFIX = ""
    SPACE = "test-integration-2"
    if(SPACE.contains("integration-2")) {
        WORKER_SUFFIX = "-2"
    }
    else if(SPACE.contains("-performance")) {
        WORKER_SUFFIX = "-performance"
    }
    else if(SPACE.contains)("acceptance") {
        WORKER_SUFFIX = "-acceptance"
    }

    return this
}