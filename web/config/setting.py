import web
# -*- coding: utf-8 -*- 
#db = web.database(dbn='mysql', db='www', user='www', pw='www')

render = web.template.render('templates/', cache=False)

web.config.debug = True

config = web.storage(
	email='aa.com',
	site_name = U"学生工作决策支持平台",
	site_desc = '',
	static = '/static',
)


web.template.Template.globals['config'] = config
web.template.Template.globals['render'] = render


