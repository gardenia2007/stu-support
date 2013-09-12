#!/usr/bin/python
#-*- encoding:utf-8 -*-

import json
import urllib2
import sqlite3

conn = sqlite3.connect("web/db/testdb")
conn.isolation_level = None
cur = conn.cursor()
cur.execute("select * from stu limit 10")
db_res = cur.fetchall()

# 抓取人人状态
def get_status(renren_id):
	renren = 'https://api.renren.com/v2/status/list?access_token=240067|6.1110bddb6e9728bec980c0bfbdabcd8e.2592000.1379818800-308894730&pageSize=20&ownerId=%s'
	content = urllib2.urlopen(renren%(renren_id)).read()
	data = json.loads(content)
	res = data['response']
	return res

# 分析心情
def parse_status(status):
	# TODO 使用jpype调用java代码
	return 'happy'

# 将状态及心情存入数据库
def insert_db(stu_id, renren_id, status_id, status, emotion, create_time):
	cur.execute("SELECT * FROM renren WHERE r_statusid = %d" %(int(status_id)))
	if(len(cur.fetchall()) > 0):
		return
	cur.execute("INSERT INTO renren VALUES (NULL, %d, %d, %d, '%s', '%s', '%s')"\
			%(int(stu_id), int(renren_id), int(status_id), status, emotion, create_time))


# 处理每一个学生
for stu in db_res:
	res = get_status(stu[5])
	for s in res:
		if(s['sharedStatusId'] != 0):
			continue
		emotion = parse_status(s['content'])
		insert_db(stu_id=stu[1], renren_id=stu[5], status_id=s['id'], status=s['content'],\
				emotion=emotion, create_time=s['createTime']) 
		conn.commit()
	print stu[2],'\tDone'



