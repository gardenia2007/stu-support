# -*- coding: utf-8 -*- 

import web
import random
import csv
import codecs
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

		if g_id == '0':
			g_id_str = '('
			for g in (db.select('grade', what='g_id')): g_id_str += (str(g.g_id)+',')
			web.ctx.session.grade_id = g_id_str + '0)'
		else:
			web.ctx.session.grade_id = '('+str(g_id)+')'
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
		for s_class, s_no, s_name, s_renren in f:
			if s_class[:3] == codecs.BOM_UTF8:
				s_class = s_class[3:]
			if s_class == '' or s_renren == '':
				continue
			web.debug(db.query('insert into stu values ($no, NULL, $n, $g, $c, $r)', \
				vars={'no':buffer(s_no.decode('utf-8')), 'n':buffer(s_name.decode('utf-8')), 'g':int(grade),\
					'c':int(s_class), 'r':int(s_renren.decode('utf-8'))}))
		raise web.seeother('/admin')


class AddUser(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self):
		pass
	def POST(self):
		i = web.input()
		db.insert('user', u_name=i.name, u_pass=i.password, u_grade=i.grade, u_role=i.role, u_keyword='')
		web.seeother('/admin')

class DelUser(Admin):
	def __init__(self):
		Admin.__init__(self)
	def GET(self, u_id):
		db.delete('user', where="u_id=$u_id", vars={'u_id':u_id})
		web.seeother('/admin')
	def POST(self):
		pass

