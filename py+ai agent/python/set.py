s={1,2,3}
empty =set() ##注意{}是空dict，不是空List或者set

#从list去重
nums=[1,2,3,4,5,1,2,3]
print(set(nums))


#操作
s.add(4)
s.remove(2)
print(s)
s.discard(5) ##discard和remove的区别是，discard删除不存在的元素不会报错，而remove会报错
1 in s
print(1 in s)



##集合运算
a ={1,2,3,4}
b ={3,4,5,6}
a|b ##并集
print(a|b)
print(a&b)
print(a-b)
print(a^b) ##对称差集
