if echo "${SPACE_NAME}" | grep -q "abc"; then
    echo "if triggered"
    ${SPACE_NAME}="new-space-name-123"
fi