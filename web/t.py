#!/usr/bin/python


import json
import urllib2


renren = 'https://api.renren.com/v2/status/list?access_token=240067|6.1110bddb6e9728bec980c0bfbdabcd8e.2592000.1379818800-308894730&ownerId=283278996'

content = urllib2.urlopen(renren).read()

data = json.loads(content)

res = data['response']

for item in res:
	print item['content']


