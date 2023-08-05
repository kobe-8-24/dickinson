

## 编排文件

### **hw-traefik-ingress.yml**

```yaml
apiVersion: traefik.containo.us/v1alpha1
kind: IngressRoute
metadata:
  name: my-ingressroute
spec:
  entryPoints:
    - web
  routes:
    - match: Host(`myapp.example.com`) && PathPrefix(`/`)
      kind: Rule
      services:
        - name: my-service
          port: 80

---
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: my-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80

---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
        - name: my-app-container
          image: myregistry/my-app-image:latest
          ports:
            - containerPort: 80

```

### **ocp-ingress.yml**

```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  namespace: ocp30-jx
  name: ocp30standard-jx-ingress
  labels:
    app: ocp30standard-jx-ingress
spec:
  rules:
  - http:
      paths:
      - path: /
        backend:
          serviceName: pms-im-framework-gateway
          servicePort: 80
      - path: /ocp-project-portal-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /pms-framework-hqocp-ui
        backend:
          serviceName: pms-framework-hqocp-ui
          servicePort: 80
      - path: /app-pms-framework-hqocp-ui
        backend:
          serviceName: pms-framework-portal-ui
          servicePort: 80
      - path: /platform-manage-web-ui
        backend:
          serviceName: platform-manage-web-ui
          servicePort: 80
      - path: /app-platform-manage-web-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /pms-project-center-portal-ui
        backend:
          serviceName: pms-project-center-portal-ui
          servicePort: 80
      - path: /pms-framework-cdn-ui
        pathType: ImplementationSpecific
        backend:
          serviceName: pms-framework-cdn-ui
          servicePort: 80
      - path: /ocp-project-manage-ui
        backend:
          serviceName: ocp-project-manage-ui
          servicePort: 80
      - path: /app-ocp-project-manage-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /ocp-project-center-ui
        backend:
          serviceName: ocp-project-center-ui
          servicePort: 80
      - path: /app-ocp-project-center-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /pms-framework-hqsl-latest-ui
        backend:
          serviceName: pms-framework-hqsl-latest-ui
          servicePort: 80
      - path: /app-pms-framework-hqsl-latest-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /pms-framework-hqsl-ui
        backend:
          serviceName: pms-framework-hqsl-ui
          servicePort: 80
      - path: /app-pms-framework-hqsl-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /pms-framework-tiles-ui
        backend:
          serviceName: pms-framework-tiles-ui
          servicePort: 80
      - path: /app-pms-framework-tiles-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80
      - path: /ocp-project-demand-ui
        backend:
          serviceName: ocp-project-demand-ui
          servicePort: 80
      - path: /app-ocp-project-demand-ui
        backend:
          serviceName: ocp-project-portal-ui
          servicePort: 80

```

### **pms-framework-nacos.yml**

```yaml
apiVersion: v1
kind: Service
metadata:
  namespace: ocp30-jx
  name: pms-framework-nacos-headless
  labels:
    app: pms-framework-nacos
  annotations:
    service.alpha.kubernetes.io/tolerate-unready-endpoints: "true"
spec:
  ports:
    - port: 80
      name: server
      targetPort: 8848
  clusterIP: None
  selector:
    app: pms-framework-nacos
---
apiVersion: apps/v1
kind: StatefulSet
metadata:
  namespace: ocp30-jx
  name: pms-framework-nacos
spec:
  serviceName: pms-framework-nacos-headless
  replicas: 2
  template:
    metadata:
      labels:
        app: pms-framework-nacos
      annotations:
        pod.alpha.kubernetes.io/initialized: "true"
    spec:
      imagePullSecrets:
        - name: docker-registry-ocp30
      containers:
        - name: pms-framework-nacos
          imagePullPolicy: Always
          image: swr.jx-region-1.sgic.sgcc.com.cn/pms30-jx/nacos-pg:2.0.3-2
          resources:
            limits:
              cpu: 2
              memory: 4Gi
            requests:
              memory: "2Gi"
              cpu: "500m"
          ports:
            - containerPort: 8848
              name: client
            - containerPort: 9848
              name: client-rpc
            - containerPort: 9849
              name: raft-rpc
          env:
            - name: NACOS_REPLICAS
              value: "2"
            - name: POSTGRESQL_SERVICE_HOST
              value: "25.60.235.7"
            - name: POSTGRESQL_SERVICE_DB_NAME
              value: "ocpdb"
            - name: POSTGRESQL_SERVICE_PORT
              value: "15432"
            - name: POSTGRESQL_SERVICE_USER
              value: "postgres"
            - name: POSTGRESQL_SERVICE_PASSWORD
              value: "s2.Cg-3kBl0#v-082!"
            - name: nacos_core_auth_enabled
              value: "false"
            - name: JVM_XMX
              value: "4g"
            - name: JVM_XMS
              value: "4g"
            - name: MODE
              value: "cluster"
            - name: NACOS_SERVER_PORT
              value: "8848"
            - name: PREFER_HOST_MODE
              value: "hostname"
            - name: NACOS_SERVERS
              value: "pms-framework-nacos-0.pms-framework-nacos-headless.ocp30-jx.svc.cluster.local:8848 pms-framework-nacos-1.pms-framework-nacos-headless.ocp30-jx.svc.cluster.local:8848"
            - name: SPRING_DATASOURCE_PLATFORM
              value: "postgresql"
            - name: POSTGRESQL_DATABASE_NUM
              value: "1"
            - name: POSTGRESQL_SERVICE_DB_PARAM
              value: "currentSchema=nacos_config"
            - name: NACOS_AUTH_ENABLE
              value: 'true'
            - name: DRIVER_CLASS_NAME
              value: "org.postgresql.Driver"
              #60版本新增配置解决nacos漏洞 
            - name: NACOS_AUTH_TOKEN
              value: "emh1aml0b25neW91cWlhbnlvdXNodWFpbmFuZGFveW91Y3VvbWE="
  selector:
    matchLabels:
      app: pms-framework-nacos
---
kind: Service
apiVersion: v1
metadata:
  annotations:   
    kubernetes.io/elb.class: union
    kubernetes.io/elb.health-check-flag: 'on'
    kubernetes.io/elb.health-check-option: '{"protocol":"TCP","delay":"5","timeout":"10","max_retries":"3"}'
    kubernetes.io/elb.id: 83192124-0ee7-4a5d-8057-631719c9c1a8
    kubernetes.io/elb.lb-algorithm: ROUND_ROBIN
  name: pms-framework-nacos-elb
  namespace: ocp30-jx
  labels:
    app: pms-framework-nacos
    name: pms-framework-nacos-elb
spec:
  selector:
    app: pms-framework-nacos
  ports:
    - name: cce-service-1
      protocol: TCP
      port: 30011
      targetPort: 8848
      nodePort: 32388
  type: LoadBalancer

---
apiVersion: v1
kind: Service
metadata:
  namespace: ocp30-jx
  name: pms-framework-nacos
  labels:
    app: pms-framework-nacos
spec:
  type: ClusterIP
  selector:
    app: pms-framework-nacos
  ports:
  - name: http
    port: 80
    targetPort: 8848
    protocol: TCP
  - name: client-rpc
    port: 1080
    targetPort: 9848
    protocol: TCP
  - name: raft-rpc
    port: 1081
    targetPort: 9849
```

