# -*- coding: utf-8 -*- 

import web
import random
import csv
from auth import Admin
from config import setting


render = setting.render
db = setting.db


class Index(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self):
		grades = list(db.select('grade'))
		users = list(db.query('select * from user, grade where u_grade = g_id'))
		info = list(db.query('select g_name, count(*) as count from stu, grade where s_grade = g_id group by s_grade'))
		data = {'grades':grades, 'users':users, 'info':info}
		return render.admin.admin(web.ctx.session, data)
	def POST(self):
		pass


class Change(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self, g_id, g_name):
		web.ctx.session.grade = g_name
		web.ctx.session.grade_id = g_id
		web.seeother('/admin')
	def POST(self):
		pass


class Upload(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self):
		pass
	def POST(self):
		grade = web.input().grade
		db.query('delete from stu where s_grade = $g', vars={'g':grade})
		# uploaded csv file
		f = csv.reader(web.input(file={}).file.file)
		for s_name, s_class, s_renren in f:
			web.debug(db.query('insert into stu values (NULL, $n, $g, $c, $r)', \
				vars={'n':s_name.decode('utf-8'), 'g':grade, 'c':s_class, 'r':s_renren}))
		raise web.seeother('/admin')


class AddUser(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self):
		pass
	def POST(self):
		i = web.input()
		db.insert('user', u_name=i.name, u_pass=i.password, u_grade=i.grade, u_role=i.role)
		web.seeother('/admin')

class DelUser(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self, u_id):
		db.delete('user', where="u_id=$u_id", vars={'u_id':u_id})
		web.seeother('/admin')
	def POST(self):
		pass

