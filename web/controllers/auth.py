import web


class Admin:
	def __init__(self):
		if not (web.ctx.session.is_login and web.ctx.session.is_admin):
			raise web.seeother('/login')

class User:
	def __init__(self):
		if web.ctx.session.is_login == False:
			raise web.seeother('/login')
	def get_time_cond(self, filed_name = 'r_createtime'):
		start, end = int(web.input(start=-1).start), int(web.input(end=0).end)
		if start == -1:
			return ' 1 '
		else:
			return " %s < %s AND %s > %s "%(filed_name, int(end), filed_name, int(start))

