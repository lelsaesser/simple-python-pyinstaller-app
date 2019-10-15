if echo "${SPACE_NAME}" | grep -q "abc"; then
    echo "if triggered"
    export $SPACE_NAME="new-space-name-123"
fi