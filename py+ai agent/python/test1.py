from mymodule import * #导入所有公开的东西

print(public_func) # 输出：我可以被导入
print(_INTERNAL_FUNC) # 输出：我不应该被导入

