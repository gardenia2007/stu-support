#!/usr/bin/python
#-*- encoding:utf-8 -*-

import json, urllib2, sqlite3, time
from java import EmotionClassify

#!!!!!! token一段时间后就会失效 :<
access_token = '240067|6.9e73231b3594bd0096e1870e9d304313.2592000.1386309600-308894730'

emo = EmotionClassify()

conn = sqlite3.connect("e:/stu-support/web/db/testdb")
conn.isolation_level = None
cur = conn.cursor()
offset = (int(time.strftime('%H')) - 18) * 100
cur.execute("select * from stu order by s_id limit %d , 100"%(offset))
db_res = cur.fetchall()

# 抓取人人状态
def get_status(renren_id):
    renren = 'https://api.renren.com/v2/status/list?access_token=%s&pageSize=20&ownerId=%s'
    try:
        content = urllib2.urlopen(renren%(access_token, renren_id)).read()
        data = json.loads(content)
        res = data['response']
    except:
        res = ()
        print "----- HTTP ERROR -----"
    return res


# 将状态及心情存入数据库
def insert_db(stu_id, renren_id, status_id, status, emotion, create_time, stu_class, stu_gid):
    cur.execute("SELECT * FROM renren WHERE r_statusid = %d" %(int(status_id)))
    if(len(cur.fetchall()) > 0):
        return
    cur.execute("INSERT INTO renren VALUES (NULL, %d, %d, %d, '%s', '%s', '%s', '%s', %d)"\
            %(int(stu_id), int(renren_id), int(status_id), status, emotion, create_time, stu_class, int(stu_gid)))


# 处理每一个学生
for stu in db_res:
    res = get_status(stu[5])
    for s in res:
        if(s['sharedStatusId'] != 0):
            continue
        emotion = emo.get_emo(s['content'].encode('utf-8'))
        print emotion
        insert_db(stu_id=stu[1], renren_id=stu[5], status_id=s['id'], status=s['content'],\
                emotion=emotion, create_time=int(time.mktime(time.strptime(s['createTime'],'%Y-%m-%d %H:%M:%S'))),\
                  stu_class=stu[4], stu_gid=stu[3])
        conn.commit()
    print str(stu[2]).decode('utf-8').encode('GB18030'),'\tDone'
    print '-'*60



