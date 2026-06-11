#定义一个Person类
class Person:
    def __init__(self,name,age,gender):
        self.name = name
        self.age = age
        self.gender = gender

#自定义方法（给实例添加行为）
#speak方法收到的参数是：调用speak方法的实例对象（self），其他参数
    def speak(self,msg):
        print(f'我叫{self.name},年龄是{self.age},性别是{self.gender},我想说：{msg}')

#print(Person.__dict__)     
p1=Person('张三',18,'男')


#当执行p1.speak()的时候，查找speak方法的过程：1.实例对象自身 2.实例缔造者身上
p1.speak('好好学习')