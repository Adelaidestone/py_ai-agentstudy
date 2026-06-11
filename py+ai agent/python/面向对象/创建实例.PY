#定义一个Person类
#定义一个Person类
class Person:
    #当一个函数被定义在了类中，那这个函数就被称为：方法
    # _init_:初始化方法，主要作用：给当前正在创建的实例对象添加属性
    #_init_方法收到的参数：当前正在创建的实例对象（self）、其他的自定义参数
    def __init__(self,name,age,gender):
        #给实例添加属性（语法为:self。属性名 = 值）
        self.name = name
        self.age = age
        self.gender = gender

#创建Person类的实例对象
p1=Person('张三',18,'男')
p2=Person('李四',12,'女')

#无法直接打印一个实例

print(p1.name)
print(p2.name)
print(p1.age)
print(p2.age)


#通过实例.__dict__可以查看实例身上所有的属性
print(p1.__dict__)