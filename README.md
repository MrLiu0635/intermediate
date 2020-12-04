

```sh
docker build -t gdc.docker.iec.io/demo:liu.zhao-20201204 -f .\dockerfile .
# 通过dockerfile制作镜像

docker swarm init
# 初始集群

docker stack deploy -c .\docker-compose.yml demo2
# 启动脚本。在windows中nginx启动不起来，会报无效的bind mount
 
docker stack ps demo2
# 查看集群中的任务

docker stack services demo2
# 查看启动过程

docker service logs -f -t --tail 30 demo2_nginx
# 查看某service启动日志

```

