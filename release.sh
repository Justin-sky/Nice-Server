#!/bin/sh


version=`date +%F-%H-%M`

BASE_DIR=$(dirname $0)/

RELEASE_DIR=${BASE_DIR}release/lipstickserver-release-$version
RELEASE_BIN_DIR=$RELEASE_DIR/bin
RELEASE_CONF_DIR=$RELEASE_DIR/conf
RELEASE_LIB_DIR=$RELEASE_DIR/lib

MODULE_CORE_DIR=${BASE_DIR}

if [ -d "$RELEASE_DIR" ];then
	error_exit "$RELEASE_DIR is existed, please check the version"
fi


#===========================================================================================
# 生成jar
#===========================================================================================
./gradlew jar
./gradlew copyJars

#===========================================================================================
# 准备相关目录结构
#===========================================================================================
mkdir -p $RELEASE_DIR
mkdir -p $RELEASE_BIN_DIR
mkdir -p $RELEASE_CONF_DIR
mkdir -p $RELEASE_LIB_DIR


cp $MODULE_CORE_DIR/lib/*.jar         $RELEASE_LIB_DIR
cp $MODULE_CORE_DIR/build/libs/*.jar  $RELEASE_LIB_DIR

cp -R ${BASE_DIR}bin/* $RELEASE_BIN_DIR
cp -R ${BASE_DIR}conf/*  $RELEASE_CONF_DIR


cd release

tar -zcf lipstickserver-release-$version.tar.gz lipstickserver-release-$version

rm -rf lipstickserver-release-$version
cd ..
