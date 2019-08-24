echo "Hello from the second script"
echo $MY_JOB_NAMESPACE
if $MY_JOB_NAMESPACE == default; then
  curl broker:8080/rest/init
else
  curl broker.$MY_JOB_NAMESPACE:8080/rest/init
fi
