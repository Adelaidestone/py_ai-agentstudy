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
scoreList =[]
while True:
    data = input("请输入学生的成绩:")
    if data=="结束":
        break;
    else:
        scoreList.append(int(data))


if scoreList:
    #统计平均分
    avg = sum(scoreList)/len(scoreList)
    #合格人数
    pass_num = 0

    #优秀人数
    high_num=0
    
    for item in scoreList:
        if item>=60:
            pass_num += 1
        if item>=90:
            high_num += 1
    #合格率
    pass_rate = pass_num/len(scoreList)*100
    high_rate = high_num/len(scoreList)*100

    print('统计信息如下')
    print(f'总人数为：{len(scoreList)}')
    print(f'最高分为：{max(scoreList)}')
    print(f'最低分为：{min(scoreList)}')
    print(f'合格人数：{pass_num}')
    print(f'优秀人数：{high_num}')
    print(f'合格率：{pass_rate:1f}%')
    print(f'优秀率：{high_rate:1f}%')    
    print(f'平均分数：{sum(scoreList)/len(scoreList)}')         


else:
    print("你没有输入任何成绩")   
    