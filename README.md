## 1, 打包

    * 执行： ./relase.sh  ,在release目录下生成tar包

## 2，jdk安装

    * 下载jdk1.8

    * 编辑/etc/profile 文件，添加如下配置：
     export JAVA_HOME=/usr/local/jdk1.8.0_151
     export CLASSPATH=".:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar"
     export PATH="$JAVA_HOME/bin:$PATH:/usr/local/mongodb/bin/"


## 3, 运行
    * 解压tar包，例如：tar -zxvf  lipstickserver-release-2018-09-26-02-22.tar.gz
    * cd  lipstickserver-release-2018-09-26-02-22/bin
    * ./ run_server.sh

