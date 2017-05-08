kubectl create -f ballerina_test_service.yaml
kubectl create -f ballerina_test_rc.yaml

pods=$(kubectl get pods --output=jsonpath={.items..metadata.name})
json='['
for pod in $pods; do
         hostip=$(kubectl get pods "$pod" --output=jsonpath={.status.hostIP})
         lable=$(kubectl get pods "$pod" --output=jsonpath={.metadata.labels.name})
         servicedata=$(kubectl describe svc "$lable")
         json+='{"hostIP" :"'$hostip'", "lable" :"'$lable'", "ports" :['
         declare -a dataarray=($servicedata)
         let count=0
         for data in ${dataarray[@]}  ; do
            if [ "$data" = "NodePort:" ]; then
            IFS='/' read -a myarray <<< "${dataarray[$count+2]}"
            json+='{'
            json+='"protocol" :"'${dataarray[$count+1]}'",  "port" :"'${myarray[0]}'"'
            json+="},"
            fi

         ((count+=1))
         done
         i=$((${#json}-1))
         lastChr=${json:$i:1}

         if [ "$lastChr" = "," ]; then
         json=${json:0:${#json}-1}
         fi

         json+="]},"

done
json=${json:0:${#json}-1}

json+="]"

echo $json;

cat > deployment.json << EOF1
$json
EOF1
