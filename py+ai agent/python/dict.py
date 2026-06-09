user ={"name":"Alice","age":30,"city":"new york"}

user["name"]  # 输出：Alice
user["age"]   # 输出：30
user["city"]  # 输出：new york

#安全访问
user.get("deep")  #None 不会报错


#添加/修改
user["email"]="a@b.com"
user["age"]=31
print(user) # {'name': 'Alice', 'age': 31, 'city': 'new york', 'email': '

#删除
del user["city"]
print(user) # {'name': 'Alice', 'age': 31, 'email': '
user.pop("name")
print(user) # {'age': 31, 'email': '}

#检查key
print(("name" in user )) #False
print("phone"not in user)

#长度
print (len(user))#2