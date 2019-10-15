export WORKERR_SUFFIX = ""
if echo "${env.SPACE_NAME}" | grep -q "abc"; then
    echo "if triggered"
    export WORKERR_SUFFIX="newsuffix"
fi