# Deploy do Simplex com Containers e HTTPS

## Pré-requisitos

- Podman ou Docker instalado
- Nginx instalado no host
- Domínio configurado (ex: DuckDNS)
- Portas 80 e 443 liberadas no firewall

## 1. Clonar os repositórios

```bash
git clone git@github.com:SilvaMiqueias/simplex-backend.git
git clone git@github.com:SilvaMiqueias/simplex-frotend.git simplex-frontend
```

## 2. Iniciar os containers

```bash
# Copie o docker-compose.yml para o diretório pai dos repositórios
cp simplex-backend/docker-compose.yml .

# Inicie os containers
podman-compose up -d --build
# ou
docker compose up -d --build
```

## 3. Configurar Nginx

```bash
# Copie a configuração do nginx
sudo cp simplex-backend/nginx-ssl.conf /etc/nginx/conf.d/simplex.conf

# Edite o arquivo e substitua "seu-dominio.com" pelo seu domínio
sudo nano /etc/nginx/conf.d/simplex.conf

# Teste e recarregue o nginx
sudo nginx -t && sudo systemctl reload nginx
```

## 4. Gerar certificado SSL com Let's Encrypt

```bash
# Instale o certbot
sudo pip3 install certbot certbot-nginx

# Gere o certificado (substitua pelo seu domínio e email)
sudo certbot --nginx -d seu-dominio.com --non-interactive --agree-tos --email seu-email@exemplo.com
```

## 5. Configurar renovação automática

```bash
# Adicione ao cron para renovar automaticamente
echo "0 0,12 * * * root /usr/local/bin/certbot renew -q" | sudo tee /etc/cron.d/certbot-renew
```

## 6. Liberar portas no firewall

```bash
# Firewalld (CentOS/Oracle Linux)
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --reload

# Se usar Oracle Cloud, libere as portas 80 e 443 na Security List da VCN
```

## Portas utilizadas

| Serviço  | Porta Interna | Porta Externa |
|----------|---------------|---------------|
| Frontend | 80            | 3000          |
| Backend  | 8080          | 8080          |
| Postgres | 5432          | 5432          |
| Nginx    | 80, 443       | 80, 443       |

## Arquitetura

```
Internet → Nginx (80/443) → Frontend Container (3000)
                         → Backend Container (8080) → Postgres (5432)
```

## Variáveis de ambiente do Frontend

Crie um arquivo `.env` no diretório do frontend antes do build:

```bash
VITE_API_URL=https://seu-dominio.com
```

## Notas

- O Containerfile do backend usa `amazoncorretto:17-alpine` para compatibilidade com ARM64
- O certificado SSL expira em 90 dias e é renovado automaticamente
- Os containers reiniciam automaticamente após reboot (`restart: unless-stopped`)
