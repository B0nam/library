# Estrutura de Kubernetes para o Projeto Library

Este diretório contém os arquivos de configuração Kubernetes para o projeto Library, organizados da seguinte forma:

## Estrutura

```
library/
├── app/                   # Configurações relacionadas à aplicação
│   ├── configmap.yaml     # Configurações da aplicação
│   ├── deployment.yaml    # Deployment da aplicação
│   ├── ingress.yaml       # Regras de ingress para acesso externo
│   └── service.yaml       # Serviço da aplicação
├── postgres/              # Configurações relacionadas ao banco de dados PostgreSQL
│   ├── deployment.yaml    # Deployment do PostgreSQL
│   ├── persistentvolumeclaim.yaml  # Configuração de volume persistente
│   └── service.yaml       # Serviço do PostgreSQL
├── common/                # Configurações comuns
│   ├── cluster-issuer.yaml  # Configuração do emissor de certificados Let's Encrypt
│   └── secrets.yaml       # Secrets compartilhados
└── reset-deploy.sh        # Script para recriar o namespace e reaplicar todas as configurações
```

## Aplicação de Configurações

### Método Manual

Para aplicar todas as configurações manualmente, execute:

```shell
# Criar namespace (se ainda não existir)
kubectl create namespace library

# Aplicar secrets primeiro
kubectl apply -f common/secrets.yaml

# Aplicar o cluster-issuer
kubectl apply -f common/cluster-issuer.yaml

# Aplicar configurações do PostgreSQL
kubectl apply -f postgres/

# Aplicar configurações da aplicação
kubectl apply -f app/
```

> **Nota Importante:** Certifique-se de preencher os valores dos secrets no arquivo `common/secrets.yaml` antes de aplicar as configurações.
