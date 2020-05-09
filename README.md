# activiti7 

#### 介绍
最新activiti7  
最新前端设计器bpmn-js 
整合shiro ，mybatis-plus ，使用maven构建的springboot项目

效果展示：

首页
![输入图片说明](https://images.gitee.com/uploads/images/2019/1218/132607_37a29ce6_914343.png "屏幕截图.png")

shiro权限控制（精确至按钮级别）：
![输入图片说明](https://images.gitee.com/uploads/images/2019/1224/160736_3ac449d3_914343.png "屏幕截图.png")

并行网关下单方执行完成
![输入图片说明](https://images.gitee.com/uploads/images/2019/1218/134750_44a8107b_914343.png "屏幕截图.png")

驳回操作
![输入图片说明](https://images.gitee.com/uploads/images/2019/1218/135129_47a27fab_914343.png "屏幕截图.png")

流程执行完
![输入图片说明](https://images.gitee.com/uploads/images/2019/1218/132713_a80c6ae1_914343.png "屏幕截图.png")

![输入图片说明](https://images.gitee.com/uploads/images/2019/1218/135310_040c58d1_914343.png "屏幕截图.png")

流程设计器：
![流程设计器](https://images.gitee.com/uploads/images/2019/1218/133021_4da3a3f8_914343.png "屏幕截图.png")

#### 环境要求

jdk8 ，nodeJs 

#### 安装教程

1. 复制项目里的设计器，解压到一个文件夹

![输入图片说明](https://images.gitee.com/uploads/images/2019/1218/133712_b19f39ce_914343.png "屏幕截图.png")

2. 然后进入到该文件夹，shift+鼠标右键 ，点击在此处打开 shell窗口

第一次运行输入 npm  install （注意必须安装nodeJs环境，此后运行项目不用再执行此命令）
然后等待完成后 npm run dev 前端设计器即可启动。

3. 将数据库sql 导入数据库 （位置在项目根目录下）

4. 启动项目即可。

不足：
代码生成器在测试包下，现在还没能做的很好，很系统的拿出来使用，只能通过手动配置来使用

#### 2019-12-24 更新
- 新增历史办理菜单
- 新增历史办理  我的办理节点高亮操作
![输入图片说明](https://images.gitee.com/uploads/images/2019/1224/160135_96054a6c_914343.png "屏幕截图.png")

不会添加菜单的小伙伴可以直接从新导入下数据库即可。

### activiti动画展示

[ **动态效果展示**  :fa-paper-plane: ](http://182.92.122.242:8090/exam/activiti7.mp4)

### 之前做的小demo轨迹展示一并展示给大家

[ **动态效果展示**  :fa-paper-plane: ](http://182.92.122.242:8090/exam/xuanzhuan.mp4)

 **_希望小伙伴点个赞再走，有问题留言交流_**   :smile: 
