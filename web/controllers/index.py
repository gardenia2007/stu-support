import web
import re
import base64
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
		results = list(db.select('user', where=web.db.sqlwhere({'name':i.username, 'password':i.password})))
		web.ctx.session.count += 1
		if len(results) == 1:
			web.ctx.session.is_login = True
			web.ctx.session.name = results[0].name
			web.ctx.session.admin = results[0].admin
			if results[0].role == 'admin':
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

