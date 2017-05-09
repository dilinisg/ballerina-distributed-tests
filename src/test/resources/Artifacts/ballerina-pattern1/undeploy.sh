#!/bin/bash
echo $KUBERNETES_MASTER
echo "Setting Kubernetes Master"
echo $KUBERNETES_MASTER
echo "Removing the K8S PODS!!!!!"
kubectl delete rc,services,pods -l name="ballerina-test-version-1"
