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

Uses neither semicolons or formatting rules, whitespace is required only to separate tokens when it's not obvious they're different tokens. Although less readable, the script above can be compressed like so:
```
int i@10~£i i@i-1$i=0¤¤
```

Don't want to download scrypt interpreter? No problem!
Mail your scrypt to sscriptbot@gmail.com and get the output (or debug message for bad scrypts) in reply.
(Rip the mail bot cannot connect to the mail anymore, seems Google has changed something security related)
