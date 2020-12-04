```
docker swarm init
# 初始集群

docker stack deploy -c .\docker-compose.yml demo2
# 启动脚本
 
docker stack ps demo2
# 查看启动过程

docker service logs -f -t --tail 30 demo2_nginx
# 查看某service启动日志
```sh

