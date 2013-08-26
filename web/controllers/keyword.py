import web
import re
from config import setting

render = setting.render

class Index:
	def GET(self):
		return render.keyword()
	def POST(self):
		pass


