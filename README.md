# 仓储管理系统（WMS）

## 系统演示

请扫码添加客服微信获取演示地址和系统详细资料。

![](./images/kf.png)

## 系统概述

工厂仓储管理
本项目名称为仓储管理系统，是针对仓库管理业务进行数字化管理的系统。
本系统从用户层面应该为管理人员所使用，可以进行基础信息维护、出入库管理、库存管理、系统管理等操作。

- 基础信息：对容器、条码、仓库等基础信息进行管理
- 配置管理：对流程、批次属性、出入库策略进行配置
- 物料拉动：进行物料拉动操作
- 任务管理：对仓库任务进行管理
- 库存：查看库存信息、库存事务
- 入库管理：进行入库操作
- 出库管理：进行出库操作
- 系统管理：进行用户、角色、菜单、部门、通知公告、日志的管理

## 系统包含终端说明

| 序号 | 模块              | 模块说明 |
| ---- | ----------------- | -------- |
| 1    | GC-AMS-QHY-MANAGE | 管理端   |
| 2    | GC-AMS-QHY-SERVER | 服务端   |

## 系统功能说明

本系统主要功能包括：

- 基础信息管理：如容器管理、条码品号关系、仓库、库区、库位、物料信息、物料种类、供应商、客户等
- 配置管理：如流程配置、批次属性配置、出入库策略等
- 物料拉动
- 任务管理
- 库存管理：如库位库存信息、库存事务、库位视图等
- 入库管理
- 出库管理：如出库单、分配查询等
- 系统管理：如用户管理、角色管理、菜单管理、部门管理、通知公告、日志管理等

## 系统主要界面

![image1.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_1.png)
![image2.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_2.png)
![image3.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_3.png)
![image4.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_4.png)
![image5.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_5.png)
![image6.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_6.png)
![image7.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_7.png)
![image8.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_8.png)
![image9.png](http://codeimg.haishi.net.cn/GC-AMS-QHY_9.png)


# 系统部署说明

## 代码模块说明

| 序号 | 目录                               | 目录说明 |
| ---- | ---------------------------------- | -------- |
| 1    | GC-AMS-QHY-SERVER/px-ams-quartz    | --       |
| 2    | GC-AMS-QHY-SERVER/px-ams-admin     | --       |
| 3    | GC-AMS-QHY-SERVER/px-ams-generator | --       |
| 4    | GC-AMS-QHY-SERVER/out              | --       |
| 5    | GC-AMS-QHY-SERVER/px-ams-common    | --       |
| 6    | GC-AMS-QHY-SERVER/px-ams-system    | --       |
| 7    | GC-AMS-QHY-SERVER/px-warewms-ams   | --       |
| 8    | GC-AMS-QHY-SERVER/META-INF         | --       |
| 9    | GC-AMS-QHY-SERVER/px-warewms-hard  | --       |
| 10   | GC-AMS-QHY-SERVER/px-ams-framework | --       |
| 11   | GC-AMS-QHY-SERVER/px-warewms-base  | --       |
| 12   | GC-AMS-QHY-SERVER/.idea            | --       |

## 系统技术说明

### 服务端

- MySQL： 5.7+
- JDK： 1.8+
- Redis

### 前端

- VUE：2.6.14
- element-ui: 2.15.6
- NODE：14.21.3 (>=8.9)
- NPM: >=3.0.0

## 系统演示

请扫码添加客服微信获取演示地址和系统详细资料。

![](./images/kf.png)
