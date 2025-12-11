# Despliegue de Microservicios en AWS EKS

Proyecto completo para desplegar una aplicación de facturación en Amazon EKS usando contenedores Docker y Kubernetes.

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

## Desarrollo Local

```bash
# Construir aplicación
cd billing_include_tests
./mvnw package -Pprod

# Ejecutar con Docker Compose
cd ../cicd_aws_eks
docker-compose -f docker-compose-app.yaml up -d
```

## Variables de Entorno

| Variable | Valor | Descripción |
|----------|-------|-------------|
| PORT | 7080 | Puerto aplicación |
| DB_HOST | postgres16-service:5432 | Host base de datos |
| DB_DATABASE | billingapp_db | Nombre BD |
| DB_USERNAME | billingapp | Usuario BD |
| DB_PASSWORD | qwerty | Contraseña BD |

## Acceso a Servicios

- **API Billing**: `http://<LoadBalancer-IP>:7080`
- **Swagger UI**: `http://<LoadBalancer-IP>:7080/swagger-ui/index.html`
- **pgAdmin**: `http://localhost:5050` (local)

## Comandos Útiles

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