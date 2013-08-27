
pre_fix = 'controllers.'

urls = (
    "/",      					pre_fix + "index.Index",
    "/index", 					pre_fix + "index.Index",
    "/login", 					pre_fix + "index.Login",
    "/logout", 					pre_fix + "index.Logout",

    ###########
    "/status",					pre_fix + "status.Index",
    "/status/(.+)/list",		pre_fix + "status.List",
    "/status/value",			pre_fix + "status.Value",
    "/status/class",			pre_fix + "status.Class",

    ###########
    "/keyword",					pre_fix + "keyword.Index",

    "/db",						pre_fix + "index.DBtest",
)

