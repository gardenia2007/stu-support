#!/usr/bin/python

from config.url import urls
import web
import sys 

default_encoding = 'utf-8' 
if sys.getdefaultencoding() != default_encoding: 
    reload(sys) 
    sys.setdefaultencoding(default_encoding) 

app = web.application(urls, globals())

if web.config.get('_session') is None:
    session = web.session.Session(app, web.session.DiskStore('sessions'),\
    	initializer={'count': 0, 'is_login':False, 'is_admin':False})
    web.config._session = session
else:
    session = web.config._session

def session_hook():
	web.ctx.session = session

web.input._unicode = False

app.add_processor(web.loadhook(session_hook))

if __name__ == "__main__":
	app.run()

