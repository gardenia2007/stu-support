stu-support
======================
* 注意修改下列文件中的绝对路径
 * EmotionClassify/src/edu/hit/irlab/nlp/irlas/IRLAS.java
 * EmotionClassify/src/edu/hit/irlab/nlp/irlas/IRLAS_Interface.java
 * EmotionClassify/src/edu/hit/irlab/nlp/tools/FMMProcessor.java
 * EmotionClassify/src/util/Resource.java
 * EmotionClassify/src/util/Config.java
 * java.py

服务器启动方法
====================
将setup-web.bat放到C盘根目录下,需要启动服务器时双击执行setup.vbs即可
也可以添加到定时计划任务中

状态更新
====================
把命令“c:\python27\python.exe e:\stu-support\get_status.py"添加到定时任务中，每小时更新一次

数据库备份
====================
数据库文件为e:\stu-support\web\db\testdb，备份数据库时只需备份该文件即可