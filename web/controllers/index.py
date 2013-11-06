import web
import re
import base64
import urllib
from config import setting
from config import setting

render = setting.render
db = setting.db

class Index:
    def GET(self):
    	if web.ctx.session.is_login == False:
    		web.seeother('/login')
    	else:
    		web.seeother('/status')
    def POST(self):
        pass

class Logout:
    def GET(self):
    	web.ctx.session.kill()
    	web.seeother('/')
    def POST(self):
        pass



class Login:
	def GET(self):
		return render.login()
	def POST(self):
		i = web.input()
		# results = list(db.select('user', where=web.db.sqlwhere({'name':i.username, 'password':i.password})))
		sql = "select * from user, grade where u_name=$n and u_pass=$p and u_grade = g_id"
		results = list(db.query(sql, vars={'n':i.username, 'p':i.password}))
		web.ctx.session.count += 1
		if len(results) == 1:
			web.ctx.session.is_login = True
			web.ctx.session.uid = results[0].u_id
			web.ctx.session.name = results[0].u_name
			web.ctx.session.grade = results[0].g_name
			web.ctx.session.grade_id = '('+str(results[0].g_id)+')'
			if results[0].u_role == 'admin':
				web.ctx.session.is_admin = True
			web.seeother('/')
		else:
			return 'Fail, %s' % web.ctx.session.count


class DBtest:
    def GET(self):
        data = db.select('test')
        for x in data:
        	pass
    def POST(self):
        pass

class Test:
	def GET(self):
		pass

	def POST():
		pass

