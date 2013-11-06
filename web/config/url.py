
pre_fix = 'controllers.'

urls = (
    "/",      					pre_fix + "index.Index",
    "/index", 					pre_fix + "index.Index",
    "/login", 					pre_fix + "index.Login",
    "/logout", 					pre_fix + "index.Logout",
    "/test",                    pre_fix + "index.Test",
    "/t",                    pre_fix + "keyword.Tmp",



    ###########
    "/status",					pre_fix + "status.Index",
    "/status/(.+)/list",		pre_fix + "status.List",
    "/status/value",			pre_fix + "status.Value",
    "/status/class",			pre_fix + "status.Class",

    ###########
    "/keyword",                 pre_fix + "keyword.Index",
    "/keyword/list",            pre_fix + "keyword.List",
    "/keyword/update",          pre_fix + "keyword.Update",


    "/admin",					pre_fix + "admin.Index",
    "/admin/change/(\d+)/(.+)", pre_fix + "admin.Change",
    "/admin/upload",            pre_fix + "admin.Upload",
    "/admin/adduser",           pre_fix + "admin.AddUser",
    "/admin/deluser/(\d+)",     pre_fix + "admin.DelUser",
)

