import web
import random
from config import setting

render = setting.render


class User:
	"""check if is login"""
	def __init__(self):
		self.is_login = True
		if web.ctx.session.is_login == False:
			web.seeother('/login')
			self.is_login = False



class Index:
	def GET(self):
		return web.seeother('/status/value')
	def POST(self):
		pass


class Value(User):
	def __init__(self):
		User.__init__(self)
	def GET(self):
		data = {'url':'value'}
		v = random.randint(0, 30)
		return render.status.status(web.ctx.session, data, web.input(start='0', end='0'))
	def POST(self):
		pass


class Class(User):
	def __init__(self):
		User.__init__(self)
	def GET(self):
		data = {'url':'class'}
		return render.status.status(web.ctx.session, data, web.input(start='0', end='0'))
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


