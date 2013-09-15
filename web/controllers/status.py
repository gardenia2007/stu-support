import web
import random
from config import setting
from auth import User

render = setting.render
db = setting.db

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
		plot_data = {'emotion':[], 'data':[]}
		data = db.query('select r_emotion, count(*) from renren where r_grade in %s group by r_emotion order by count(*) ASC'\
			%(str(web.ctx.session.grade_id)))
		for emo in data:
			plot_data['data'].append(emo['count(*)'])
			plot_data['emotion'].append(emo['r_emotion'].encode('utf-8'))
		web.ctx.session.emotion = plot_data['emotion']
		return render.status.value(web.ctx.session, plot_data, web.input(start='0', end='0'))
	def POST(self):
		pass


class Class(User):
	def __init__(self):
		User.__init__(self)
	def GET(self):
		plot_data = {'emotion':[], 'data':[]}
		all_class = db.query('select dinstinct r_class from renren where r_grade in %s'%(str(web.ctx.session.grade_id)))
		data = db.query('select r_emotion, count(*) from renren where r_grade in %s group by r_emotion order by count(*) ASC'\
			%(str(web.ctx.session.grade_id)))
		for emo in data:
			plot_data['data'].append(emo['count(*)'])
			plot_data['emotion'].append(emo['r_emotion'].encode('utf-8'))
		web.ctx.session.emotion = plot_data['emotion']
		return render.status.class_(web.ctx.session, plot_data, web.input(start='0', end='0'))
	def POST(self):
		pass


class List(User):
	def __init__(self):
		User.__init__(self)
	def GET(self, type):
		data = []
		stus = db.query('select s_renren, s_name, count(*) from stu, renren where r_emotion = "%s" and s_id = r_stu and s_grade in %s group by s_id order by count(*) DESC'\
			%(web.ctx.session.emotion[int(web.input(y=-1).y)], str(web.ctx.session.grade_id)))
		for stu in stus:
			data.append([str(stu['s_name']).encode('utf-8'), stu['count(*)'], stu['s_renren']])
		return render.status.list(data)
	def POST(self):
		pass