### ocp-traefik.yml

```yaml
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: traefik-ingress-controller
rules:
  - apiGroups:
      - ""
    resources:
      - pods
      - services
      - endpoints
      - secrets
    verbs:
      - get
      - list
      - watch
  - apiGroups:
      - extensions
    resources:
      - ingresses
    verbs:
      - get
      - list
      - watch
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: traefik-ingress-controller
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: traefik-ingress-controller
subjects:
- kind: ServiceAccount
  name: traefik-ingress-controller
  namespace: kube-system
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: traefik-ingress-controller
  namespace: kube-system
---
kind: Deployment
apiVersion: apps/v1
metadata:
  name: traefik-ingress-controller
  namespace: kube-system
  labels:
    k8s-app: traefik-ingress-lb
spec:
  replicas: 5
  selector:
    matchLabels:
      k8s-app: traefik-ingress-lb
  template:
    metadata:
      labels:
        k8s-app: traefik-ingress-lb
        name: traefik-ingress-lb
    spec:
      serviceAccountName: traefik-ingress-controller
      terminationGracePeriodSeconds: 60
      containers:
      - image: harbor.nari.com.cn/library/traefik:1.7.20
        imagePullPolicy: IfNotPresent
        name: traefik-ingress-lb
        args:
        - --api
        - --kubernetes
        - --logLevel=INFO
---
apiVersion: v1
kind: Service
metadata:
  annotations:
    kubernetes.io/elb.class: union
    kubernetes.io/session-affinity-mode: SOURCE_IP
    kubernetes.io/elb.id: 3c7caa5a-a641-4bff-801a-feace27424b6   #elb的ID
  labels:
    k8s-app: traefik-ingress-lb
  name: traefik-ingress-lb
spec:
  loadBalancerIP: 10.78.42.242     #elb绑定的弹性IP
  externalTrafficPolicy: Local
  ports:
  - name: web
    port: 80   #修改为现场实际对外暴露端口
    targetPort: 80
    protocol: TCP
  - name: admin
    port: 8080
    targetPort: 80
    name: admin
    protocol: TCP
  selector:
    k8s-app: traefik-ingress-lb
  type: LoadBalancer
```

### traefik-ingress.yml

```yaml
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: traefik-ingress-controller
rules:
  - apiGroups:
      - ""
    resources:
      - pods
      - services
      - endpoints
      - secrets
    verbs:
      - get
      - list
      - watch
  - apiGroups:
      - extensions
    resources:
      - ingresses
    verbs:
      - get
      - list
      - watch
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: traefik-ingress-controller
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: traefik-ingress-controller
subjects:
- kind: ServiceAccount
  name: traefik-ingress-controller
  namespace: kube-system
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: traefik-ingress-controller
  namespace: kube-system
---
kind: Deployment
apiVersion: apps/v1
metadata:
  name: traefik-ingress-controller
  namespace: kube-system
  labels:
    k8s-app: traefik-ingress-lb
spec:
  replicas: 2
  selector:
    matchLabels:
      k8s-app: traefik-ingress-lb
  template:
    metadata:
      labels:
        k8s-app: traefik-ingress-lb
        name: traefik-ingress-lb
    spec:
      imagePullSecrets:
        - name: ocp30-system
      serviceAccountName: traefik-ingress-controller
      terminationGracePeriodSeconds: 60
      containers:
      - image: swr.jx-region-1.sgic.sgcc.com.cn/pms30-jx/traefik:1.7.4
        imagePullPolicy: IfNotPresent
        name: traefik-ingress-lb
        args:
        - --api
        - --kubernetes
        - --logLevel=INFO
---
kind: Service
apiVersion: v1
metadata:
  annotations:   
    kubernetes.io/elb.class: union
    kubernetes.io/session-affinity-mode: SOURCE_IP
    kubernetes.io/elb.id: 83192124-0ee7-4a5d-8057-631719c9c1a8   #elb的ID
  name: traefik-ingress-service
  namespace: kube-system
spec:
  selector:
    k8s-app: traefik-ingress-lb
  ports:
    - name: web
      protocol: TCP
      port: 30010 # 页面访问 http://25.60.235.2:30010
      targetPort: 80
  type: LoadBalancer
```

### pms30standard-dev-ingress.yml

