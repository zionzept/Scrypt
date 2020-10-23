# Scrypt

Scrypt! Custom script interpreter in java.


Empty program:
```
¤
```

Hello world! (almost, prints 1):
```
£ 1
¤
```

Variable declaration and assigning (prints 12.4):
```
int i
i @ 10

float f @ 2.4

£ i + f
¤
```

Control flow

if (prints 10 and 0):
```
int i @ 10

? i = 10
  £ 10
  ¤
  
? i = 15
  £ 15
  ¤
€
  £ 0
  ¤

¤
```
