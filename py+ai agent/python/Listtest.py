nums=[1,2,3,4,5]
nums2=[6,7,8]
#算长度
len(nums) #5

print (nums+nums2)

#访问（支持负索引）
nums[0]#1
nums[-1]#5

#修改
nums[0]=10

#添加
nums.append(6)#在末尾加
nums.insert(0,0)#在指定位置加
nums.extend([7,8])#在末尾加一个列表

#删除
nums.remove(3)#删除第一个出现的3
del nums[0]#按照索引删除
nums.pop()#删除最后一个
nums.pop(2)#删除指定位置的元素'



#查找
3 in nums #True/false(包含检查)

nums.index(2);#返回索引

nums.count(2)#返回出现次数


#排序
nums.sort()#升序
nums.sort(reverse=True)#降序
sorted_nums=sorted(nums)#返回新列表（不改原）
nums.reverse()#原地反转

print(nums);

#list[start:end:start](包含step，不包含end，步长为step)

nums[2:4]#索引2到3
print(nums[2:4])
nums[::2]#步长为2
print(nums[::2])
nums[::-1]#反转列表
print(nums[::-1])
nums[1:] #第一个到末尾的所有元素
print(nums[1:])

print(nums * 2)
