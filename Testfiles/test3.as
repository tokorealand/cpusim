.wordsize 32              ; sets the machine wordsize
.regcnt    4              ; identifies number of gen purpose registers
.maxmem   0x40      ; max memory size is 64 bytes


ADDI x1, x1, #3;
ADD  x0, x1, x3;
SUBI x1, x0, #-3;
ADDI x2, x2, #48;
LDUR x1, [x2, #0];
STUR x1, [x3, #0];
.pos 0x20
stack:
HALT
