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
def test1(*args):
    print(args)
test1('张三','李四')   
