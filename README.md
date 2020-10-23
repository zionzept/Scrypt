# Scrypt

Scrypt! Custom script interpreter in java.


¤ Empty program:
```
¤
```

£ Hello world! (almost, prints 1):
```
£ 1
¤
```

@ Variable declaration and assigning (prints 12.4):
```
int i
i @ 10

float f @ 2.4

£ i + f
¤
```

Control flow

?€ if else (prints 10 and 0):
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

~ loop (infinitely prints 10):
```
int i @ 10
~
  £ i
  ¤
¤
```

$ conditional break (counts down from 10 to 1):
```
int i @ 10
~
  £ i
  i @ i - 1
  $ i = 0
  ¤
¤
```
