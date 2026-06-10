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

![[Pasted image 20260607094341.png]]

如果想从一串字符串中获取子字符串，可以使用[头下标:尾下标]来截取相应的字符串
可以是正数或负数，下标可以为空表示取到头或尾。

[头下标:尾下标] 获取的子字符串包含头下标的字符，==但不包含尾下标的字符。==
![[Pasted image 20260607094940.png]]
加号（+）是字符串连接运算符，星号（*）是重复操作




#### List（列表）(ArrayList)
可变，有序，允许重复
字面量	[1,2,3]	
+是列表连接运算符 *是重复操作
```
nums = [1, 2, 3, 4, 5]

# 长度
len(nums)              # 5

max(nums)

sorted(nums,reverse=布尔值)

min(nums)

sum(nums)



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
nums.clear()

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

💡 **什么时候用 list，什么时候用 tuple？** 
	**list**:内容可能变（用户列表、日志、训练数据batch）
	**tuple**：固定结构（坐标，RGB、函数返回多个值）


- **list**：内容可能变（用户列表、日志、训练数据 batch）
- **tuple**：固定结构（坐标、RGB、函数返回多个值）
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

````
## 6. 综合：什么时候用什么？（5 分钟）

| 场景            | 用什么                                | 为什么               |
| --------------- | ------------------------------------- | -------------------- |
| 一组顺序数据    | `list`                                | 可变、有序、最常用   |
| 多个返回值      | `tuple`                               | 不可变更安全         |
| 键值对          | `dict`                                | 快速查找 O(1)        |
| 去重 / 包含判断 | `set`                                 | 去重 + O(1) 包含查询 |
| 配置 / 函数返回 | `dict` 或 `dataclass`                 | 自描述               |
| 矩阵数据        | `list` 嵌套 → `numpy.array`（明天学） | 性能                 |

## 解包
```● "解包"就是把容器拆开，把里面的东西取出来。


  1. 基本解包 —— 拆箱子
  a, b, c = [1, 2, 3]
  # 箱子里有 3 个东西，用 3 个变量接住
  # a=1, b=2, c=3
  2. * 星号 —— "剩下的全给我"
  first, *rest = [1, 2, 3, 4, 5]
  # first 拿走第1个 → 1
  # *rest 拿走剩下的 → [2, 3, 4, 5]
  *init, last = [1, 2, 3, 4, 5]
  # *init 拿走前面的 → [1, 2, 3, 4]
  # last 拿走最后一个 → 5
  * 就是"我全包了"的意思。
  1. ** 双星号 —— 把字典拆成"键=值"传给函数
  config = {"lr": 0.01, "batch_size": 32}
  train(**config)
  # 拆开之后等于 ↓
  train(lr=0.01, batch_size=32)
  就是把字典"铺开"塞进函数参数里。
  2. 合并 —— 把多个容器拼一起
  # 列表合并，用 * 展开
  a = [1, 2]
  b = [3, 4]
  combined = [*a, *b, 5]   # [1, 2, 3, 4, 5]
  # 相当于把 a 和 b 倒进一个新列表，再加个 5
  # 字典合并，用 ** 展开
  d1 = {"a": 1}
  d2 = {"b": 2}
  merged = {**d1, **d2}    # {'a': 1, 'b': 2}
  # 相当于把两个字典倒进一个新字典
  ---
  一句话：* 拆列表，** 拆字典，都是把容器"打开"用。
```````

## any/all
```
nums=[1,2,3,4,5]

any(x > 3 for x in nums) ##至少一个 True
all(x>2 for x in nums)##所有 False

```````
## dict.setdefault
```
counts = {}
for word in ["a", "b", "a", "c", "a"]:
    counts.setdefault(word, 0)
    counts[word] += 1
# 等价更优雅写法：用 Counter


  ---
  逐步拆解

  第1步： 创建空字典 counts = {}

  第2步： 遍历列表，一个一个处理：

  第1轮：word="a" → 字典里没有"a"，先设为0，再+1 → {"a": 1}
  第2轮：word="b" → 字典里没有"b"，先设为0，再+1 → {"a": 1, "b": 1}
  第3轮：word="a" → 字典里有"a"了，跳过设0，直接+1 → {"a": 2, "b": 1}
  第4轮：word="c" → 字典里没有"c"，先设为0，再+1 → {"a": 2, "b": 1, "c": 1}
  第5轮：word="a" → 字典里有"a"了，跳过设0，直接+1 → {"a": 3, "b": 1, "c": 1}

  最终结果："a" 出现3次，"b" 出现1次，"c" 出现1次。

  ---
  关键一行：setdefault

  counts.setdefault(word, 0)

  意思是：如果字典里没有这个键，就设为0；有了就不动。

  没有这行的话，第一次 counts["a"] += 1 会报错，因为 counts["a"] 还不存在，不能对一个不存在的东西做 +1。

  ---
  更优雅的写法

  from collections import Counter

  counts = Counter(["a", "b", "a", "c", "a"])
  print(counts)  # Counter({'a': 3, 'b': 1, 'c': 1})

  一行搞定，专门干这事。
```````
## 字母推导式
```# 反转字典（key/value 互换）
original = {"a": 1, "b": 2, "c": 3}
reversed_dict = {v: k for k, v in original.items()}
# {1: 'a', 2: 'b', 3: 'c'}

# 过滤
scores = {"Alice": 85, "Bob": 60, "Charlie": 90}
high_scores = {name: score for name, score in scores.items() if score >= 80}
# {'Alice': 85, 'Charlie': 90}

# 从两个列表创建字典
keys = ["a", "b", "c"]
values = [1, 2, 3]
d = dict(zip(keys, values))           # {'a': 1, 'b': 2, 'c': 3}
d = {k: v for k, v in zip(keys, values)}  # 同上
```
## 运算符

```
print(100/12)#除法
print(100//12) #整数除法，向下取整
print(100%12) #求余数
print(100**12)#幂运算


age=18 赋值运算符
age += 1  等价于 age = age+1

age /=3

age // 3 取整运算 

age *= 3

age -= 3


age %= 3 取模
```
