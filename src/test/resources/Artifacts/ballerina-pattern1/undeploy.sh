#!/bin/bash
export KUBERNETES_MASTER=http://192.168.48.24:8080
echo "Setting Kubernetes Master"
echo $KUBERNETES_MASTER
echo "Removing the K8S PODS!!!!!"
kubectl delete rc,services,pods -l name="ballerina-test-version-1"
