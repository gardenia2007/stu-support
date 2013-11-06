#!/usr/bin/python
#-*- encoding:utf-8 -*-


import jpype

class EmotionClassify:
    def __init__(self):
        jvmPath = jpype.getDefaultJVMPath()           #the path of jvm.dll 
        classpath = "E:\\stu-support\\EmotionClassify\\bin"    #the path of PasswordCipher.class 
        jvmArg = "-Djava.class.path=" + classpath 
        if not jpype.isJVMStarted():                    #test whether the JVM is started 
            jpype.startJVM(jvmPath,jvmArg)             #start JVM 
        javaClass = jpype.JClass("sa.EmotionClassify")   #create the Java class
        self.Emo = javaClass()
    def get_emo(self, content):
        try:
            print content.decode('utf-8').encode('GB18030')
            return self.Emo.getEmo(content.decode('utf-8'))
        except jpype.JavaException as exception:
            print exception.message()
            print exception.stacktrace()

    def __del__(self):
        jpype.shutdownJVM()        #shut down JVM 

