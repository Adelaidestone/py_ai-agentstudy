'''
##定义一个名为welcome的函数，函数中打印两句欢迎yu
def welcome():
    print("欢迎来到阿里巴巴")
    print("我一定要去大厂")


def order(num,dish):
    print(f'你点的是：{num}份{dish}')


order(1,'辣椒炒肉')
'''
##限制传参方式
'''
def greet(name,/,gender,*,age,height):
    print(f'我叫{name},性别{gender},年龄是{age}，身高是{height}cm')
greet('小王',gender='男',age=18,height=23)   
'''
'''
def test1(*args):
    print(args)
test1('张三','李四')   
'''
'''
def greet(name,msg):
    print(f'我是{name}，我要说：')
    speak(msg)
    print('牌没有问题')

def speak(msg):
    print(msg)


greet('FA国赌神','给我擦皮鞋')
'''

'''
def welcome(n):
    print(f'你好啊{n}')
    if n>1:
        welcome(n-1)
welcome(5)        
'''
'''
def welcome(n):
    if n >= 1:
        welcome(n - 1)
        print(f'你好啊{n}')
welcome(5)
'''

nums=[1,2,3,4]
print(sum(nums))