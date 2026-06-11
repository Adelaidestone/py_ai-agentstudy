"""
age = input("请输入你的年龄：")
age = int(age) #将输入的字符串转换为整数
if age>=18:
    print("你是成年人")
else:
    print("你是未成年人")
print("总之你就是猪")
"""
"""
age = int(input("请输入你的年龄："))

if age<10:
    print("你是幼儿")
elif age<18:
    print("你是未成年人")
elif age<65:
    print("你是成年人")
else:
    print("你是老年人")    

"""
"""
age=int(input("请输入你的年龄："))
ishc=input("你体检过吗？（是/否）")
score=int(input("请输入你的会员等级："))

if age < 18:
    print("你是未成年人，不能参加比赛")    
else:
        if ishc=="否":
            print("你没有体检，不能参加比赛")  
        else:
            if score==1:
                print("奖励T恤")
            elif score==2:
                print("专业跑鞋")
            elif score==3:
                print("运动耳机")
"""
"""
n = 1
while n<=10:
    print(f"第{n}次你好啊")
    n+=1
"""
"""
print("你现在身处密室，请输入密码逃脱")
Key = "Mussy520"
guess = ""
while guess != Key:
    guess=input("请输入密码：")
    if guess == Key:
        print("恭喜你逃脱了密室！")
    else:
        print("密码错误，请再试一次！")
"""
"""
for n in range(1,11):
    print("你好啊",n)
print("循环结束了")


for m in 'abcdef':
    print(m)

nums = [1,2,3,4,5]
for i in nums:
    nums.append(5)
    print(i)
"""

"""
##加密输入的文字
text = input("请选择要加密的文字：")

encrypted_char=""
for i in text:
    char_code = ord(i) 
    encrypted_code = char_code + 1
    encrypted_char += chr(encrypted_code)
print("加密后的文字是：",encrypted_char)
"""
"""
day = 1
for day in range(1,31):
    print(f"今天是第{day}天")
    for n in range(1,4):
        print(f"第{n}次锻炼")
    print(f"第{day}天锻炼已完成！明天继续")    
print(f"第{day}天锻炼计划完成了！我现在闪闪发光")   
"""

"""
n=1
while n<=30:
    print(f"今天是第{n}天")
    m=1
    while m<=3:
        print(f"第{m}次锻炼")
        m+=1
    print(f"第{n}天锻炼已完成！明天继续")
    n +=1
print(f"第{n-1}天锻炼计划完成了！我现在闪闪发光")
"""
"""
for n in range(1,10):
    m=1
    while m<=n:
        print(f"{m}*{n}={m*n}",end="\t")
        m+=1
    print()
"""
##吃饭睡觉 第2天不准睡觉
"""
for day in range(1,6):
    print(f"第{day}天")
    print("吃饭")
    if day == 2:
        continue
    print("睡觉")
"""


"""
3个关卡 一个关卡一道题 答对进下一关 3关通过成功
答错重试 第3次错 游戏结束
输入为空 重新作答 不消耗时长
输入q 直接退出游戏
"""
questions =["第1题的答案是：","第2题的答案是：","第3题的答案是："]
answers =[1,2,3]

for i in range(3):
    wrong =0
    while wrong<=2:
        guess=input(questions[i])
        if int(guess) == int(answers[i]):
            print("进入下一关" if i<2 else "恭喜你通关了")
            break
        if guess =="":
            continue
        if guess=="q":
            print("退出游戏")
            exit()
        else:
            wrong += 1
            print(f"答错了，还剩{3-wrong}次机会")

    else:
        print("答错3次，挑战失败")
        break
    