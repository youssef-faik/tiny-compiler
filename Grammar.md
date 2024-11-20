# Tiny Compiler Grammar

## Statements

1. **Variable Assignment (`LET`)**

```VAR = INT```

- VAR → Variable name, letters only (uppercase/lowercase letters)
- INT → Integer (positive numbers, no decimals)

2. **Print Statement (`SHOW`)**

```SHOW INT``` <br/> ```SHOW VAR```

- SHOW → Displays either a constant integer or a variable value.

## Grammar Rules (BNF-like)

```PROGRAM → STATEMENT* 
STATEMENT → LET | SHOW 
LET → VAR "=" INT 
SHOW → "SHOW" (INT | VAR) 
VAR → [a-zA-Z]+ INT → [0-9]+
```

## Example Program

```
X = 10 
Y = 20 
SHOW X 
SHOW Y 
SHOW 30
```

## Expected Output:

```
10
20
30
```