```yaml
kind: Ingress
apiVersion: extensions/v1beta1
metadata:
  name: pms30standard-dev-ingress
  namespace: pms30-standard-dev
  selfLink: >-
    /apis/extensions/v1beta1/namespaces/pms30-standard-dev/ingresses/pms30standard-dev-ingress
  uid: 47cd3ba8-a2fc-4aed-9956-e6eddbbac878
  resourceVersion: '221698010'
  generation: 6
  creationTimestamp: '2023-07-04T01:33:34Z'
  labels:
    app: pms30standard-dev-ingress
  annotations:
    kubectl.kubernetes.io/last-applied-configuration: >
      {"apiVersion":"extensions/v1beta1","kind":"Ingress","metadata":{"annotations":{"nginx.ingress.kubernetes.io/proxy-body-size":"100m","nginx.ingress.kubernetes.io/proxy-read-timeout":"1800","nginx.ingress.kubernetes.io/proxy-send-timeout":"1800","nginx.ingress.kubernetes.io/use-regex":"true"},"labels":{"app":"pms30standard-dev-ingress"},"name":"pms30standard-dev-ingress","namespace":"pms30-standard-dev"},"spec":{"rules":[{"host":"pms.pms30standard.dev.com.cn","http":{"paths":[{"backend":{"serviceName":"pms-framework-gateway","servicePort":80},"path":"/pms-framework-portal-ui.*Copy"},{"backend":{"serviceName":"pms-framework-gateway","servicePort":80},"path":"/pms-framework-portal-ui/.*/pms-framework-portal-ui"},{"backend":{"serviceName":"pms-framework-gateway","servicePort":80},"path":"/"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/pms-framework-portal-ui"},{"backend":{"serviceName":"pms-framework-web-ui","servicePort":80},"path":"/pms-framework-web-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-web-ui"},{"backend":{"serviceName":"pms-public-twovotes-ui-tf","servicePort":80},"path":"/pms-public-twovotes-ui-tf"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-public-twovotes-ui-tf"},{"backend":{"serviceName":"hussar-front","servicePort":80},"path":"/lowcodeplat"},{"backend":{"serviceName":"pms-transformation-overhaul-ui","servicePort":80},"path":"/pms-transformation-overhaul-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-transformation-overhaul-ui"},{"backend":{"serviceName":"pms-directcurrent-operation-ui","servicePort":80},"path":"/pms-directcurrent-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-directcurrent-operation-ui"},{"backend":{"serviceName":"pms-transformation-operation-ui","servicePort":80},"path":"/pms-transformation-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-transformation-operation-ui"},{"backend":{"serviceName":"pms-framework-demand-ui","servicePort":80},"path":"/pms-framework-demand-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-demand-ui"},{"backend":{"serviceName":"pms-framework-tiles-ui","servicePort":80},"path":"/pms-framework-tiles-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-tiles-ui"},{"backend":{"serviceName":"pms-ts-ohl-ui","servicePort":80},"path":"/pms-ts-ohl-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ts-ohl-ui"},{"backend":{"serviceName":"pms-directcurrent-overhaul-ui","servicePort":80},"path":"/pms-directcurrent-overhaul-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-directcurrent-overhaul-ui"},{"backend":{"serviceName":"pms-distribution-operation-ui","servicePort":80},"path":"/pms-distribution-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-distribution-operation-ui"},{"backend":{"serviceName":"pms-public-twovotes-ui-dc","servicePort":80},"path":"/pms-public-twovotes-ui-dc"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-public-twovotes-ui-dc"},{"backend":{"serviceName":"pms-public-twovotes-ui-ds","servicePort":80},"path":"/pms-public-twovotes-ui-ds"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-public-twovotes-ui-ds"},{"backend":{"serviceName":"pms-public-twovotes-ui-tscable","servicePort":80},"path":"/pms-public-twovotes-ui-tscable"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-public-twovotes-ui-tscable"},{"backend":{"serviceName":"pms-public-twovotes-ui-tsohl","servicePort":80},"path":"/pms-public-twovotes-ui-tsohl"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-public-twovotes-ui-tsohl"},{"backend":{"serviceName":"pms-transmission-cableapplication-ui","servicePort":80},"path":"/pms-transmission-cableapplication-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-transmission-cableapplication-ui"},{"backend":{"serviceName":"pms-plan-techtransproject-ui","servicePort":80},"path":"/pms-plan-techtransproject-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-techtransproject-ui"},{"backend":{"serviceName":"pms-plan-overhaulproject-ui","servicePort":80},"path":"/pms-plan-overhaulproject-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-overhaulproject-ui"},{"backend":{"serviceName":"pms-plan-opemainproject-ui","servicePort":80},"path":"/pms-plan-opemainproject-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-opemainproject-ui"},{"backend":{"serviceName":"pms-plan-productioncosts-ui","servicePort":80},"path":"/pms-plan-productioncosts-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-productioncosts-ui"},{"backend":{"serviceName":"pms-framework-sclmt-ui","servicePort":80},"path":"/pms-framework-sclmt-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-sclmt-ui"},{"backend":{"serviceName":"pms-framework-hqsl-ui","servicePort":80},"path":"/pms-framework-hqsl-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-hqsl-ui"},{"backend":{"serviceName":"pms-framework-hqocp-ui","servicePort":80},"path":"/pms-framework-hqocp-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-hqocp-ui"},{"backend":{"serviceName":"pms-framework-config-ui","servicePort":80},"path":"/pms-framework-config-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-config-ui"},{"backend":{"serviceName":"pms-tech-supervise-ui","servicePort":80},"path":"/pms-tech-supervise-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-supervise-ui"},{"backend":{"serviceName":"pms-distribution-repair-ui","servicePort":80},"path":"/pms-distribution-repair-ui"},{"backend":{"serviceName":"pms-distribution-repair-ui","servicePort":80},"path":"/app-pms-distribution-repair-ui"},{"backend":{"serviceName":"pms-disribution-livework-ui","servicePort":80},"path":"/pms-disribution-livework-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-disribution-livework-ui"},{"backend":{"serviceName":"pms-ts-overhaul-ui","servicePort":80},"path":"/pms-ts-overhaul-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ts-overhaul-ui"},{"backend":{"serviceName":"pms-crem-lean-management-ui","servicePort":80},"path":"/pms-crem-lean-management-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-crem-lean-management-ui"},{"backend":{"serviceName":"pms-framework-demand-ghq-service","servicePort":80},"path":"/pms-framework-demand-ghq-service"},{"backend":{"serviceName":"pms-framework-demand-ghq-ui","servicePort":80},"path":"/pms-framework-demand-ghq-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-demand-ghq-ui"},{"backend":{"serviceName":"pms-serviceadapter-start","servicePort":80},"path":"/pms-serviceadapter-start"},{"backend":{"serviceName":"pms-framework-operation-ui","servicePort":80},"path":"/pms-framework-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-operation-ui"},{"backend":{"serviceName":"jobmanage-center","servicePort":80},"path":"/jobmanage-center"},{"backend":{"serviceName":"pms-framework-auth-ui","servicePort":80},"path":"/pms-framework-auth-ui"},{"backend":{"serviceName":"pms-framework-cts-gateway","servicePort":80},"path":"/pms-framework-cts-gateway"},{"backend":{"serviceName":"pms-serviceadapter-ui","servicePort":80},"path":"/pms-serviceadapter-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-serviceadapter-ui"},{"backend":{"serviceName":"psr-manage-ui","servicePort":80},"path":"/psr-manage-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-psr-manage-ui"},{"backend":{"serviceName":"pms-tf-overhaul-ui","servicePort":80},"path":"/pms-tf-overhaul-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tf-overhaul-ui"},{"backend":{"serviceName":"pms-tf-twovotes-ui","servicePort":80},"path":"/pms-tf-twovotes-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tf-twovotes-ui"},{"backend":{"serviceName":"pms-dc-twovotes-ui","servicePort":80},"path":"/pms-dc-twovotes-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-dc-twovotes-ui"},{"backend":{"serviceName":"pms-tr-twovotes-ui","servicePort":80},"path":"/pms-tr-twovotes-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tr-twovotes-ui"},{"backend":{"serviceName":"pms-ds-twovotes-ui","servicePort":80},"path":"/pms-ds-twovotes-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-twovotes-ui"},{"backend":{"serviceName":"psr-manage-ui","servicePort":80},"path":"/psr-manage-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-psr-manage-ui"},{"backend":{"serviceName":"pms-tech-supplyvoltage-ui","servicePort":80},"path":"/pms-tech-supplyvoltage-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-supplyvoltage-ui"},{"backend":{"serviceName":"pms-design-ui","servicePort":80},"path":"/pms-design-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-design-ui"},{"backend":{"serviceName":"pms-tf-operation-ui","servicePort":80},"path":"/pms-tf-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tf-operation-ui"},{"backend":{"serviceName":"pms-plan-cost-analysis-ui","servicePort":80},"path":"/pms-plan-cost-analysis-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-cost-analysis-ui"},{"backend":{"serviceName":"platform-manage-web","servicePort":80},"path":"/platform-manage-web"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-platform-manage-web"},{"backend":{"serviceName":"pms-framework-interface-ui","servicePort":80},"path":"/pms-framework-interface-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-interface-ui"},{"backend":{"serviceName":"pms-all-twovotes-ui","servicePort":80},"path":"/pms-all-twovotes-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-all-twovotes-ui"},{"backend":{"serviceName":"pms-ds-livework-ui","servicePort":80},"path":"/pms-ds-livework-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-livework-ui"},{"backend":{"serviceName":"pms-ds-rushrepair-ui","servicePort":80},"path":"/pms-ds-rushrepair-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-rushrepair-ui"},{"backend":{"serviceName":"pms-ds-operation-ui","servicePort":80},"path":"/pms-ds-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-operation-ui"},{"backend":{"serviceName":"pms-active-forewaring-ui","servicePort":80},"path":"/pms-active-forewaring-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-active-forewaring-ui"},{"backend":{"serviceName":"pms-framework-interface-service","servicePort":80},"path":"/pms-framework-interface-service"},{"backend":{"serviceName":"platform-operate-model","servicePort":80},"path":"/platform-operate-model"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-platform-operate-model"},{"backend":{"serviceName":"pms-tr-unoutage-ui","servicePort":80},"path":"/pms-tr-unoutage-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tr-unoutage-ui"},{"backend":{"serviceName":"pms-plan-overhaulproject-nationwide-ui","servicePort":80},"path":"/pms-plan-overhaulproject-nationwide-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-overhaulproject-nationwide-ui"},{"backend":{"serviceName":"platform-operate-model-base","servicePort":80},"path":"/platform-operate-model-base"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-platform-operate-model-base"},{"backend":{"serviceName":"pms-macro-panoramic-monitor-pro-ui","servicePort":80},"path":"/pms-macro-panoramic-monitor-pro-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-macro-panoramic-monitor-pro-ui"},{"backend":{"serviceName":"pms-panoramic-monitor-city-pro-ui","servicePort":80},"path":"/pms-panoramic-monitor-city-pro-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-panoramic-monitor-city-pro-ui"},{"backend":{"serviceName":"pms-tech-power-quality-ui","servicePort":80},"path":"/pms-tech-power-quality-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-power-quality-ui"},{"backend":{"serviceName":"pms-dc-overhaul-ui","servicePort":80},"path":"/pms-dc-overhaul-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-dc-overhaul-ui"},{"backend":{"serviceName":"pms-plan-opemainproject-nationwide-ui","servicePort":80},"path":"/pms-plan-opemainproject-nationwide-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-opemainproject-nationwide-ui"},{"backend":{"serviceName":"pms-plan-techtransproject-nationwide-ui","servicePort":80},"path":"/pms-plan-techtransproject-nationwide-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-techtransproject-nationwide-ui"},{"backend":{"serviceName":"pms-framework-problem-report-ui","servicePort":80},"path":"/pms-framework-problem-report-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-problem-report-ui"},{"backend":{"serviceName":"pms-im-productioncontrol-nx-ui","servicePort":80},"path":"/pms-im-productioncontrol-nx-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-nx-ui"},{"backend":{"serviceName":"pms-project-center-ui","servicePort":80},"path":"/pms-project-center-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-project-center-ui"},{"backend":{"serviceName":"pms-dc-operation-ui","servicePort":80},"path":"/pms-dc-operation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-dc-operation-ui"},{"backend":{"serviceName":"pms-workstatistics-wmcenter","servicePort":80},"path":"/pms-workstatistics-wmcenter"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-workstatistics-wmcenter"},{"backend":{"serviceName":"pms-plan-costanalysisstd-ui","servicePort":80},"path":"/pms-plan-costanalysisstd-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-costanalysisstd-ui"},{"backend":{"serviceName":"pms-plan-productioncostsstd-ui","servicePort":80},"path":"/pms-plan-productioncostsstd-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-productioncostsstd-ui"},{"backend":{"serviceName":"pms-distribution-reliablityevaluation-ui","servicePort":80},"path":"/pms-distribution-reliablityevaluation-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-distribution-reliablityevaluation-ui"},{"backend":{"serviceName":"pms-tech-preventfloodpc-sd-ui","servicePort":80},"path":"/pms-tech-preventfloodpc-sd-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-preventfloodpc-sd-ui"},{"backend":{"serviceName":"pms-tech-supplyvoltage-blue-ui","servicePort":80},"path":"/pms-tech-supplyvoltage-blue-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-supplyvoltage-blue-ui"},{"backend":{"serviceName":"pms-tech-lossreduction-standard-ui","servicePort":80},"path":"/pms-tech-lossreduction-standard-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-lossreduction-standard-ui"},{"backend":{"serviceName":"omsservices","servicePort":8088},"path":"/omsservices"},{"backend":{"serviceName":"pms-vtms-ws-ui","servicePort":80},"path":"/pms-vtms-ws-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-vtms-ws-ui"},{"backend":{"serviceName":"pms-distribution-project-management-nationwide-ui","servicePort":80},"path":"/pms-distribution-project-management-nationwide-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-distribution-project-management-nationwide-ui"},{"backend":{"serviceName":"finebi","servicePort":80},"path":"/webroot"},{"backend":{"serviceName":"pms-tech-supplier-eval-ui","servicePort":80},"path":"/pms-tech-supplier-eval-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-supplier-eval-ui"},{"backend":{"serviceName":"pms-amap-service","servicePort":20010},"path":"/amapsdk"},{"backend":{"serviceName":"pms-maps-service","servicePort":80},"path":"/authentication"},{"backend":{"serviceName":"pms-amap-service","servicePort":20010},"path":"/configs"},{"backend":{"serviceName":"pms-amap-service","servicePort":20010},"path":"/amap-gateway-service"},{"backend":{"serviceName":"pms-im-productioncontrol-ui","servicePort":80},"path":"/pms-im-productioncontrol-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-ui"},{"backend":{"serviceName":"pms-maps-service","servicePort":80},"path":"/maps"},{"backend":{"serviceName":"pms-ds-powerprotection-ui","servicePort":80},"path":"/pms-ds-powerprotection-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-powerprotection-ui"},{"backend":{"serviceName":"pms-powerguarantee-ui","servicePort":80},"path":"/pms-powerguarantee-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-powerguarantee-ui"},{"backend":{"serviceName":"pms-framework-oa-ui","servicePort":80},"path":"/pms-framework-oa-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-oa-ui"},{"backend":{"serviceName":"pms-equipment-parameters-test-ui","servicePort":80},"path":"/pms-equipment-parameters-test-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-equipment-parameters-test-ui"},{"backend":{"serviceName":"pms-framework-cdn-ui","servicePort":80},"path":"/pms-framework-cdn-ui","pathType":"ImplementationSpecific"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/pms-plan-asset-bw-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-asset-bw-ui"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/yj-pms-utcnumservice"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/pms-plan-assetmanage-accountcardmat-start"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/pms-plan-assetmanage-entityid-start"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/yj-pms-job-center"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/yj-pms-appdataentryservice"},{"backend":{"serviceName":"platform-manage-web-ui","servicePort":80},"path":"/platform-manage-web-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-platform-manage-web-ui"},{"backend":{"serviceName":"pms-ds-uav-ui","servicePort":80},"path":"/pms-ds-uav-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-uav-ui"},{"backend":{"serviceName":"ai-service-cpu-img","servicePort":80},"path":"/targetDetection"},{"backend":{"serviceName":"pms25sync","servicePort":80},"path":"/pms25sync"},{"backend":{"serviceName":"pms-all-insure-ui","servicePort":80},"path":"/pms-all-insure-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-all-insure-ui"},{"backend":{"serviceName":"pms-framework-audit-ui","servicePort":80},"path":"/pms-framework-audit-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-audit-ui"},{"backend":{"serviceName":"rmc-ds-statistics-ui","servicePort":80},"path":"/rmc-ds-statistics-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-rmc-ds-statistics-ui"},{"backend":{"serviceName":"pms-plan-lcam-ui","servicePort":20015},"path":"/pms-plan-lcam-ui"},{"backend":{"serviceName":"pms-plan-astlifecycle-manage","servicePort":20016},"path":"/pms-plan-astlifecycle-manage"},{"backend":{"serviceName":"pms-tongyuan-service","servicePort":30998},"path":"/yj-pms-devicemanage"},{"backend":{"serviceName":"pms-tongyuan-service","servicePort":30998},"path":"/yj-pms-portal-ui"},{"backend":{"serviceName":"pms-tongyuan-service","servicePort":30998},"path":"/yj-pms-devicemanage-ui"},{"backend":{"serviceName":"pms-tongyuan-service","servicePort":30998},"path":"/yj-pms-portal"},{"backend":{"serviceName":"pms-tongyuan-service","servicePort":30998},"path":"/yj-pms-dev-maint-mgt-nationwide-ui"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/pms-plan-assetmanage-wmcenter"},{"backend":{"serviceName":"plan-pa","servicePort":30998},"path":"/pms-plan-jobresource-wmcenter"},{"backend":{"serviceName":"pms-im-productioncontrol-jb-ui","servicePort":80},"path":"/pms-im-productioncontrol-jb-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-jb-ui"},{"backend":{"serviceName":"rmc-centerportal-ui","servicePort":80},"path":"/rmc-centerportal-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-rmc-centerportal-ui"},{"backend":{"serviceName":"pms-ds-fauils-ui","servicePort":80},"path":"/pms-ds-fauils-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-fauils-ui"},{"backend":{"serviceName":"pms-im-productioncontrol-standard-ui","servicePort":80},"path":"/pms-im-productioncontrol-standard-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-standard-ui"},{"backend":{"serviceName":"pms-project-center-portal-ui","servicePort":80},"path":"/pms-project-center-portal-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-project-center-portal-ui"},{"backend":{"serviceName":"pms-link-trace-ui","servicePort":80},"path":"/pms-link-trace-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-link-trace-ui"},{"backend":{"serviceName":"pms-im-productioncontrol-sx-ui","servicePort":80},"path":"/pms-im-productioncontrol-sx-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-sx-ui"},{"backend":{"serviceName":"pms-tsf-reliability-ui","servicePort":80},"path":"/pms-tsf-reliability-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tsf-reliability-ui"},{"backend":{"serviceName":"pms-production-command-center-ui","servicePort":80},"path":"/pms-production-command-center-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-production-command-center-ui"},{"backend":{"serviceName":"pms-cable-monitor-ui","servicePort":80},"path":"/pms-cable-monitor-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-cable-monitor-ui"},{"backend":{"serviceName":"pssc-ui","servicePort":80},"path":"/pssc-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pssc-ui"},{"backend":{"serviceName":"pms-pssc-service","servicePort":31465},"path":"/pssc-gfrh"},{"backend":{"serviceName":"pms-bd-jkxxgl","servicePort":80},"path":"/pms-bd-jkxxgl"},{"backend":{"serviceName":"pms-plan-trospecial-ui","servicePort":80},"path":"/pms-plan-trospecial-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-trospecial-ui"},{"backend":{"serviceName":"pms-ds-evaluate-ui","servicePort":80},"path":"/pms-ds-evaluate-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ds-evaluate-ui"},{"backend":{"serviceName":"pms-plan-jobresource-ui","servicePort":80},"path":"/pms-plan-jobresource-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-jobresource-ui"},{"backend":{"serviceName":"pms-plan-assetmanage-ui","servicePort":80},"path":"/pms-plan-assetmanage-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-assetmanage-ui"},{"backend":{"serviceName":"pms-livework-nx-ui","servicePort":80},"path":"/pms-livework-nx-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-livework-nx-ui"},{"backend":{"serviceName":"pms-livework-nx-ui","servicePort":80},"path":"/pms-livework-nx-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-livework-nx-ui"},{"backend":{"serviceName":"pms-tf-analytical-ui","servicePort":80},"path":"/pms-tf-analytical-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tf-analytical-ui"},{"backend":{"serviceName":"pms-plan-helper","servicePort":80},"path":"/pms-plan-helper"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-plan-helper"},{"backend":{"serviceName":"rmc-centerportal-map-ui","servicePort":80},"path":"/rmc-centerportal-map-ui"},{"backend":{"serviceName":"rmc-centerportal-map-ui","servicePort":80},"path":"/app-rmc-centerportal-map-ui"},{"backend":{"serviceName":"pms-dc-reliability-ui","servicePort":80},"path":"/pms-dc-reliability-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-dc-reliability-ui"},{"backend":{"serviceName":"pms-ts-uav-ui","servicePort":80},"path":"/pms-ts-uav-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-ts-uav-ui"},{"backend":{"serviceName":"pms-interreport-pro-ui","servicePort":80},"path":"/pms-interreport-pro-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-interreport-pro-ui"},{"backend":{"serviceName":"pms-framework-permission-ui","servicePort":80},"path":"/pms-framework-permission-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-framework-permission-ui"},{"backend":{"serviceName":"pms-framework-permission-api","servicePort":80},"path":"/pms-framework-permission-api"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tech-supervise-evaluate-ui"},{"backend":{"serviceName":"pms-tech-supervise-evaluate-ui","servicePort":80},"path":"/pms-tech-supervise-evaluate-ui"},{"backend":{"serviceName":"pms-bd-jkxxgl-test","servicePort":80},"path":"/pms-bd-jkxxgl-test"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-fzjk-ui"},{"backend":{"serviceName":"pms-fzjk-ui","servicePort":80},"path":"/pms-fzjk-ui"},{"backend":{"serviceName":"pms-tech-supplyvoltage-vmcenter-start","servicePort":80},"path":"/pms-tech-supplyvoltage-vmcenter-start"},{"backend":{"serviceName":"pms-framework-permission-api","servicePort":80},"path":"/pms-framework-permission-api","pathType":"ImplementationSpecific"},{"backend":{"serviceName":"pms-im-productioncontrol-bigscreenhlj-ui","servicePort":80},"path":"/pms-im-productioncontrol-bigscreenhlj-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-bigscreenhlj-ui"},{"backend":{"serviceName":"pms-tf-intelligent-decision-ui","servicePort":80},"path":"/pms-tf-intelligent-decision-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-tf-intelligent-decision-ui"},{"backend":{"serviceName":"pms-transformation-acceptance-ui","servicePort":80},"path":"/pms-transformation-acceptance-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-transformation-acceptance-ui"},{"backend":{"serviceName":"pms-im-productioncontrol-bigscreenln-ui","servicePort":80},"path":"/pms-im-productioncontrol-bigscreenln-ui"},{"backend":{"serviceName":"pms-framework-portal-ui","servicePort":80},"path":"/app-pms-im-productioncontrol-bigscreenln-ui"}]}}]}}
    nginx.ingress.kubernetes.io/proxy-body-size: 100m
    nginx.ingress.kubernetes.io/proxy-read-timeout: '1800'
    nginx.ingress.kubernetes.io/proxy-send-timeout: '1800'
    nginx.ingress.kubernetes.io/use-regex: 'true'
  managedFields:
    - manager: kubectl
      operation: Update
      apiVersion: extensions/v1beta1
      time: '2023-08-03T10:45:27Z'
      fieldsType: FieldsV1
      fieldsV1:
        'f:metadata':
          'f:annotations':
            .: {}
            'f:kubectl.kubernetes.io/last-applied-configuration': {}
            'f:nginx.ingress.kubernetes.io/proxy-body-size': {}
            'f:nginx.ingress.kubernetes.io/proxy-read-timeout': {}
            'f:nginx.ingress.kubernetes.io/proxy-send-timeout': {}
            'f:nginx.ingress.kubernetes.io/use-regex': {}
          'f:labels':
            .: {}
            'f:app': {}
        'f:spec':
          'f:rules': {}
spec:
  rules:
    - host: pms.pms30standard.dev.com.cn
      http:
        paths:
          - path: /pms-framework-portal-ui.*Copy
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-gateway
              servicePort: 80
          - path: /pms-framework-portal-ui/.*/pms-framework-portal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-gateway
              servicePort: 80
          - path: /
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-gateway
              servicePort: 80
          - path: /pms-framework-portal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-web-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-web-ui
              servicePort: 80
          - path: /app-pms-framework-web-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-public-twovotes-ui-tf
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-public-twovotes-ui-tf
              servicePort: 80
          - path: /app-pms-public-twovotes-ui-tf
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /lowcodeplat
            pathType: ImplementationSpecific
            backend:
              serviceName: hussar-front
              servicePort: 80
          - path: /pms-transformation-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-transformation-overhaul-ui
              servicePort: 80
          - path: /app-pms-transformation-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-directcurrent-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-directcurrent-operation-ui
              servicePort: 80
          - path: /app-pms-directcurrent-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-transformation-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-transformation-operation-ui
              servicePort: 80
          - path: /app-pms-transformation-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-demand-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-demand-ui
              servicePort: 80
          - path: /app-pms-framework-demand-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-tiles-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-tiles-ui
              servicePort: 80
          - path: /app-pms-framework-tiles-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ts-ohl-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ts-ohl-ui
              servicePort: 80
          - path: /app-pms-ts-ohl-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-directcurrent-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-directcurrent-overhaul-ui
              servicePort: 80
          - path: /app-pms-directcurrent-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-distribution-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-distribution-operation-ui
              servicePort: 80
          - path: /app-pms-distribution-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-public-twovotes-ui-dc
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-public-twovotes-ui-dc
              servicePort: 80
          - path: /app-pms-public-twovotes-ui-dc
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-public-twovotes-ui-ds
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-public-twovotes-ui-ds
              servicePort: 80
          - path: /app-pms-public-twovotes-ui-ds
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-public-twovotes-ui-tscable
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-public-twovotes-ui-tscable
              servicePort: 80
          - path: /app-pms-public-twovotes-ui-tscable
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-public-twovotes-ui-tsohl
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-public-twovotes-ui-tsohl
              servicePort: 80
          - path: /app-pms-public-twovotes-ui-tsohl
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-transmission-cableapplication-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-transmission-cableapplication-ui
              servicePort: 80
          - path: /app-pms-transmission-cableapplication-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-techtransproject-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-techtransproject-ui
              servicePort: 80
          - path: /app-pms-plan-techtransproject-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-overhaulproject-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-overhaulproject-ui
              servicePort: 80
          - path: /app-pms-plan-overhaulproject-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-opemainproject-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-opemainproject-ui
              servicePort: 80
          - path: /app-pms-plan-opemainproject-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-productioncosts-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-productioncosts-ui
              servicePort: 80
          - path: /app-pms-plan-productioncosts-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-sclmt-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-sclmt-ui
              servicePort: 80
          - path: /app-pms-framework-sclmt-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-hqsl-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-hqsl-ui
              servicePort: 80
          - path: /app-pms-framework-hqsl-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-hqocp-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-hqocp-ui
              servicePort: 80
          - path: /app-pms-framework-hqocp-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-config-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-config-ui
              servicePort: 80
          - path: /app-pms-framework-config-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-supervise-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-supervise-ui
              servicePort: 80
          - path: /app-pms-tech-supervise-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-distribution-repair-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-distribution-repair-ui
              servicePort: 80
          - path: /app-pms-distribution-repair-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-distribution-repair-ui
              servicePort: 80
          - path: /pms-disribution-livework-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-disribution-livework-ui
              servicePort: 80
          - path: /app-pms-disribution-livework-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ts-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ts-overhaul-ui
              servicePort: 80
          - path: /app-pms-ts-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-crem-lean-management-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-crem-lean-management-ui
              servicePort: 80
          - path: /app-pms-crem-lean-management-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-demand-ghq-service
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-demand-ghq-service
              servicePort: 80
          - path: /pms-framework-demand-ghq-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-demand-ghq-ui
              servicePort: 80
          - path: /app-pms-framework-demand-ghq-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-serviceadapter-start
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-serviceadapter-start
              servicePort: 80
          - path: /pms-framework-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-operation-ui
              servicePort: 80
          - path: /app-pms-framework-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /jobmanage-center
            pathType: ImplementationSpecific
            backend:
              serviceName: jobmanage-center
              servicePort: 80
          - path: /pms-framework-auth-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-auth-ui
              servicePort: 80
          - path: /pms-framework-cts-gateway
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-cts-gateway
              servicePort: 80
          - path: /pms-serviceadapter-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-serviceadapter-ui
              servicePort: 80
          - path: /app-pms-serviceadapter-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /psr-manage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: psr-manage-ui
              servicePort: 80
          - path: /app-psr-manage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tf-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tf-overhaul-ui
              servicePort: 80
          - path: /app-pms-tf-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tf-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tf-twovotes-ui
              servicePort: 80
          - path: /app-pms-tf-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-dc-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-dc-twovotes-ui
              servicePort: 80
          - path: /app-pms-dc-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tr-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tr-twovotes-ui
              servicePort: 80
          - path: /app-pms-tr-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-twovotes-ui
              servicePort: 80
          - path: /app-pms-ds-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /psr-manage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: psr-manage-ui
              servicePort: 80
          - path: /app-psr-manage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-supplyvoltage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-supplyvoltage-ui
              servicePort: 80
          - path: /app-pms-tech-supplyvoltage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-design-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-design-ui
              servicePort: 80
          - path: /app-pms-design-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tf-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tf-operation-ui
              servicePort: 80
          - path: /app-pms-tf-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-cost-analysis-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-cost-analysis-ui
              servicePort: 80
          - path: /app-pms-plan-cost-analysis-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /platform-manage-web
            pathType: ImplementationSpecific
            backend:
              serviceName: platform-manage-web
              servicePort: 80
          - path: /app-platform-manage-web
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-interface-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-interface-ui
              servicePort: 80
          - path: /app-pms-framework-interface-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-all-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-all-twovotes-ui
              servicePort: 80
          - path: /app-pms-all-twovotes-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-livework-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-livework-ui
              servicePort: 80
          - path: /app-pms-ds-livework-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-rushrepair-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-rushrepair-ui
              servicePort: 80
          - path: /app-pms-ds-rushrepair-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-operation-ui
              servicePort: 80
          - path: /app-pms-ds-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-active-forewaring-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-active-forewaring-ui
              servicePort: 80
          - path: /app-pms-active-forewaring-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-interface-service
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-interface-service
              servicePort: 80
          - path: /platform-operate-model
            pathType: ImplementationSpecific
            backend:
              serviceName: platform-operate-model
              servicePort: 80
          - path: /app-platform-operate-model
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tr-unoutage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tr-unoutage-ui
              servicePort: 80
          - path: /app-pms-tr-unoutage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-overhaulproject-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-overhaulproject-nationwide-ui
              servicePort: 80
          - path: /app-pms-plan-overhaulproject-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /platform-operate-model-base
            pathType: ImplementationSpecific
            backend:
              serviceName: platform-operate-model-base
              servicePort: 80
          - path: /app-platform-operate-model-base
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-macro-panoramic-monitor-pro-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-macro-panoramic-monitor-pro-ui
              servicePort: 80
          - path: /app-pms-macro-panoramic-monitor-pro-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-panoramic-monitor-city-pro-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-panoramic-monitor-city-pro-ui
              servicePort: 80
          - path: /app-pms-panoramic-monitor-city-pro-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-power-quality-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-power-quality-ui
              servicePort: 80
          - path: /app-pms-tech-power-quality-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-dc-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-dc-overhaul-ui
              servicePort: 80
          - path: /app-pms-dc-overhaul-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-opemainproject-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-opemainproject-nationwide-ui
              servicePort: 80
          - path: /app-pms-plan-opemainproject-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-techtransproject-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-techtransproject-nationwide-ui
              servicePort: 80
          - path: /app-pms-plan-techtransproject-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-problem-report-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-problem-report-ui
              servicePort: 80
          - path: /app-pms-framework-problem-report-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-im-productioncontrol-nx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-nx-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-nx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-project-center-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-project-center-ui
              servicePort: 80
          - path: /app-pms-project-center-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-dc-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-dc-operation-ui
              servicePort: 80
          - path: /app-pms-dc-operation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-workstatistics-wmcenter
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-workstatistics-wmcenter
              servicePort: 80
          - path: /app-pms-workstatistics-wmcenter
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-costanalysisstd-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-costanalysisstd-ui
              servicePort: 80
          - path: /app-pms-plan-costanalysisstd-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-productioncostsstd-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-productioncostsstd-ui
              servicePort: 80
          - path: /app-pms-plan-productioncostsstd-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-distribution-reliablityevaluation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-distribution-reliablityevaluation-ui
              servicePort: 80
          - path: /app-pms-distribution-reliablityevaluation-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-preventfloodpc-sd-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-preventfloodpc-sd-ui
              servicePort: 80
          - path: /app-pms-tech-preventfloodpc-sd-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-supplyvoltage-blue-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-supplyvoltage-blue-ui
              servicePort: 80
          - path: /app-pms-tech-supplyvoltage-blue-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-lossreduction-standard-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-lossreduction-standard-ui
              servicePort: 80
          - path: /app-pms-tech-lossreduction-standard-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /omsservices
            pathType: ImplementationSpecific
            backend:
              serviceName: omsservices
              servicePort: 8088
          - path: /pms-vtms-ws-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-vtms-ws-ui
              servicePort: 80
          - path: /app-pms-vtms-ws-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-distribution-project-management-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-distribution-project-management-nationwide-ui
              servicePort: 80
          - path: /app-pms-distribution-project-management-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /webroot
            pathType: ImplementationSpecific
            backend:
              serviceName: finebi
              servicePort: 80
          - path: /pms-tech-supplier-eval-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-supplier-eval-ui
              servicePort: 80
          - path: /app-pms-tech-supplier-eval-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /amapsdk
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-amap-service
              servicePort: 20010
          - path: /authentication
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-maps-service
              servicePort: 80
          - path: /configs
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-amap-service
              servicePort: 20010
          - path: /amap-gateway-service
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-amap-service
              servicePort: 20010
          - path: /pms-im-productioncontrol-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /maps
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-maps-service
              servicePort: 80
          - path: /pms-ds-powerprotection-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-powerprotection-ui
              servicePort: 80
          - path: /app-pms-ds-powerprotection-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-powerguarantee-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-powerguarantee-ui
              servicePort: 80
          - path: /app-pms-powerguarantee-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-oa-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-oa-ui
              servicePort: 80
          - path: /app-pms-framework-oa-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-equipment-parameters-test-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-equipment-parameters-test-ui
              servicePort: 80
          - path: /app-pms-equipment-parameters-test-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-cdn-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-cdn-ui
              servicePort: 80
          - path: /pms-plan-asset-bw-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /app-pms-plan-asset-bw-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /yj-pms-utcnumservice
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /pms-plan-assetmanage-accountcardmat-start
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /pms-plan-assetmanage-entityid-start
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /yj-pms-job-center
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /yj-pms-appdataentryservice
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /platform-manage-web-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: platform-manage-web-ui
              servicePort: 80
          - path: /app-platform-manage-web-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-uav-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-uav-ui
              servicePort: 80
          - path: /app-pms-ds-uav-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /targetDetection
            pathType: ImplementationSpecific
            backend:
              serviceName: ai-service-cpu-img
              servicePort: 80
          - path: /pms25sync
            pathType: ImplementationSpecific
            backend:
              serviceName: pms25sync
              servicePort: 80
          - path: /pms-all-insure-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-all-insure-ui
              servicePort: 80
          - path: /app-pms-all-insure-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-audit-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-audit-ui
              servicePort: 80
          - path: /app-pms-framework-audit-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /rmc-ds-statistics-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: rmc-ds-statistics-ui
              servicePort: 80
          - path: /app-rmc-ds-statistics-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-lcam-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-lcam-ui
              servicePort: 20015
          - path: /pms-plan-astlifecycle-manage
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-astlifecycle-manage
              servicePort: 20016
          - path: /yj-pms-devicemanage
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tongyuan-service
              servicePort: 30998
          - path: /yj-pms-portal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tongyuan-service
              servicePort: 30998
          - path: /yj-pms-devicemanage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tongyuan-service
              servicePort: 30998
          - path: /yj-pms-portal
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tongyuan-service
              servicePort: 30998
          - path: /yj-pms-dev-maint-mgt-nationwide-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tongyuan-service
              servicePort: 30998
          - path: /pms-plan-assetmanage-wmcenter
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /pms-plan-jobresource-wmcenter
            pathType: ImplementationSpecific
            backend:
              serviceName: plan-pa
              servicePort: 30998
          - path: /pms-im-productioncontrol-jb-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-jb-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-jb-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /rmc-centerportal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: rmc-centerportal-ui
              servicePort: 80
          - path: /app-rmc-centerportal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-fauils-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-fauils-ui
              servicePort: 80
          - path: /app-pms-ds-fauils-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-im-productioncontrol-standard-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-standard-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-standard-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-project-center-portal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-project-center-portal-ui
              servicePort: 80
          - path: /app-pms-project-center-portal-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-link-trace-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-link-trace-ui
              servicePort: 80
          - path: /app-pms-link-trace-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-im-productioncontrol-sx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-sx-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-sx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tsf-reliability-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tsf-reliability-ui
              servicePort: 80
          - path: /app-pms-tsf-reliability-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-production-command-center-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-production-command-center-ui
              servicePort: 80
          - path: /app-pms-production-command-center-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-cable-monitor-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-cable-monitor-ui
              servicePort: 80
          - path: /app-pms-cable-monitor-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pssc-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pssc-ui
              servicePort: 80
          - path: /app-pssc-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pssc-gfrh
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-pssc-service
              servicePort: 31465
          - path: /pms-bd-jkxxgl
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-bd-jkxxgl
              servicePort: 80
          - path: /pms-plan-trospecial-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-trospecial-ui
              servicePort: 80
          - path: /app-pms-plan-trospecial-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ds-evaluate-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ds-evaluate-ui
              servicePort: 80
          - path: /app-pms-ds-evaluate-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-jobresource-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-jobresource-ui
              servicePort: 80
          - path: /app-pms-plan-jobresource-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-assetmanage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-assetmanage-ui
              servicePort: 80
          - path: /app-pms-plan-assetmanage-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-livework-nx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-livework-nx-ui
              servicePort: 80
          - path: /app-pms-livework-nx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-livework-nx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-livework-nx-ui
              servicePort: 80
          - path: /app-pms-livework-nx-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tf-analytical-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tf-analytical-ui
              servicePort: 80
          - path: /app-pms-tf-analytical-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-plan-helper
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-plan-helper
              servicePort: 80
          - path: /app-pms-plan-helper
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /rmc-centerportal-map-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: rmc-centerportal-map-ui
              servicePort: 80
          - path: /app-rmc-centerportal-map-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: rmc-centerportal-map-ui
              servicePort: 80
          - path: /pms-dc-reliability-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-dc-reliability-ui
              servicePort: 80
          - path: /app-pms-dc-reliability-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-ts-uav-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-ts-uav-ui
              servicePort: 80
          - path: /app-pms-ts-uav-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-interreport-pro-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-interreport-pro-ui
              servicePort: 80
          - path: /app-pms-interreport-pro-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-permission-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-permission-ui
              servicePort: 80
          - path: /app-pms-framework-permission-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-framework-permission-api
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-permission-api
              servicePort: 80
          - path: /app-pms-tech-supervise-evaluate-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tech-supervise-evaluate-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-supervise-evaluate-ui
              servicePort: 80
          - path: /pms-bd-jkxxgl-test
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-bd-jkxxgl-test
              servicePort: 80
          - path: /app-pms-fzjk-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-fzjk-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-fzjk-ui
              servicePort: 80
          - path: /pms-tech-supplyvoltage-vmcenter-start
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tech-supplyvoltage-vmcenter-start
              servicePort: 80
          - path: /pms-framework-permission-api
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-permission-api
              servicePort: 80
          - path: /pms-im-productioncontrol-bigscreenhlj-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-bigscreenhlj-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-bigscreenhlj-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-tf-intelligent-decision-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-tf-intelligent-decision-ui
              servicePort: 80
          - path: /app-pms-tf-intelligent-decision-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-transformation-acceptance-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-transformation-acceptance-ui
              servicePort: 80
          - path: /app-pms-transformation-acceptance-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
          - path: /pms-im-productioncontrol-bigscreenln-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-im-productioncontrol-bigscreenln-ui
              servicePort: 80
          - path: /app-pms-im-productioncontrol-bigscreenln-ui
            pathType: ImplementationSpecific
            backend:
              serviceName: pms-framework-portal-ui
              servicePort: 80
status:
  loadBalancer: {}
```

