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

    return WORKER_SUFFIX
}

return this





def set_suffix() {
    WORKER_SUFFIX = ""
    if(env.CLOUDFOUNDRY_SPACE.contains("integration-2")) {
        WORKER_SUFFIX = "-2"
    }
    else if(env.CLOUDFOUNDRY_SPACE.contains("-performance")) {
        WORKER_SUFFIX = "-performance"
    }
    else if(env.CLOUDFOUNDRY_SPACE.contains)("canary-acceptance") {
        WORKER_SUFFIX = "-acceptance"
    }
    return WORKER_SUFFIX
}
return this
