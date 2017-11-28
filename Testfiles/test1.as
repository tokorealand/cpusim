.wordsize 32              ; sets the machine wordsize
.regcnt    4              ; identifies number of gen purpose registers
.maxmem   0x40            ; max memory size is 64 bytes

ADDI x1, x2, #1;
ADDI x2, x2, #2;
PUSH x2;
POP  x0;
.pos 0x30
stack:
.pos 0x34
HALT

