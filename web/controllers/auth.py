import web


class Admin:
	def __init__(self):
		if not (web.ctx.session.is_login and web.ctx.session.is_admin):
			raise web.seeother('/login')

class User:
	def __init__(self):
		if web.ctx.session.is_login == False:
			raise web.seeother('/login')

