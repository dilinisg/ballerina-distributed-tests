#!/bin/bash
echo "Removing the K8S PODS!!!!!"
kubectl delete rc,services,pods -l name="ballerina-test-version-1"
