#list
a,b,c =[1,2,3]
first,*rest =[1,2,3,4,5]
print(first)
print(rest)


#字典解包到函数
config = {"Ir":1,"Or":2}
def train(Ir,Or):
    print(Ir,Or)
train(**config) 

#列表解包合并
a =[1,2,3]
b =[4,5,6]
merged = [*a,*b]
print(merged)

#字典合并解包
d1 = {"a":1,"b":2}
d2 = {"b":3,"c":4}
merged_dict={**d1,**d2}
print(merged_dict)


counts = {"a":0,"b":0,"c":0,"d":0}
for word in ["a","b","c","d","a"]:
    counts.setdefault(word,0)
    counts[word] +=1
print(counts)

from collections import Counter

counts = Counter(["a","b","c","a"])
print(counts)
