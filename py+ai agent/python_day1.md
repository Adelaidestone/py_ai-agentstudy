### Day 1（4h）— Python 基础语法 + 大模型认知

| 时间 | 内容                                               | 资料                             |
| ---- | -------------------------------------------------- | -------------------------------- |
| 2h   | Python Day1：变量、控制流、函数、类（Java 对照版） | A: `Day1-基础语法.md` + 练习     |
| 2h   | 大模型认知：什么是 LLM、训练流程、落地场景         | B: `1-1-大模型认知与工程概览.md` |

**Python 重点**：缩进代替花括号、动态类型、`def` 函数、`class` 基本写法
**Agent 重点**：搞懂大模型基本原理、token、上下文窗口

## Python标识符
在python中，标识符由字母、数字、下划线组成；所有标识符可以包括英文、数字、下划线（_）,但不能以数字开头
python的标识符区分大小写，大小写不同，python会觉得是两个不同的东西
以双下划线开头的 __foo  代表类的私有成员（可以访问，不建议碰），以双下划线开头和结尾的 __foo__ 代表 Python 里特殊方法专用的标识，如 __init__() 代表类的构造函数。
Python 可以同一行显示多条语句，方法是用分号 ; 分开


### python字符编码
utf-8 万国码 全球语言通吃 可以对任意语言进行编码
python3的编码方式是utf-8，py3无需==编写文件编码注释==

## python行与缩进
python的代码块不是用大括号{}来控制类，函数以及其他逻辑判断，是用缩进来写模块；通常缩进数量是4
## 多行语句
python可以在同一行使用多条语句，语句之间用分号（;）分割。
print 默认输出是换行的，如果要实现不换行需要在变量末尾加上逗号 。
可以用斜杠（\）将一行的语句分为多行显示。有括号的语句就不需要
## python引号
可以用引号('),双引号(")来标识字符串
## 注释
单行注释采用#
多行注释采用三个''' 或者三个双引号"""

## Python变量类型

### 标准数据类型

在内存中存储的数据可以有多种类型。

例如，一个人的年龄可以用数字来存储，他的名字可以用字符来存储。

Python 定义了一些标准类型，用于存储各种类型的数据。

Python有五个标准的数据类型：
#### Numbers（数字）
数字类型用于存储数值。他们是不可改变的数据类型，意味改变数字数据类型会分配一个新对象；
Python支持四种不同的数字类型：
* int 有符号整型
* long 长整型 可以代表八进制和十六进制（long python3后被移除，使用int代替）
* float  浮点型 
* complex 复数（复数由实数部分和虚数部分构成，可以用 a + bj,或者 complex(a,b) 表示， 复数的实部 a 和虚部 b 都是浮点型。）
java byte short int long float double char
#### String（字符串）
字符串或串(String)是由数字、字母、下划线组成的一串字符。
```
s= "dasdsadsad"
```

字符列表有两种取值顺序
* 从走到右从0开始，最大范围是字符串长度少1
* 从右到左从-1开始，最大范围是字符串开头

![[字符串索引.png]]

如果想从一串字符串中获取子字符串，可以使用[头下标:尾下标]来截取相应的字符串
可以是正数或负数，下标可以为空表示取到头或尾。

[头下标:尾下标] 获取的子字符串包含头下标的字符，==但不包含尾下标的字符。==
![[字符串切片.png]]
加号（+）是字符串连接运算符，星号（*）是重复操作

#### List（列表）(ArrayList)
可变，有序，允许重复
字面量	[1,2,3]	
+是列表连接运算符 *是重复操作
```
nums = [1, 2, 3, 4, 5]

# 长度
len(nums)              # 5

# 访问（支持负索引！）
nums[0]                # 1（第一个）
nums[-1]               # 5（最后一个）⭐
nums[-2]               # 4（倒数第二个）

# 修改
nums[0] = 100          # nums = [100, 2, 3, 4, 5]

# 添加
nums.append(6)         # 末尾添加
nums.insert(0, 0)      # 指定位置插入
nums.extend([7, 8])    # 添加多个 = nums + [7, 8]

# 删除
nums.remove(3)         # 删除值为 3 的第一个
del nums[0]            # 按索引删除
nums.pop()             # 弹出最后一个
nums.pop(0)            # 弹出指定位置

# 查找
3 in nums              # True / False（包含检查）
nums.index(3)          # 返回索引
nums.count(3)          # 出现次数

# 排序
nums.sort()            # 原地升序
nums.sort(reverse=True)  # 降序
sorted_nums = sorted(nums)  # 返回新列表（不改原）⭐
nums.reverse()         # 原地反转
```
#####  切片（Slicing）⭐⭐⭐ Python 杀手锏
```
nums = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

# 语法：list[start:stop:step]  （包含 start，不包含 stop，步长为step）

nums[2:5]      # [2, 3, 4]      索引 2 到 4
nums[:3]       # [0, 1, 2]      从头到索引 2
nums[7:]       # [7, 8, 9]      从索引 7 到末尾
nums[:]        # 完整复制 ⭐⭐
nums[::2]      # [0, 2, 4, 6, 8]   每 2 个取一个
nums[::-1]     # [9, 8, 7, ..., 0] 反转 ⭐⭐⭐
nums[-3:]      # [7, 8, 9]      最后 3 个

# 切片可以赋值
nums[2:5] = [20, 30, 40]   # 替换索引 2-4
```


> **重点中的重点**，Java 没有这个特性。
#### Tuple（元组）(record)
不可变，有序，允许重复
tuple=
元组用()标识。内部元素用逗号隔开。但是元组不能二次赋值,只读列表
字面量（1,2,3）
#### Dict(字典）(HashMap)
字典和list区别：字典元素是通过键（key）存取，不是通过偏移存
字典由索引（key）和对应值value组成
可变，有序(保证插入顺序)，不允许重复
字面量 {"a":1}
```user = {"name": "Alice", "age": 30}

# 访问
user["name"]              # "Alice"
user["height"]            # KeyError ❌

# 安全访问 ⭐
user.get("height")        # None（不会报错）
user.get("height", 1.7)   # 1.7（默认值）

# 添加 / 修改
user["email"] = "a@b.com"  # 添加
user["age"] = 31           # 修改

# 删除
del user["age"]
user.pop("age", None)      # 安全删除（不存在返回 None）

# 检查 key
"name" in user             # True
"phone" not in user        # True

# 长度
len(user)
```

#### Set(HashSet)
可变，无序，不重复
字面量{1,2,3}
```
# 字面量
s = {1, 2, 3}
empty = set()           # 注意：{} 是空 dict，不是空 set！

# 从 list 创建（去重）⭐
nums = [1, 2, 2, 3, 3, 3]
unique = set(nums)      # {1, 2, 3}
unique_list = list(set(nums))  # [1, 2, 3]

# 操作
s.add(4)
s.remove(1)             # 不存在会报错
s.discard(99)           # 不存在不报错
1 in s                  # True / False

集合运算（数学课重温）⭐
a = {1, 2, 3, 4}
b = {3, 4, 5, 6}

a | b        # 并集 {1, 2, 3, 4, 5, 6}
a & b        # 交集 {3, 4}
a - b        # 差集 {1, 2}
a ^ b        # 对称差 {1, 2, 5, 6}



实战：用户标签匹配

user_tags = {"python", "java", "ai", "backend"}
job_tags = {"python", "ai", "ml"}

common = user_tags & job_tags   # {'python', 'ai'} 匹配的标签
match_rate = len(common) / len(job_tags)  # 0.67```


