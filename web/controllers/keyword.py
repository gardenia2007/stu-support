import web
import re
from config import setting

render = setting.render

class Index:
	def GET(self):
		return render.keyword(web.ctx.session)
	def POST(self):
		pass


