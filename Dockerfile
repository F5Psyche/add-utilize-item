# 基础镜像, 使用centos操作系统
FROM centos:7
# 发布者
MAINTAINER zhanghf <f5psyche@163.com>

# 在docker容器构建时创建文件夹
RUN mkdir /usr/local/java

# 调整docker容器内的时间
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

# 将宿主机的文件拷贝到容器中的具体目录
# ADD, 拷贝后自动解压; COPY, 只做拷贝
# 宿主机上的文件路径是Dockerfile的相对路径
# 如果按目前的配置, 则需要在Dockerfile文件所在路径下拥有jdk-8u301-linux-x64.tar.gz压缩包
ADD jdk-8u301-linux-x64.tar.gz /usr/local/java

# 在容器中创建/usr/local/java/jdk1.8.0_301与/usr/local/java/jdk的软连接
RUN ln -s /usr/local/java/jdk1.8.0_301 /usr/local/java/jdk

# 配置jdk环境
ENV JAVA_HOME /usr/local/java/jdk
ENV JRE_HOME ${JAVA_HOME}/jre
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib
ENV PATH ${JAVA_HOME}/bin:$PATH

# 系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#声明一个挂载点, 容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#应用构建成功后的jar文件被复制到镜像内
ADD target/add-utilize-item-1.0.jar add-utilize-item.jar

#启动容器时的进程
ENTRYPOINT ["java","-jar","/add-utilize-item.jar"]

#暴露18080端口
EXPOSE 18080