import web
import random
from config import setting

render = setting.render

class Index:
	def GET(self):
		return web.seeother('/status/value')
	def POST(self):
		pass


class Value:
	def GET(self):
		data = {'url':'value'}
		v = random.randint(0, 30)
		return render.status.status(data, web.input(start='0', end='0'))
	def POST(self):
		pass


class Class:
	def GET(self):
		data = {'url':'class'}
		return render.status.status(data, web.input(start='0', end='0'))
	def POST(self):
		pass


class List:
	def GET(self, type):
		data = []
		for i in xrange(0,int(web.input(count = 0).count)):
			data.append(['123'+str(i), random.randint(5, 30), '#'])
		data = sorted(data, reverse = True, key = lambda x: x[1])
		return render.status.list(data)
	def POST(self):
		pass


