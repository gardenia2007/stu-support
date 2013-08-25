import web
import re
import base64
from config import setting

render = setting.render
       
class Index:
    def GET(self):
        return render.index("Hello World!")
    def POST(self):
        pass



class Login:
    def GET(self):
        return render.login()
    def POST(self):
        pass