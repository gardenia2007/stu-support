import web
import random
from config import setting
from auth import User

render = setting.render

class Index(User):
	def __init__(self):
		User.__init__(self)
	def GET(self):
		raise web.seeother('/status/value')
	def POST(self):
		pass


class Value(User):
	def __init__(self):
		User.__init__(self)
	def GET(self):
		# plot_data = [5, 16, 27, 20, 14, 11, 4]
		plot_data = []
		for x in xrange(1,10):
			plot_data.append(random.randint(0, 30))
		v = random.randint(0, 30)
		return render.status.value(web.ctx.session, plot_data, web.input(start='0', end='0'))
	def POST(self):
		pass


class Class(User):
	def __init__(self):
		User.__init__(self)
	def GET(self):
		plot_data = []
		return render.status.class_(web.ctx.session, plot_data, web.input(start='0', end='0'))
	def POST(self):
		pass


class List(User):
	def __init__(self):
		User.__init__(self)
	def GET(self, type):
		data = []
		for i in xrange(0,int(web.input(count = 0).count)):
			data.append(['123'+str(i), random.randint(5, 30), '#'])
		data = sorted(data, reverse = True, key = lambda x: x[1])
		return render.status.list(data)
	def POST(self):
		pass


