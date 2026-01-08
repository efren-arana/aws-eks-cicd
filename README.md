# Despliegue de Microservicios en AWS EKS

Proyecto completo para desplegar una aplicación de facturación en Amazon EKS usando contenedores Docker y Kubernetes.

## Notas Importantes antes de realizar el despliegue
- Verificar que el AWS EFS se encuentre en la misma **Subnet** del nodo de la base de datos, si el cluster tiene subnets privadas y publicas. Verificar que la base de datos se encuentre en la misma subnet del File System
- Antes de Crear los **Pods** o culquier otro objeto en el cluster, asegurarse de haber creado el nodegroup, sino se hace el cluster automaticamente busca aprovicionar otros nodos para poder desplegar los pods.
- Asegurarse de ejecutar los comandos para crear el cluster EKS con la mismo usuario **IAM**, sino en la seccion de Acceso debes de agregar el usuario con el que estas interactuando en el cluster y agregarle los permisos necesarios.
- En cuentas nuevas, AWS ofrece creditos pero tiene ciertas limitaciones por lo tanto no se va a poder crear los balanceadores para los servicios.
- Para conectarnos a la base de datos el webservice como la base de datos tienen que estar en la misma subnet. ya sea publica o privada
- Para obtener el host del servicio de la base de datos se debe de ver los detalle del servicio y obtener el Endpoints para pasarlo como parametro ejecutanto el siguiente comando
```bash
kubectl describe service postgres16-service
```
-Agregue la siguiente linea en el servicio para que el balanceador se despliegue en la subnet publica y pueda acceder desde internet

```
service.beta.kubernetes.io/aws-load-balancer-scheme: internet-facing
```

## Arquitectura

- **Aplicación**: Spring Boot 3.2.3 (Java 17)
- **Base de Datos**: PostgreSQL 16
- **Orquestación**: Kubernetes en AWS EKS
- **Almacenamiento**: Amazon EFS
- **Balanceador**: AWS Load Balancer

## Estructura del Proyecto

```
AWS_Kubernetes/
├── billing_include_tests/           # Código fuente Spring Boot
│   ├── src/main/java/              # Lógica de negocio
│   ├── src/test/java/              # Tests unitarios
│   ├── Dockerfile                  # Imagen Docker
│   └── pom.xml                     # Dependencias Maven
└── cicd_aws_eks/                   # Recursos de despliegue
    ├── docker-compose-app.yaml     # Desarrollo local
    ├── dbfiles/init-user-db.sh     # Inicialización BD
    └── k8s_billingApp/             # Manifiestos Kubernetes
        ├── configmap*.yaml         # Variables de configuración
        ├── deployment*.yaml        # Despliegues
        ├── postgres-pv*.yaml       # Almacenamiento persistente
        ├── secret*.yaml            # Credenciales
        └── service*.yaml           # Servicios de red
```

## Componentes

### Aplicación Billing
- **Imagen**: `earanadocker/webservice-billing:latest`
- **Puerto**: 7080
- **Réplicas**: 2
- **API**: REST con Swagger UI

### PostgreSQL
- **Imagen**: `postgres:16-alpine`
- **Puerto**: 5432
- **Almacenamiento**: EFS (2Gi)
- **Inicialización**: Script automático

### pgAdmin4
- **Imagen**: `dpage/pgadmin4:latest`
- **Puerto**: 5050
- **Acceso**: admin@admin.com/admin

## Despliegue Local

```bash
# Construir aplicación
# Dentro del proyecto Spring ./billing_include_tests se encuentra el Dockerfile
cd billing_include_tests
#Construir el Jar del webservice en Linux
mvn clean install
#Opcional
#Pueden realizar cambios en el Webservice y volver a construir la imagen
#La imagen ya se encuentra cargada en el repository DockerHub lista para ser utilizada en Docker Compose como earanadocker/webservice-billing
docker build -t webservice-billing -f ./Dockerfile .

# Ejecutar con Docker Compose
cd ../cicd_aws_eks
docker-compose -f docker-compose-app.yaml up -d

# Eliminar la orquestacion
# Podemos utilizar la bandera -v para eliminar tambien los volumenes
docker-compose -f docker-compose-app.yaml down
```

## Variables de Entorno

| Variable | Valor | Descripción |
|----------|-------|-------------|
| PORT | 7080 | Puerto aplicación |
| DB_HOST | postgres16-service:5432 | Host base de datos |
| DB_DATABASE | billingapp_db | Nombre BD |
| DB_USERNAME | billingapp | Usuario BD |
| DB_PASSWORD | qwerty | Contraseña BD |

## Add-Ons Kubernetes

Debemos utilizar los siguientes complementos en el clúster

- CNI (Container Network Interface) Este complemento nos permite que los pods tengan la misma IP dentro del Pod como en la red VPC

- CSI (Container Storage Interface) Este driver permite que administren el ciclo de vida de los sistemas de archivos de Amazon EFS. 

## Configurar AWS EFS

## Despliegue en EKS

### 1. Crear almacenamiento
```bash
kubectl apply -f k8s_billingApp/postgres-pv.yaml
kubectl apply -f k8s_billingApp/postgres-pvc.yaml
```

### 2. Configurar secrets y variables
```bash
kubectl apply -f k8s_billingApp/secret-postgres.yaml
kubectl apply -f k8s_billingApp/secret-ws-app.yaml
kubectl apply -f k8s_billingApp/configmap.yaml
kubectl apply -f k8s_billingApp/configmap-postgres-initbd.yaml
```

### 3. Desplegar base de datos
```bash
kubectl apply -f k8s_billingApp/deployment-postgres-app.yaml
kubectl apply -f k8s_billingApp/service-postgres.yaml
```

### 4. Desplegar aplicación
```bash
kubectl apply -f k8s_billingApp/deployment-ws-billingapp.yaml
kubectl apply -f k8s_billingApp/service-ws-billingapp.yaml
```



## Acceso a Servicios

- **API Billing**: `http://<LoadBalancer-IP>:7080`
- **Swagger UI**: `http://<LoadBalancer-IP>:7080/swagger-ui/index.html`
- **pgAdmin**: `http://localhost:5050` (local)

## Comandos Útiles
```bash
# Comando necesario para la comunicacion
kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default

```bash
# Ver estado del cluster
kubectl get all

# Escalar aplicación
kubectl scale deployment ws-billingapp-deploy --replicas=3

# Ver logs
kubectl logs -l app=ws-billingapp -f

# Acceder a PostgreSQL
kubectl exec -it <postgres-pod> -- psql -U billingapp -d billingapp_db
```

## Características Técnicas

- **Persistencia**: Amazon EFS para datos PostgreSQL
- **Alta Disponibilidad**: 2 réplicas de la aplicación
- **Seguridad**: Secrets para credenciales sensibles
- **Monitoreo**: Logs centralizados en CloudWatch
- **Escalabilidad**: Auto-scaling habilitado