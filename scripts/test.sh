if echo "${SPACE_NAME}" | grep -q "abc"; then
    echo "if triggered"
    export env.SPACE_NAME="new-space-name-123"
fi