a ={1,2,3,4}
b={2,3,4,5}
a.difference_update(b)
print(a)

c=a.union(b)
print(c)

result=a.issubset(b)
print(result)


for item in a:
    print(item)