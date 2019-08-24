echo "Checking data status of a specific endpoint"
echo $MY_JOB_NAMESPACE
zero=0
if [[ $MY_JOB_NAMESPACE == default ]]; then
    result=$(curl property:9084/customer|
    sed -e 's/[{}]/''/g' |
    awk -v k="text" '{n=split($0,a,","); print n}')
else
  result=$(curl property.$MY_JOB_NAMESPACE:9084/customer|
    sed -e 's/[{}]/''/g' |
    awk -v k="text" '{n=split($0,a,","); print n}')
fi
if [[ "$result" -eq "$zero" ]];then
    echo "Trigger setup for customers!"
else
    echo "All good"
fi